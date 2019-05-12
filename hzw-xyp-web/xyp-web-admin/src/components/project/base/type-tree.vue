<template>
  <!-- v-loading.fullscreen.lock="loading"
       -->
  <div id="type-tree">
    <!-- 标题 -->
    <div class="tree-title">
      <h3>类别管理</h3>
    </div>

    <div class="tree-content">
      <!-- 树状数据展示 -->
      <div class="block" v-loading="loading">
        <div>
          <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>
        </div>
        <el-tree
          ref="treeData2"
          :data="treeData"
          :props="defaultProps"
          :filter-node-method="filterNode"
          :expand-on-click-node="expandOnClickNode"
          :allow-drop="allowDrop"
          node-key="id"
          draggable
          default-expand-all
          @node-click="editNode"
          @node-drop="nodeDrop"
        >
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span><i class="el-icon-loading" v-if="data.selected"></i>{{ node.label }}</span>
          <span>
            <el-button type="text" size="mini" v-if="node.level < 3" @click.stop="createNextNode(node, data)">
              添加下级
            </el-button>
            <el-button type="text" size="mini" @click.stop="createSomeNode(node, data)">
              添加同级
            </el-button>
            <el-button type="text" size="mini" @click.stop="deleteNode(node, data)">
              删除
            </el-button>
          </span>
        </span>
        </el-tree>
      </div>

      <!-- 占位标签 方便给下面的固定窗口占个位置 -->
      <div class="block"></div>

      <!-- 数据编辑 -->
      <div class="float_block">
        <div>
          <span class="tree-title">{{editParam.title || "新增顶级节点"}} <i>{{(editParam.subTitle && "(" + editParam.subTitle + ")")}}</i></span>
        </div>
        <el-form :model="beanData" label-width="100px" >
          <el-form-item label="上级分类" v-if="beanData.parentId && beanData.parentId > 0">
            <span>{{beanData.parentName || "未知"}}</span>
          </el-form-item>
          <el-form-item  prop="name" label="分类名称">
            <el-input v-model="beanData.name"></el-input>
          </el-form-item>
          <div class="edit-btn">
            <el-form-item size="large">
              <el-button type="primary" @click="save(beanData)" v-if="!beanData.id">新 增</el-button>
              <el-button type="primary" @click="save(beanData)" v-if="beanData.id">确 认 修 改</el-button>
              <el-button @click="cancelEdit" v-if="beanData.parentId && beanData.parentId > 0 || beanData.id">取 消</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </div>

</template>

<script>

  export default {
    name: "type-tree",
    info: "类别管理模块",
    data(){
      return {
        treeData : [],
        beanData : {},
        editParam : {},
        filterText : "",
        expandOnClickNode : false,
        node : {},
        loading: false,
        defaultProps: {
          children: 'childs',
          label: 'name',
        }
      }
    },
    created() {
      this.getTreeData();
    },

    methods : {
      getTreeData(){ // 获取树节点数据   目前先考虑获取全部数据，以后再考虑懒加载吧
        let _this = this;
        _this.loading = true;
        this.$axios.get("base/type/trees").then(result =>{
          _this.treeData = (result && result.trees) || [];
        }).catch(err =>{
          _this.FUNCS.error(err.message);
        }).finally(function () {
          _this.loading = false;
        })
      },

      save(beanData){ // 新增、保存节点数据
        let _this = this;
        let parentName = beanData.parentName;
        this.$axios.post("base/type", beanData).then(result =>{
          _this.FUNCS.success("数据保存成功！");
          _this.beanData = (result && result.beanData) || {};
          _this.beanData.parentName = parentName;
          // 重新获取数据
          _this.getTreeData();
        }).catch(err =>{
          _this.FUNCS.error(err.message);
        })
      },

      deleteNode(node, data){ // 删除
        console.log("节点删除");
        let id = data.id;
        if(!id){
          this.FUNCS.warning("获取id失败！");
        }
        let _this = this;
        this.$confirm('你确定要删除当前数据 - ' + data.name + '?', '删除提示', {
          confirmButtonText: '确认删除',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return this.$axios.delete("base/type/" + id);
        }).then(result =>{
          _this.FUNCS.success("删除成功！");
          // 重新获取数据
          _this.getTreeData();
          _this.beanData = {};
          _this.editParam = {};
        }).catch(err =>{
          console.log(err)
          err.message && _this.FUNCS.error(err.message);
        });
    },

      filterNode(value, data) { // 节点过滤
        if (!value && !data.name) return true;
        return data.name.indexOf(value) !== -1;
      },

      editNode(data, node) { // 编辑节点
        let parentData = node.parent.data || {};
        this.setEditParams("编辑节点", data.name);
        this.changeNodeSelected(this.treeData, data);
        let beanData = data.beanData || {};
        this.beanData = beanData;
        this.beanData.parentName = parentData.name;
      },

      createNextNode(node, data){ // 添加下级节点
        this.setEditParams("添加下级节点", data.name);
        this.beanData = {};
        this.$set(this.beanData, "parentId", data.id || -1);
        this.$set(this.beanData, "parentName", data.name);
        console.log(this.beanData)
      },

      createSomeNode(node, data){ // 添加同级节点
        let parentData = node.parent.data || {};
        this.setEditParams("添加同级节点", data.name);
        this.beanData = {};
        this.$set(this.beanData, "parentId", parentData.id || -1);
        this.$set(this.beanData, "parentName", parentData.name);
        console.log(this.beanData)
      },

      allowDrop(node, node2, type){  // 节点发生位置变动
        if(node.level === node2.level && node.parent === node2.parent && type === "prev"){ // 只允许同父级内同级拖拽行为
          return true;
        }else{
          return false;
        }
      },

      nodeDrop(node, node2){ // 节点发生位置变动 -- 成功结束后
        let _this = this;
        let id = (node.data && node.data.id) || -1;
        let id2 = (node2.data && node2.data.id) || -1;
        if(id <= 0 || id2 <= 0){
          _this.FUNCS.error("请求参数有误!");
        }

        this.$axios.post("base/type/seq/exchange", {id : id, id2 : id2}).then(result =>{
          _this.FUNCS.success("节点交换成功！");
        }).catch(err =>{
          _this.FUNCS.error(err.message);
        })
      },

      cancelEdit(){ // 取消编辑
        this.editParam = {};
        this.beanData = {};
        this.changeNodeSelected(this.treeData, null, false);
      },

      setEditParams(title, subTitle){  // 设置更新弹窗信息
        this.$set(this.editParam, "title", title);
        this.$set(this.editParam, "subTitle", subTitle);
      },

      changeNodeSelected(nodes, selectedNode, isSelected){ // 改变节点被选中状态,遍历更新
        if(!nodes) return;
        let _this = this;
        nodes && nodes.filter(node => {
          if(typeof(isSelected) != 'undefined'){
            this.$set(node, "selected", isSelected);
          }
          if(selectedNode && node.id === selectedNode.id){
            this.$set(node, "selected", true);
          }else{
            this.$set(node, "selected", false);
          }
          if(node.childs && node.childs.length > 0){ // 判断是否有子节点，有的话就递归遍历子节点
            _this.changeNodeSelected(node.childs, selectedNode, isSelected);
          }
        });
      }
    },

    watch: {
      filterText(val) {
        this.$refs.treeData2.filter(val);
      }
    },
  }
</script>

<style scoped>
  #type-tree{
    padding: 0px 10px;
  }
  #type-tree .tree-title{
    font-weight: unset;
    border-left: 2px solid #409eff;
    padding-left: 5px;
    display: inline-block;
    line-height: 30px;
  }
  #type-tree .tree-title i{
    color: #e6a23c;
    font-size: 14px;
  }
  #type-tree .tree-content{
    display: flex;
  }
  #type-tree .block:nth-child(1){
    flex: 3;
    padding: 8px 0px 24px;
  }
  #type-tree .block:nth-child(1) .el-tree{
    margin-top: 10px;
  }
  #type-tree .block:nth-child(2){
    flex: 2;
  }

  /** 浮动编辑框
   */
  #type-tree .float_block{
    position: fixed;
    top: 150px;
    right: 80px;
    width: 500px;
    padding: 0px 50px 8px 0px;
    border: 1px solid #ebebeb;
    border-radius: 3px;
    transition: 0.2s;
  }
  #type-tree .float_block:hover{
    box-shadow: 0 0 8px 0 rgba(232, 237, 250, 0.6), 0 2px 4px 0 rgba(232, 237, 250, 0.5);
  }

  #type-tree .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 16px;
    padding-right: 8px;
  }
</style>

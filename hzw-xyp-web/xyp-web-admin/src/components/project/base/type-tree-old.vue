<template>
  <div id="type-tree">
    <!-- 标题 -->
    <div class="tree-title">
      <h3>类别管理</h3>
    </div>

    <div class="tree-content">
      <!-- 树状数据展示 -->
      <div class="block">
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText">
        </el-input>
        <el-tree
          ref="treeData2"
          :data="treeData.trees"
          :props="defaultProps"
          :filter-node-method="filterNode"
          node-key="id"
          draggable
          default-expand-all
          @node-click="handleNodeClick">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span><i class="el-icon-loading" v-if="data.selected"></i>{{ node.label }} &#45;&#45; {{data.checked}} &#45;&#45; {{data.hello}}</span>
          <span>
            <el-button type="text" size="mini" v-if="node.level < 3" @click.stop="selectedNode2(node, data)">
              选中测试
            </el-button>
            <el-button type="text" size="mini" v-if="node.level < 3" @click.stop="createNextNode(node, data)">
              添加下级
            </el-button>
            <el-button type="text" size="mini" @click.stop="createSomeNode(node, data)">
              添加同级
            </el-button>
            <el-button type="text" size="mini" @click.stop="removeNode(node, data)">
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
              <el-button type="primary" @click="" v-if="!beanData.id">新 增</el-button>
              <el-button type="primary" @click="" v-if="beanData.id">确 认 修 改</el-button>
              <el-button @click="cancelEdit" v-if="beanData.parentId && beanData.parentId > 0 || beanData.id">取 消</el-button>
            </el-form-item>
          </div>
        </el-form>
      </div>
    </div>
  </div>

</template>

<script>
  import TypeTreeRender from '@/components/project/base/type-tree-render';

  export default {
    name: "type-tree-old",
    info: "类别管理模块",
    data(){
      return {
        treeData : {trees:[]},
        beanData : {},
        editParam : {},
        filterText : "",
        isFirst : true,
        resolve : null,
        node : {},
        defaultProps: {
          children: 'children',
          label: 'name'
        }
      }
    },
    created() {
      this.getTreeData();
    },

    methods : {
      getTreeData(){
        this.treeData.trees = [{
          id : 1,
          name: '一级 1',
          selected : false,
          children: [{
            id : 2,
            name: '二级 1-1',
            selected : false,
            children: [{
              id : 3,
              selected : false,
              name: '三级 1-1-1'
            }]
          }]
        }, {
          id : 4,
          name: '一级 2',
          selected : false,
          children: [{
            id : 5,
            name: '二级 2-1',
            selected : false,
            children: [{
              id : 6,
              selected : false,
              name: '三级 2-1-1'
            }]
          }, {
            id : 7,
            name: '二级 2-2',
            selected : false,
            children: [{
              id : 8,
              selected : false,
              name: '三级 2-2-1'
            }]
          }]
        }];
      },

      renderContent(h,{node,data,store}){ // 加载树控件
        let _this = this;//指向vue
        return h(TypeTreeRender,{
          props: {
            DATA: data,//节点数据
            NODE: node,//节点内容
            STORE: store,//完整树形内容
          },
          on: {//绑定方法
            //nodeAdd: ((s,d,n) => _this.handleAdd(s,d,n)),
            selectedNode: ((s,d,n) => _this.selectedNode(s,d,n)),
            //nodeDel: ((s,d,n) => _this.handleDelete(s,d,n))
          }
        });
      },


      handleNodeClick(data) {
        console.log(data);
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },

      selectedNode(STORE,DATA,NODE){ // 节点选中测试
        console.log("节点选中测试", STORE);
        console.log("节点选中测试", DATA);
        console.log("节点选中测试", NODE);
        DATA.selected = !DATA.selected;

        /*console.log("节点选中测试222", this.treeData.trees);
        this.changeNodeChecked(this.treeData.trees, data);
        console.log("节点选中测试333", this.treeData.trees);*/
      },

      selectedNode2(node, data){ // 节点选中测试
        console.log("节点选中测试", node);
        console.log("节点选中测试", data);
        data.selected = !data.selected;
        this.changeNodeChecked(this.treeData.trees, data);
        /*console.log("节点选中测试222", this.treeData.trees);
        this.changeNodeChecked(this.treeData.trees, data);
        console.log("节点选中测试333", this.treeData.trees);*/
      },

      updateKeyChildren(key, node){
        console.log("update", key);
        console.log("update22", node);
      },

      createNextNode(node, data){ // 添加下级
        console.log("添加下级节点", node);
        this.editParam.title = "添加下级节点";
        this.editParam.subTitle = node.data.name;

        let parentData = node.data || {};
        this.beanData = {parentId : parentData.id || -1, parentName : parentData.name};
        console.log("添加下级节点22", this.editParam);
      },

      createSomeNode(node, data){ // 添加同级
        console.log("添加同级节点", node);
        console.log("添加同级节点111", data);
        this.editParam.title = "添加同级节点";
        this.editParam.subTitle = node.data.name;

        let parentData = node.parent.data || {};
        this.beanData = {parentId : parentData.id || -1, parentName : parentData.name};
        console.log("添加同级节点22", this.editParam);



      },

      removeNode(node){ // 删除节点
        console.log("删除节点", node);

      },

      cancelEdit(){ // 取消编辑
        this.editParam = {};
        this.beanData = {};
      },

      changeNodeChecked(nodes, checkedNode){ // 改变节点被选中状态,遍历更新
        if(!nodes || !checkedNode) return;
        let _this = this;
        nodes && nodes.filter(node => {
          if(node.id === checkedNode.id){
            node.selected = true;
            node.hello = "你好";
          }else{
            node.selected = false;
            node.hello = "";
          }
          if(node.children && node.children.length > 0){ // 判断是否有子节点，有的话就递归遍历子节点
            _this.changeNodeChecked(node.children, checkedNode);
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
    top: 25%;
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

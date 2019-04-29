<template>
  <div id="type-tree">
    <!-- 标题 -->
    <div class="tree-title">
      <h3>类别管理</h3>
    </div>

    <div class="tree-content">
      <!-- 树状数据展示 -->
      <div class="block">
        <el-input placeholder="输入关键字进行过滤" v-model="filterText"></el-input>

        <h-tree :nodes="treeData.trees"></h-tree>
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
  import HTree from '@/components/commons/h-tree';

  export default {
    name: "type-tree",
    info: "类别管理模块",
    data(){
      return {
        treeData : {trees:[]},
        beanData : {},
        editParam : {},
        filterText : "",
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
          children: [{
            id : 2,
            name: '二级 1-1',
            children: [{
              id : 3,
              name: '三级 1-1-1'
            }]
          }]
        }, {
          id : 4,
          name: '一级 2',
          children: [{
            id : 5,
            name: '二级 2-1',
            children: [{
              id : 6,
              name: '三级 2-1-1'
            }]
          }, {
            id : 7,
            name: '二级 2-2',
            children: [{
              id : 8,
              name: '三级 2-2-1'
            }]
          }]
        }];
      },
    },
    components:{HTree},
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
</style>

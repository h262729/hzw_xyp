<template>
    <div id="admin-list">
      <!-- 列表 -->
      <h-list ref="hList" :filter="filter" :columns="columns" :name="listName" :list-url="queryUrl" :remove-url="removeUrl" :create="create">
        <template slot="search">
          <el-input v-model="filter.name" size="small" placeholder="请输入文章标题" clearable></el-input>
        </template>
        <template slot="handle" slot-scope="scope">
          <el-button type="text" size="small" @click="edit(scope.row)">编辑</el-button>
        </template>
      </h-list>
    </div>
</template>

<script>
  import HList from '@/components/commons/h-list';

  export default {
    name: "label-list",
    data(){
      return {
        listName : "资讯文章列表", // 列表名称
        queryUrl : "/info/list", // 列表查询地址
        removeUrl : "/info/remove",
        data: {}, // 查询结果
        filter: {},  // 筛选条件
        columns : [], // 表格数据
        dialogTitle : "标签编辑",
        dialogVisible : false,
        beanData : {}, // 弹窗编辑信息
      };
    },
    created() {
      // 初始化
      this.init();
    },
    methods: {
      init(){ // 页面初始化
        // 自定义表头
        this.columns = [
          {label:"文章标题", prop:"title"},
          {label:"所属分类", prop:"typeId"},
          {label:"拥有标签", prop:"labelIds"},
          {label:"文章来源", prop:"source"},
          {label:"是否为管理员创建", prop:"createIsAdmin"},
          {label:"创建人", prop:"createUserName"},
          {label:"修改时间", prop:"updateTime"},
          {label:"创建时间", prop:"createTime"},
        ];
      },
      view(row) { // 查看
        let tab = {code:"info-view:" + row.id, name: "查看资讯文章 -【" + row.title.substring(0, 5) + "... 】", routerLink:"info-view", query:{id: row.id}};
        this.selectAsideMenu(tab);
      },
      edit(row) { // 编辑
        let tab = {code:"info-edit:" + row.id, name: "编辑资讯文章 -【" + row.title.substring(0, 5) + "... 】", routerLink:"info-edit", query:{id: row.id}};
        this.selectAsideMenu(tab);
      },
      create() { // 新增
        let tab = {code:"info-add", name: "新增资讯文章", routerLink:"info-add"};
        this.selectAsideMenu(tab);
      },
    },
    components:{HList},
    inject:['selectAsideMenu'],
  }
</script>

<style scoped>
</style>

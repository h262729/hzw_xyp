<template>
    <div id="admin-list">
      <h-list :filter="filter" :columns="columns" name="管理员列表" list-url="admin/list" :pageRoute="pageRoute" @gotoPage="selectAsideMenu">
        <template slot="search">
          <el-input v-model="filter.name" size="small" placeholder="请输入用户名" clearable></el-input>
          <el-input v-model="filter.nickname" size="small" placeholder="请输入昵称" clearable></el-input>
        </template>
        <template slot="handle" slot-scope="scope">
          <el-button type="text" size="small" @click="view(scope.row)">查看</el-button>
          <el-button type="text" size="small" @click="edit(scope.row)">编辑</el-button>
        </template>
      </h-list>
    </div>
</template>

<script>
  import HList from '@/components/commons/h-list';

  export default {
    name: "admin-list",
    data(){
      return {
        data: {}, // 查询结果
        filter: {},  // 筛选条件
        columns : [], // 表格数据
        pageRoute : {} // 操作路由信息
      };
    },
    created() {
      console.log("admin-list 被加载");
      // 初始化
      this.init();
    },
    methods: {
      init(){ // 页面初始化
        // 自定义表头
        this.columns = [
          {label:"用户名", prop:"name"},
          {label:"昵称", prop:"nickname"},
          {label:"联系电话", prop:"mobile"},
          {label:"联系邮箱", prop:"email"},
        ];

        // 可操作路由信息
        this.pageRoute = {
          add : {code:"admin-add", name: "新增管理员", routerLink:"admin-add"}
        };
      },
      view(row) { // 查看
        console.log("查看", row);
        let tab = {code:"admin-view:" + row.id, name: "查看管理员-【" + row.name + "】", routerLink:"admin-view", query:{id: row.id}};
        this.selectAsideMenu(tab);
      },
      edit(row) { // 编辑
        console.log("编辑", row);
        let tab = {code:"admin-edit:" + row.id, name: "编辑管理员-【" + row.name + "】", routerLink:"admin-edit", query:{id: row.id}};
        this.selectAsideMenu(tab);
      }
    },
    components:{HList},
    inject:['selectAsideMenu'],
  }
</script>

<style scoped>
</style>

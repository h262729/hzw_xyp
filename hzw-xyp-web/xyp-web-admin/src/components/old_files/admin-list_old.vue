<template>
    <div id="admin-list">
      <div class="list-search">
        <span class="list-name">查询</span>
        <el-input v-model="filter.name" size="small" placeholder="请输入用户名" clearable></el-input>
        <el-input v-model="filter.nickname" size="small" placeholder="请输入昵称" clearable></el-input>
        <el-button type="primary" size="small">搜 索</el-button>
      </div>
      <hr class="list-hr"/>

      <!-- 查询结果 -->
      <div class="list-header">
        <span class="list-name">管理员列表 <span>共<strong>{{data.totalCount || 0}}</strong>条数据</span></span>
        <el-button type="primary" size="small" >新 增</el-button>
        <el-button type="danger" size="small" >删 除</el-button>
      </div>
      <el-table :data="data.rows" border style="width: 100%">
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column prop="name" label="用户名"></el-table-column>
        <el-table-column prop="nickname" label="昵称"></el-table-column>
        <el-table-column prop="mobile" label="联系电话"></el-table-column>
        <el-table-column prop="email" label="联系邮箱"></el-table-column>
        <el-table-column fixed="right" label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small">查看</el-button>
            <el-button type="text" size="small">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页条 -->
      <div class="list-page">
        <el-pagination
          background
          layout="total, sizes, prev, pager, next, jumper"
          :total="data.totalCount || 0"
          :page-size="data.pageSize || 10"
          :page-sizes="[10, 15, 20]">
        </el-pagination>
      </div>
    </div>
</template>

<script>
    export default {
      name: "admin-list",
      data(){
        return {
          data: {}, // 查询结果
          filter: {}  // 筛选条件
        };
      },
      created() {
        console.log("admin-list 被加载");

        // 模拟数据
        /*this.data = {
          pageSize: 15, // 分页大小
          pageNum: 1,  // 页码
          pageCount: 12,  // 总分页数
          totalCount: 1200, // 结果总数
          rows : [
            {id:1, name:"hhh", nickname:"你和O", mobile:"13538774125", email:"1797532841@qq.com", status:1},
            {id:2, name:"qqq", nickname:"你是否和O", mobile:"135387451125", status:1},
            {id:3, name:"ada", nickname:"阿达的", mobile:"13538773415", email:"1797538841@qq.com", status:1},
            {id:4, name:"qeqeq", nickname:"阿萨达", mobile:"13538741125", email:"1797732841@qq.com", status:1},
          ]
        };*/

        // 获取数据
        this.getListData();
      },
      methods: {
        getListData : function (filter, pageNum, pageSize) { //获取数据
          console.log("获取列表数据");
          let _this = this;
          this.$axios.get("admin/list", {params: {filter : filter, pageNum : pageNum, pageSize : pageSize}}).then(function(result){
            _this.data = result;
          }).catch(function(err){
            _this.FUNCS.error(err.message);
          })
        }
      }
    }
</script>

<style scoped>
  #admin-list{
    padding: 0px 10px;
  }
  .list-name{
    font-weight: unset;
    border-left: 2px solid #409eff;
    padding-left: 5px;
    display: inline-block;
    line-height: 30px;
  }
  .list-name span{
    font-size: 14px;
    color: #999;
  }
  .list-name span strong{
    color: #f70;
    font-size: 16px;
    padding: 0px 5px;
  }
  .list-search{

  }
  .list-search .el-input{
    width: 150px;
  }
  .list-header{
    margin: 5px auto;
  }
  .list-hr{
    border: none;
    height: 1px;
    background: #e4e4e4;
  }
  .list-page{
    margin: 10px auto;
  }
</style>

<template>
  <div id="list-base">
    <div class="list-search">
      <span class="list-name">查询</span>
      <!-- 搜索条件插槽 -->
      <slot name="search"></slot>

      <el-button type="primary" size="small" @click="query">搜 索</el-button>
    </div>
    <hr class="list-hr"/>

    <!-- 查询结果 -->
    <div class="list-header">
      <span class="list-name">{{name}} <span>共<strong>{{data.totalCount || 0}}</strong>条数据</span></span>
      <el-button type="primary" size="small" @click="toCreatePage">新 增</el-button>
      <el-button type="danger" size="small" @click="deleteData">删 除</el-button>
    </div>

    <!-- 表格 -->
    <el-table
      border
      style="width: 100%"
      :data="data.rows"
      v-loading="loading"
      @select="select"
      @select-all="selectAll"
    >
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column v-for="(item, index) in columns" :key="index" :prop="item.prop" :label="item.label"></el-table-column>
      <el-table-column fixed="right" label="操作">
        <!-- 自定义操作插槽 -->
        <template slot-scope="scope">
          <slot name="handle" :row="data.rows[scope.$index]"></slot>
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
        :page-sizes="[10, 15, 20]"
        @size-change="sizeChange"
        @current-change="currentChange"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
    export default {
        name: "h-list",
      data(){
        return {
          data: {}, // 查询结果
          currentPageNum : this.pageNum || 1, // 当前页码
          currentPageSize : this.pageSize || 10, // 当前分页大小
          loading : false, // 加载动画
          selectRows : [], // 选中的数据行
        };
      },
      created() {
        console.log("h-list 被加载");
        // 获取数据
        this.query();
      },
      methods: {
        query : function () { //获取数据
          if(!this.listUrl){
            return this.FUNCS.error("无效请求地址！");
          }
          let _this = this;
          let _filter = this.filter || {};  // 筛选
          this.loading = true;  // 显示加载动画
          this.$axios.get(this.listUrl, {params: {filter : _filter, pageNum : _this.currentPageNum, pageSize :  _this.currentPageSize}}).then(result =>{
            _this.data = result;
          }).catch(err =>{
            _this.FUNCS.error(err.message);
          }).finally(() =>{
            _this.loading = false; // 关闭加载动画
          })
        },

        toCreatePage() {  // 前往新增页面
          let tab = this.pageRoute.add || {};
          this.$emit('gotoPage', tab);
        },

        sizeChange(pageSize) {  // 每页条数改变
          this.currentPageSize = pageSize;
          this.query();
        },

        currentChange(pageNum){  // 当前页改变
          this.currentPageNum = pageNum;
          this.query();
        },

        deleteData(){ // 删除数据
          console.log("删除数据");
          let _this = this;
          let deleteIds = [];
          this.selectRows.filter(row => row.id && deleteIds.push(row.id));
          console.log("删除数据", deleteIds);
          _this.$axios.post("admin/remove", {ids : deleteIds}).then(result => {
            _this.FUNCS.success("数据删除成功！影响条数:" + (result && result.num || 0) + "条");
            _this.query();
          }).catch(err => {
            _this.FUNCS.error(err.message);
          })
        },

        select(selection, row){ // 选择某一行
          this.selectRows = selection;
        },

        selectAll(selection){  // 选中所有行
          this.selectRows = selection;
        }
      },
      props: {
        filter : Object,  // 筛选条件
        pageNum : Number, // 页码
        pageSize : Number,  // 分页大小
        columns : Array,  // 显示列
        name : String,  // 当前列表名称
        listUrl : String,  // 请求连接
        pageRoute : Object,  // 跳转路由信息
        gotoPage : Function,  // 前往下个页面的函数
      }
    }
</script>

<style scoped>
  #list-base{
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

<template>
    <div id="admin-list">
      <!-- 列表 -->
      <h-list ref="hList" :filter="filter" :columns="columns" :name="listName" :list-url="queryUrl" :remove-url="removeUrl" :create="create">
        <template slot="search">
          <el-input v-model="filter.name" size="small" placeholder="请输入标签名称" clearable></el-input>
        </template>
        <template slot="handle" slot-scope="scope">
          <el-button type="text" size="small" @click="edit(scope.row)">编辑</el-button>
        </template>
      </h-list>

      <!-- 编辑弹窗 -->
      <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" :close-on-click-modal="false" width="30%">
        <el-form :model="beanData" label-width="100px" label-position="left">
          <el-form-item label="标签名称" >
            <el-input v-model="beanData.name" autofocus></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="save(beanData)">{{beanData.id > 0 ? "确 定 修 改" : "确 定 新 增"}}</el-button>
        </div>
      </el-dialog>
    </div>
</template>

<script>
  import HList from '@/components/commons/h-list';

  export default {
    name: "label-list",
    data(){
      return {
        listName : "标签列表", // 列表名称
        queryUrl : "/base/label/list", // 列表查询地址
        removeUrl : "/base/label/remove",
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
          {label:"标签名称", prop:"name"},
          {label:"是否为管理员创建", prop:"createIsAdmin", options : [{key:-1, value:"否"},{key:1, value:"是"}]}
        ];
      },

      save(beanData){ // 保存
        let _this = this;
        this.$axios.post("base/label", beanData).then(result =>{
          _this.FUNCS.success("标签保存成功！");
          _this.beanData = (result && result.beanData) || {};
          // 重新查询列表数据 --调用子组件的查询函数
          _this.$refs.hList.query();
        }).catch(err =>{
          _this.FUNCS.error(err.message);
        })
      },

      edit(row) { // 编辑
        if(!row.id){
          return this.FUNCS.warning("获取id失败！");
        }
        this.dialogTitle = "标签编辑 - " + row.name;
        this.beanData = row;
        this.dialogVisible = true;
      },

      create(){ // 新增
        this.dialogTitle = "新增标签";
        this.beanData = {};
        this.dialogVisible = true;
      }
    },
    components:{HList},
  }
</script>

<style scoped>
</style>

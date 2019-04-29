<template>
    <div id="admin_edit">
      <div>
        <span class="edit-name">基础信息 <i>(默认初始密码为：123456)</i></span>
      </div>
      <el-form :model="beanData" ref="beanData" @submit.native.prevent :rules="rules" label-width="100px" >
        <el-row>
          <el-col :span="10">
            <el-form-item prop="name" label="用户名" :disabled="!beanData.id || beanData.id <= 0">
              <el-input v-model="beanData.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item prop="nickname" label="昵称">
              <el-input v-model="beanData.nickname"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item  prop="mobile" label="联系电话">
              <el-input v-model="beanData.mobile"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item prop="email" label="联系邮箱">
              <el-input v-model="beanData.email"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <div class="edit-btn">
          <el-form-item size="large" v-if="id == -1">
            <el-button type="primary" @click="onSubmit()">新 增</el-button>
            <el-button @click="resetForm('beanData')">重 置</el-button>
          </el-form-item>
          <el-form-item size="large" v-if="id != -1">
            <el-button type="primary" @click="onSubmit()">确 认 修 改</el-button>
            <el-button @click="initPwd()">初 始 化 密 码</el-button>
          </el-form-item>
        </div>
      </el-form>
    </div>
</template>

<script>
  import Validate from "@/assets/common/validate.js"
  export default {
    name: "admin-edit",
    data() {
      return {
        title: "新增/编辑管理员",
        beanData: {},
        rules : {
          name : [{required: true, message: "用户名不能为空", trigger: "blur"},{validator: Validate.userName}],
          nickname : {required: true, message: "昵称不能为空", trigger: "blur"},
          mobile : [{ validator: Validate.mobile}],
          email : [{ validator: Validate.email}],
        }
      };
    },
    created() {
      console.log("admin-edit 被加载", this.id);
      if(this.id > 0){ // 编辑
        this.getData(this.id);
      }else{  // 新增

      }
    },
    watch: {
      '$route' (to, from) { // 监听路由变化
        // 注意：离开缓存的执行要在进入之前，因为进入读取数据导致本页数据被刷新了
        let beanData = this.beanData;
        this.beanData = {};
        if(from.name === "admin-edit" || from.name === "admin-add"){  // 离开
          // 离开缓存当前页面数据
          this.FUNCS.cacheRouteData(from, beanData);
        }

        if(to.name === "admin-edit" || to.name === "admin-add"){  // 进入
          // 获取sessionStorage中的已缓存的表单数据
          let data = this.FUNCS.getRouteData(to);
          if(!data){ // 没有缓存数据的话就去请求
            this.getData(this.id);
          }else{
            this.beanData = data || {};
          }
        }
      }
    },
    methods: {
      getData(id){ // 获取数据
        console.log("获取实体数据", id);
        if(!id || id <= 0){
          return;
        }

        // 发起请求获取实体数据
        let _this = this;
        this.$axios.get("admin/get", {params : {id : id}}).then(function (result) {
          console.log("获取到的数据", result);
          _this.beanData = result && result.beanData || {};
        }).catch(function (error) {
          console.log("错误信息", error);
        })

      },

      onSubmit : function() { // 提交表单
        let _this = this;
        this.$refs.beanData && this.$refs.beanData.validate().then(function () {
          return _this.$axios.post("admin", _this.beanData)
        }).then(function (result) {
          _this.beanData = (result && result.beanData) || {};
          if(_this.id <= 0){ // 新增操作
            _this.FUNCS.success("新增管理员成功！");
            _this.removeTab(_this.$route.meta.code || "admin-add"); // 关闭当前的标签页
          }else{ // 保存操作
            _this.FUNCS.success("保存成功！");
          }
        }).catch(function (error) {
          return error.message && _this.FUNCS.error(error.message);
        })
      },

      resetForm : function(formName){ // 重置表单
        this.$nextTick(() => {
          this.$refs[formName] && this.$refs[formName].resetFields();
        });
      },

      initPwd : function () { // 初始化密码
        let _this = this;
        this.$axios.post("admin/pwd/init", {id : _this.id}).then(result => {
          _this.FUNCS.success("初始化密码成功！");
        }).catch(error => {
          return error.message && _this.FUNCS.error(error.message);
        })
      }

    },
    computed:{
      id : function(){
        return this.$route.query.id || -1;
      }
    },
    inject:['removeTab']
  }
</script>

<style scoped>
  #admin_edit{
    margin: auto 10px;
    width: 60%;
  }
  .edit-name{
    font-weight: unset;
    border-left: 2px solid #409eff;
    padding-left: 5px;
    display: inline-block;
    line-height: 30px;
    margin: 5px auto;
  }
  .edit-name i{
    color: #e6a23c;
    font-size: 14px;
  }
  .edit-btn{
    margin-top: 20px;
    text-align: center;
  }
  .el-form .el-row:nth-child(1){
    border-top: 1px solid #e0e0e0;
  }
  .el-row{
    border: 1px solid #e0e0e0;
    border-top: none;
    padding: 20px 0px;
  }
  .el-row .el-col{
    margin-right: 10px;
  }
  .el-form-item{
    margin: auto;
  }
</style>

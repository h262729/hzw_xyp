<template>
    <!-- 登录组件 -->
    <div id="login">
      <div class="login-content">
        <div class="login-content-main">
          <div class="header">
            <h2>馨语阁</h2>
            <p>后台管理网站</p>
          </div>
          <div>
            <el-input v-model="data.userName" placeholder="请输入用户名">
              <template slot="prepend">
                <i class="icon iconfont icon-people"></i>
              </template>
            </el-input>
          </div>
          <div>
            <el-input placeholder="请输入密码" v-model="data.pwd" show-password>
              <template slot="prepend">
                <i class="icon iconfont icon-lock"></i>
              </template></el-input>
          </div>
          <div class="row-vcode">
            <div>
              <el-input placeholder="请输入验证码" v-model="data.vCode"></el-input>
            </div>
            <div>
              <img :src="verifySrc" @click="getVerify">
            </div>
          </div>
          <div class="btn_login">
            <el-button type="primary" @click="login">登 录</el-button>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
  export default {
    name: "login",
    data(){
      return{
        data: {},
        verifySrc: "",
      }
    } ,
    created() {
      this.getVerify();
    },
    methods:{
      getVerify : function(){
        // 获取验证码
        this.verifySrc = this.GLOBAL.BASE_URL + "api/base/image/verify?" + new Date().getTime();
      },
      login : function(){
        // 登录
        console.log("data", JSON.stringify(this.data));
        let $this = this;
        this.$axios.post("login", this.data).then(function(result){
          window.localStorage.setItem("user", JSON.stringify(result));
          $this.FUNCS.success("登录成功");
          $this.$router.push("/home");
        }).catch(function(err){
          $this.FUNCS.error(err.message);
        })
      }
    }
  }
</script>

<style scoped>
  #login{
    position: fixed;
    background: #f2f2f2;
    padding: 0px;
    margin: 0px;
    width: 100%;
    height: 100%;
  }
  .login-content{
    position: relative;
    left: 0;
    top: 0;
    padding: 110px 0;
    min-height: 100%;
    box-sizing: border-box;
  }
  .login-content-main{
    width: 375px;
    margin: 0 auto;
    box-sizing: border-box;
  }
  .login-content-main .header{
    text-align: center;
  }
  .login-content-main .header h2{
    margin-bottom: 10px;
    font-weight: 300;
    font-size: 30px;
    color: #000;
  }
  .login-content-main .header p{
    font-weight: 300;
    color: #999;
  }
  .login-content-main div{
    margin-bottom: 15px;
  }
  .login-content-main div input{
    background: #ffffff;
  }
  .login-content-main .btn_login button{
    width: 100%;
  }
  .login-content-main .row-vcode{
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content : space-between;
  }
  .login-content-main .row-vcode div{
    margin: 0px;
  }
  .login-content-main .row-vcode div:nth-child(1){
    flex:3 ;
  }
  .login-content-main .row-vcode div:nth-child(2){
    flex:1;
    margin-left: 15px;
  }
  .login-content-main .row-vcode div:nth-child(2) img{
    height: 40px;
    float: right;
    cursor: pointer;
  }
  .iconfont{
    font-size: 20px;
    color:#409eff;
  }
</style>

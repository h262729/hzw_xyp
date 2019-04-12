<template>
  <!-- 主界面 - 头部 -->
  <div id="header">
    <div class="header-menu-left">
      <el-menu
        :default-active="activeIndex"
        mode="horizontal"
        @select="handleSelect"
        background-color="#409eff"
        text-color="#fff"
        active-text-color="#FF5722">
        <!--<el-menu-item index="1">项目管理</el-menu-item>-->
        <el-menu-item index="1">基础管理</el-menu-item>
      </el-menu>
    </div>
    <div class="header-menu-right">
      <ul>
        <li>
          <el-dropdown @visible-change="iconStatus = !iconStatus">
            <span class="el-dropdown-link">
              {{user.nickname || "未知用户"}}
              <i class="el-icon-arrow-down el-icon--right" v-show="iconStatus"></i>
              <i class="el-icon-arrow-up el-icon--right" v-show="!iconStatus"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>基本信息</el-dropdown-item>
              <el-dropdown-item>修改密码</el-dropdown-item>
              <hr/>
              <el-dropdown-item>退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        activeIndex: '1',
        iconStatus: false,
        user:{}
      };
    },
    created(){
      // 获取登录用户信息
      console.log("当前登录用户", window.localStorage.getItem("user"));
      this.user = JSON.parse(window.localStorage.getItem("user") || {});
      console.log("当前登录用户", this.user);
    },
    methods: {
      handleSelect(key, keyPath) {
        //console.log(key, keyPath);
      },
    }
  }
</script>

<style scoped>
  #header{
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content : space-between;
    height: 60px;
    background: #409eff;
  }
  #header .header-menu-left{
    flex: 8;
  }
  #header .header-menu-right{
    flex: 2;
    padding-right: 50px;
    display: flex;
    flex-direction:row-reverse;
  }
  #header .header-menu-right ul li{
    float: left;
    margin-left: 15px;
  }
  .el-dropdown-link {
    cursor: pointer;
    color: #fff;
  }
</style>

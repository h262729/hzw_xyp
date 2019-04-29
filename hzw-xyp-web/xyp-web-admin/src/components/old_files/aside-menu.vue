<template>
  <!-- 主界面 - 侧边栏菜单 -->
  <div id="aside-menu">
    <div class="aside-header">
      <h3>馨 语 阁</h3>
    </div>
    <div class="menu">
      <el-menu
        default-active="1-1"
        class="el-menu-vertical-demo"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b">
        <el-submenu index="1">
          <template slot="title">
            <i class="el-icon-location"></i>
            <span>管理员管理</span>
          </template>
          <el-menu-item index="1-1">管理员列表</el-menu-item>
          <el-menu-item index="1-2">管理员列表222</el-menu-item>
        </el-submenu>
      </el-menu>
    </div>
  </div>
</template>

<script>
  export default {
    methods: {
    }
  }
</script>

<style scoped>
  #aside-menu{
    display: flex;
    flex-direction: column;
    height: 100vh;
  }
  #aside-menu .aside-header{
    text-align: center;
    background: #409eff;
    line-height: 60px;
    height: 60px;
    color: #fff;
    width: 200px;
  }
  #aside-menu .el-menu{
    border: none;
  }
  #aside-menu .menu{
    height: 100%;
    background: #545c64;
  }
</style>

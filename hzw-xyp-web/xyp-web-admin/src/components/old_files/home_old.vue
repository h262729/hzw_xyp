<template>
  <!-- 主界面 -->
  <div id="home">
    <el-container>
      <!-- 侧边菜单栏 -->
      <el-aside width="200px">
        <router-view name="asideMenu" />
      </el-aside>
      <!-- 主内容区域 -->
      <el-container>
        <!-- 页眉 -->
        <el-header>
          <router-view name="header" />
        </el-header>
        <!-- 内容显示区域 -->
        <el-main>
          <router-view name="content" />
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
</script>

<style scoped>
  .el-header{
    padding: 0px;
    margin: 0px;
  }
  .el-aside{
    overflow: unset;
    overflow-y:visible;
  }
  .el-main{
    padding: 0px;
  }
</style>

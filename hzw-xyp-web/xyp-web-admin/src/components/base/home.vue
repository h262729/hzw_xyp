<template>
  <!-- 主界面 -->
  <div id="home">
    <el-container>
      <!-- 侧边菜单栏 -->
      <el-aside width="200px">
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
      </el-aside>

      <!-- 主内容区域 -->
      <el-container>
        <!-- 页眉 -->
        <el-header>
          <div id="header">
            <div class="header-menu-left">
              <el-menu
                :default-active="activeIndex"
                mode="horizontal"
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
        </el-header>

        <!-- 内容显示区域 -->
        <el-main>
          <div id="content">
            <el-tabs v-model="activity_tab" type="card" @tab-click="clickTab" @tab-remove="removeTab">
              <el-tab-pane label="首页">
                <router-view name="adminHome"></router-view>
              </el-tab-pane>
              <el-tab-pane
                v-for="(item, index) in tabs"
                :key="item.code"
                :label="item.title"
                :name="item.code"
                closable
              >
                <router-view :name="item.content"></router-view>
              </el-tab-pane>
            </el-tabs>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        activeIndex: '1',
        iconStatus: false,
        user:{},
        activity_tab: '1',
        tabs: [{
          title: 'Tab 1',
          code: '1',
          content: 'adminEdit'
        }, {
          title: 'Tab 2',
          code: '2',
          content: 'adminList'
        }]
      };
    },
    created(){
      // 获取登录用户信息
      console.log("当前登录用户", window.localStorage.getItem("user"));
      this.user = JSON.parse(window.localStorage.getItem("user") || {});
      console.log("当前登录用户", this.user);

      //this.$router.push("/home/admin-edit");
    },
    methods: {
      addTab(targetName) {
        let newTabName = ++this.tabIndex + '';
        this.editableTabs2.push({
          title: 'New Tab',
          name: newTabName,
          content: 'New Tab content'
        });
        this.editableTabsValue2 = newTabName;
      },
      removeTab(targetName) {
        let tabs = this.editableTabs2;
        let activeName = this.editableTabsValue2;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.name;
              }
            }
          });
        }
        this.editableTabsValue2 = activeName;
        this.editableTabs2 = tabs.filter(tab => tab.name !== targetName);
      },
      clickTab(tab, event){ // 点击标签
        console.log(tab, event);
      }
    }
  }
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

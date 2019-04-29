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
              :default-active="activity_tab"
              class="el-menu-vertical-demo"
              background-color="#545c64"
              text-color="#fff"
              active-text-color="#ffd04b">
              <el-submenu v-for="(item, index) in asideMenu" :key="item.id" :index="item.code">
                <template slot="title">
                  <i class="el-icon-location"></i>
                  <span>{{item.name || "未知"}}</span>
                </template>
                <el-menu-item v-for="(child, index) in item.children"
                              :index="child.code" :key="child.id"
                              @click="selectAsideMenu(child)">
                  {{child.name || "未知菜单"}}
                </el-menu-item>
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
            <!-- 顶部菜单 -->
            <div class="header-menu-left">
              <el-menu
                :default-active="active_header_menu_index"
                mode="horizontal"
                background-color="#409eff"
                text-color="#fff"
                active-text-color="#FF5722"
                @select="selectHeaderMenu">
                <el-menu-item v-for="(menu, index) in headerMenu" :index="menu.code" :key="menu.id">{{menu.name || "未知菜单"}}</el-menu-item>
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
            <el-tabs v-model="activity_tab" type="card" @tab-remove="removeTab" @tab-click="selectTab">
              <el-tab-pane
                v-for="(item, index) in tabs"
                :key="item.code"
                :label="item.name"
                :name="item.code"
                :closable="index != 0"
              >
              </el-tab-pane>
            </el-tabs>
            <div>
              <keep-alive :include="cachePages">
                <router-view></router-view>
              </keep-alive>
            </div>
          </div>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import Menu from "@/assets/common/menu.js";
  import {mapState} from "vuex";

  export default {
    data() {
      return {
        iconStatus: false,
        user:{},
        activity_tab: "home",
        active_header_menu_index: '1',
        headerMenu: [], // 顶部菜单
        asideMenu: [],  // 侧边栏菜单
      };
    },
    created(){
      // 获取登录用户信息
      this.user = JSON.parse(window.localStorage.getItem("user") || {});

      // 菜单获取
      this.getMenu();
    },
    methods: {
      getMenu(menuIndex){  // 菜单获取
        this.headerMenu = Menu.headerMenu;
        let asideMenu = Menu.asideMenu;

        let active_header_menu = {};
        this.headerMenu.forEach(menu => {
          if(menuIndex && menu.code === menuIndex){
            active_header_menu = menu;
          }
          if(!menuIndex && menu.enabled === 1){
            active_header_menu = menu;
          }
        });
        if(!active_header_menu){
          return false;
        }
        this.active_header_menu_index = active_header_menu.code;
        console.log("当前激活的顶部菜单", active_header_menu);

        this.asideMenu = asideMenu[active_header_menu.code];
        console.log("获取到的侧边栏菜单", this.asideMenu);

        this.activity_tab = active_header_menu.code;
        // 初始化并跳转到首页
        let _tabs = [{name:"首页", code:active_header_menu.code, routerLink:active_header_menu.home}];
        // 更新store数据
        this.$store.commit('replaceTabs', _tabs);
        this.$store.commit('replaceCachePages', []);

        this.$router.replace( (active_header_menu.baseUrl || "/home/admin") + "/" + active_header_menu.home);
      },

      selectHeaderMenu(menuIndex){ // 顶部菜单选中事件
        this.getMenu(menuIndex);
      },

      selectAsideMenu(menu){ // 侧边栏菜单选中事件
        console.log("侧边栏菜单选中事件", menu);
        // 添加新的标签页
        let _index = -1;
        let _tabs = this.tabs;
        let _cachePages = this.cachePages;

        _tabs.forEach((tab, index) => {
          if(tab.code === menu.code){
            _index = index;
          }
        });
        if(_index === -1){
          _tabs.push(menu);
        }else{  // 更新标签信息，防止重复打开同一页面
          _tabs[_index] = menu
        }

        // 缓存页面组
        let hasPage = false;
        _cachePages.forEach(page => {
          if(page === menu.routerLink){
            hasPage = true;
          }
        });
        if(!hasPage){
          _cachePages.push(menu.routerLink);
        }

        this.activity_tab = menu.code;
        // 更新store数据
        this.$store.commit('replaceTabs', _tabs);
        this.$store.commit('replaceCachePages', _cachePages);

        // 跳转路由
        this.changeRoute(menu);
      },

      removeTab(tabName) {  // 删除标签页
        let _tabs = this.tabs;
        let _cachePages = this.cachePages;
        let activeName = this.activity_tab;
        if (activeName === tabName) {
          _tabs.forEach((tab, index) => {
            if (tab.code === tabName) {
                let nextTab = _tabs[index + 1] || _tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.code;
              }
            }
          });
        }
        let removeTab = null; // 要删除的标签页
        let activityTab = null; // 当前显示的标签页
        _tabs = _tabs.filter(tab => {
          if(tab.code === activeName){
            activityTab = tab;
          }
          if(tab.code !== tabName){
            return this;
          }else{
            removeTab = tab;
          }
        });
        if(removeTab){  // 将标签页从缓存页面组中删除
          _cachePages = _cachePages.filter(page => page !== removeTab.routerLink);
        }

        this.activity_tab = activeName;
        // 更新store数据
        this.$store.commit('replaceTabs', _tabs);
        this.$store.commit('replaceCachePages', _cachePages);

        // 移除存在sessionStorage中的页面数据
        this.FUNCS.clearRouteData(removeTab);

        if(activityTab){  // 切换当前页面的显示路由
          this.changeRoute(activityTab);
        }
      },

      selectTab(tab, event){  // 选中标签页 | 切换显示显示内容
        let _tab = this.tabs[tab.index] || {};
        this.changeRoute(_tab);
      },

      changeRoute(_tab){  // 改变路由
        if(_tab.params){
          this.$router.replace({name: _tab.routerLink, params: _tab.params});
        }else if(_tab.query){
          this.$router.replace({path: _tab.routerLink, query: _tab.query});
        }else{
          this.$router.replace(_tab.routerLink);
        }
      }
    },
    computed:{
      ...mapState({
        tabs: state => state.tabs || [], // 标签组
        cachePages: state => state.cachePages || [],  // 缓存页面
      })
    },
    provide(){
      return{
        selectAsideMenu:this.selectAsideMenu,
        removeTab:this.removeTab, // 删除标签页
      }
    },
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
    background: #c0c0c017;
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
  #header .el-dropdown-link {
    cursor: pointer;
    color: #fff;
  }
</style>

// 项目入口文件
import Vue from 'vue';
import VueRouter from 'vue-router';

// 导入app根组件
import app from './App';
// 导入自定义路由
import router from './router/router';
// 导入自定义配置
import axios from './assets/common/axiosHttp';

// 导入element-ui
import ElementUI from 'element-ui';
import "element-ui/lib/theme-chalk/index.css";

// 导入图标库
import "./lib/icons/ali-icon/iconfont.css";

// 导入自定义公共样式
import "./assets/css/common.css";
// 导入全局常量
import global_ from "./assets/common/constant.js";
// 导入全局函数
import funcs_ from "./assets/common/funcs.js";

Vue.use(VueRouter);
Vue.use(ElementUI);

Vue.prototype.$axios = axios;
Vue.prototype.GLOBAL = global_;
Vue.prototype.FUNCS = funcs_;

var vm = new Vue({
  el: "#app",
  render: c => c(app),
  router,
  methods: {
    toLogin: function () {
      // 判断本地是否存有当前登录用户信息，没有就跳转到登录页面
      if(!window.localStorage.getItem("user")){
        this.$router.push("/login");
      }
    }
    
  },
  created: function () {
    this.toLogin();
  }
});

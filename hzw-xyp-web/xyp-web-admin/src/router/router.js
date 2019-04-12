import VueRouter from 'vue-router';

import login from '@/components/base/login';
import home from '@/components/base/home';

// 基础管理路由
import adminHome from '@/components/admin/admin-home';
import adminList from '@/components/admin/user/admin-List';
import adminEdit from '@/components/admin/user/admin-edit';
import adminView from '@/components/admin/user/admin-view';


var router = new VueRouter({
  routes: [
    {path: "/", redirect: "/home"},
    {path: "/login", component: login, name: "login"},
    {path: "/home", component: home, children:[
        {path: "admin-home", component: adminHome, name: "admin-home"},
        {path: "admin-list", component: adminList},
        {path: "admin-edit", component: adminEdit},
        {path: "admin-view", component: adminView},
        {path: "/", components:{
            default: adminHome,
            adminHome: adminHome,
            adminList: adminList,
            adminEdit: adminEdit,
            adminView: adminView,
          }
        }
      ]
    },
  ]
});

export default router;

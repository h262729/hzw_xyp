import VueRouter from 'vue-router';

import login from '@/components/base/login';
import home from '@/components/base/home';
import ErrorPage from '@/components/base/error';

// 基础管理路由
import adminHome from '@/components/admin/admin-home';
import adminList from '@/components/admin/user/admin-List';
import adminEdit from '@/components/admin/user/admin-edit';
import adminView from '@/components/admin/user/admin-view';

// 项目管理模块路由
import projectHome from '@/components/project/project-home';
import typeTree from '@/components/project/base/type-tree';
import labelList from '@/components/project/base/label-list';
import infoList from '@/components/project/info/info-list';
import infoEdit from '@/components/project/info/info-edit';


const router = new VueRouter({
  routes: [
    {path: "/", redirect: "/home/admin"},
    {path: "/login", component: login, name: "login"},
    {path: "/home/admin", component: home, children:[
        {path: "/", redirect: "admin-home"},
        {path: "admin-home", component: adminHome, name: "admin-home", meta: {title:"首页" }},
        {path: "admin-list", component: adminList, name: "admin-list", meta: { keepAlive: true, title:"管理员列表", code:"admin-list"}},
        {path: "admin-add", component: adminEdit, name: "admin-add",   meta: { keepAlive: true, title:"管理员新增", code:"admin-add"}},
        {path: "admin-edit", component: adminEdit, name: "admin-edit",  meta: { keepAlive: true, title:"管理员编辑", code:"admin-edit"}},
        {path: "admin-view", component: adminView, name: "admin-view",  meta: { keepAlive: true, title:"管理员查看", code:"admin-view"}},
        {path: "*", component: ErrorPage},
      ]
    },
    {path: "/home/project", component: home, children:[
        {path: "/", redirect: "project-home"},
        {path: "project-home", component: projectHome, name: "project-home", meta: {title:"首页" }},
        {path: "type-tree", component: typeTree, name: "type-tree", meta: { keepAlive: true, title:"类别管理", code:"type-tree"}},
        {path: "label-list", component: labelList, name: "label-list", meta: { keepAlive: true, title:"标签管理", code:"label-list"}},

        {path: "info-list", component: infoList, name: "info-list", meta: { keepAlive: true, title:"资讯文章列表", code:"info-list"}},
        {path: "info-add", component: infoEdit, name: "info-add",   meta: { keepAlive: true, title:"资讯文章新增", code:"info-add"}},
        {path: "info-edit", component: infoEdit, name: "info-edit",  meta: { keepAlive: true, title:"资讯文章编辑", code:"info-edit"}},
        {path: "*", component: ErrorPage},
      ]
    },
    {path: "*", redirect: "/"}
  ]
});

export default router;

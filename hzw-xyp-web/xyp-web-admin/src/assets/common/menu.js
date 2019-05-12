// 菜单配置

// 顶部菜单
/**
 * 顶部菜单
 * code : 唯一标识
 * home : 默认首页路由
 * baseUrl : 基础路由 -- 方便根据模块不同划分路由
 * enabled : 1 为默认选中
 * @type {*[]}
 */
const headerMenu = [
  {id:1, name:"基础管理", code:"base-manage", home:"admin-home", baseUrl:"/home/admin", enabled:0},
  {id:2, name:"项目管理", code:"project-manage", home:"project-home", baseUrl:"/home/project", enabled:1},
]

// 侧边栏菜单
const asideMenu = {
  "base-manage" : [
    {id:1, name:"管理员菜单", code:"admin", children:[
        {id:2, name:"管理员列表", code:"admin-list", routerLink:"admin-list"},
        {id:3, name:"新增管理员", code:"admin-add", routerLink:"admin-add"}
      ]
    }
  ],
  "project-manage" : [
    {id:4, name:"基础信息", code:"project-base", children:[
        {id:5, name:"类别管理", code:"type-tree", routerLink:"type-tree"},
        {id:6, name:"标签管理", code:"label-list", routerLink:"label-list"}
      ]
    },

    {id:7, name:"资讯管理", code:"project-info", children:[
        {id:8, name:"资讯文章", code:"info-list", routerLink:"info-list"},
        {id:9, name:"资讯评论", code:"info-comment-list", routerLink:"info-comment-list"}
      ]
    }
  ]
}



export default {
  headerMenu,
  asideMenu
};

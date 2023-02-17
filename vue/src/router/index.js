import Vue from 'vue'
import VueRouter from 'vue-router'
import HomeView from '../views/Manage.vue'
import store from "@/store";

Vue.use(VueRouter)

const routes = [

  // {
  //   path: '/',
  //   // name: 'Manage',  //首页
  //   component: () => import('../views/ManageView'),
  //   // component: () => import('../views/Login'),
  //   redirect: "/home",
  //   // redirect: "/login",
  //   children: [
  //     {path: 'home', name: '首页', component: () => import('../views/HomeView')},   //首页
  //     {path: 'user', name: '用户管理', component: () => import('../views/User')},    //用户管理
  //     {path: 'person', name: '个人信息', component: () => import('../views/Person')},    //个人信息
  //     {path: 'file', name: '文件管理', component: () => import('../views/File')},    //文件管理
  //   ]
  // },
  // {
  //   path: '/about',
  //   name: 'about',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  // },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('../views/Register')
  },
  {
    path: '/404',
    name: '404',
    component: () => import('../views/404')
  },

]

//注意：刷新页面会导致页面路由的重置
const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

//提供一个重置路由的方法
export const resetRouter = () => {
  router.mattter = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
  })
}

export const setRoutes = () => {
  const storeMenus = localStorage.getItem("menus");
  // const storeAssets = localStorage.getItem("assets");
  if(storeMenus){
    //拼装动态路由
    const manageRoute = {path: '/',name: 'Manage', component: () => import('../views/Manage'), redirect: "/home", children: [
        {path: 'person', name: '个人信息', component: () => import('../views/Person')},    //个人信息
        {path: 'password', name: '修改密码', component: () => import('../views/Password')},    //修改密码
      ] }
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if(item.path){  //当且仅当path不为空的时候才去设置路由
        let itemMenu = {path: item.path.replace("/",""), name:item.name, component: () => import('../views/'+ item.pagePath  )}   //原式在item.pagePath后 + '.vue'
        manageRoute.children.push(itemMenu)
      }else if(item.children.length){
        item.children.forEach(item => {
          if(item.path){
            let itemMenu = {path: item.path.replace("/",""), name:item.name, component: () => import('../views/'+ item.pagePath  )}   //原式在item.pagePath后 + '.vue'
            manageRoute.children.push(itemMenu)
          }

        })
      }

    })

    //获取当前的路由对象名称数组
    const currentRouterNames = router.getRoutes().map(v => v.name)
    if(!currentRouterNames.includes('Manage')){
      //动态添加到现在的路由对象中去
      router.addRoute(manageRoute)
    }


  }
}

//重置就再set一次路由
setRoutes()


// router.addRoute({
//   path: '/',
//   component: () => import('../views/ManageView'),
//   redirect: "/home",
//   children: [
//     {path: 'home', name: '首页', component: () => import('../views/HomeView')},   //首页
//     {path: 'user', name: '用户管理', component: () => import('../views/User')},    //用户管理
//     {path: 'person', name: '个人信息', component: () => import('../views/Person')},    //个人信息
//     {path: 'role', name: '角色管理', component: () => import('../views/Role')},    //角色管理
//     {path: 'menu', name: '菜单管理', component: () => import('../views/Menu')},    //菜单管理
//     {path: 'file', name: '文件管理', component: () => import('../views/File')},    //文件管理
//   ]
// })

//路由守卫
router.beforeEach((to,from,next) => {

  localStorage.setItem("currentPathName",to.name)     //设置当前的路由名称，为了在Header组件中使用
  store.commit("setPath")   //触发store的数据更新

  const storeMenus = localStorage.getItem("menus")

  /**
   * 遇到个问题，重新启动项目时会出现点击系统管理的任何一个页面跳转到404，
   * 目前已知解决方法，先将下列所写的方法注释掉，编译完成后，再重新放开注释编译
   * 目前就此解决
   */
  //未找到路由的情况，如果缓存为空即没有token，则跳回登录页面
  if(!to.matched.length){
    if(storeMenus){   //如果没有正常退出，会导致缓存里还有数据，从而导致跳向404页面
      next("/404")    //放行路由
    }else{
      //跳回登录页面
      next("/login")
    }
  }

  //其他的情况都放行
  next()
})

export default router

// {path: 'home', name: '首页', component: () => import('../views/HomeView')},   //首页
// {path: 'user', name: '用户管理', component: () => import('../views/User')},    //用户管理
// {path: 'role', name: '角色管理', component: () => import('../views/Role')},    //角色管理
// {path: 'menu', name: '菜单管理', component: () => import('../views/Menu')},    //菜单管理
// {path: 'person', name: '个人信息', component: () => import('../views/Person')},    //个人信息
// {path: 'file', name: '文件管理', component: () => import('../views/File')},    //文件管理
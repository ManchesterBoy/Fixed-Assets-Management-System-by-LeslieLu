<template>
<!--    管理系统主体-->

    <el-container style="min-height: 100vh; ">

<!--      侧边框-->
      <el-aside :width=" sideWidth + 'px' " style="overflow: hidden;background-color: rgb(238, 241, 246);box-shadow: 2px 0 6px rgb(0 21 41  / 35%);"  >
        <Aside :is-collapse="isCollapse" :logo-text-show="logoTextShow" />
      </el-aside>

<!--      顶头栏-->
      <el-container>
        <el-header style="border-bottom: 1px solid #ccc; ">
            <Header :collapse="collapse" :collapse-btn-class="collapseBtnClass" :user="user" ></Header>
        </el-header>


        <el-main>
<!--          表示当前页面的子路由会在 router-view 里面展示-->
          <router-view @refreshUser="getUser"></router-view>
        </el-main>
      </el-container>



    </el-container>

</template>

<script>


// import request from "@/utils/request";
import Aside from "@/components/Aside";
import Header from "@/components/Header";

export default {
  name: 'HomeView',

  data(){
    return {
      // 所需数据的初始化
      // msg: "hello leslie666",
      collapseBtnClass: 'el-icon-s-fold',  //侧边栏按钮的图标
      isCollapse: false,    //侧边栏默认展开
      sideWidth:200,
      logoTextShow:true,
      // user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      user: {},
    }
  },

  components: { //组件封装
    Aside,    //侧边栏
    Header,   //顶部
  },
  created() {
    //从后台获取最新的user数据
    this.getUser()
  },
  methods:{
    collapse(){ //侧边栏点击收缩按钮触发
      this.isCollapse = !this.isCollapse
      if(this.isCollapse){  //收缩
        this.sideWidth = 64   //原数据64
        this.collapseBtnClass = 'el-icon-s-unfold'
        this.logoTextShow = false
      }else{  //展开
        this.sideWidth = 200
        this.collapseBtnClass = 'el-icon-s-fold'
        this.logoTextShow = true
      }
    },
    getUser(){
      let username = localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")).username : ""
      //从后台获取user数据
      this.request.get("/user/username/" + username ).then(res => {
        //重新赋值后台的最新user数据
      this.user = res.data
      })
    },

  }

}
</script>



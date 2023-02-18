<template>

<!--  头部栏-->

    <div style="font-size: 12px;line-height: 60px;display: flex;">



      <div style="flex: 1;">
        <span :class="collapseBtnClass" style="cursor: pointer;font-size: 17px" @click="collapse"></span>

        <el-breadcrumb separator="/" style="display: inline-block;margin-left: 10px">
          <el-breadcrumb-item :to="'/'" >首页</el-breadcrumb-item>
          <el-breadcrumb-item >{{ currentPathName }}</el-breadcrumb-item>
        </el-breadcrumb>

      </div>
      <el-dropdown style="width: 150px;cursor: pointer;text-align: right">
        <div style="display: inline-block;right: 30px">
          <img :src="user.avatarUrl" alt=""
               style="height: 25px; width: 30px;border-radius: 50%;position: relative;top: 8px;right: 10px">
<!--          <img src="https://i0.hdslb.com/bfs/album/2bb2eed8a4493c2cef36d9ae4a8c7854136ef5ea.jpg" alt=""-->
<!--               style="height: 25px;width: 30px;border-radius: 50%;position: relative;top: 8px;right: 10px">-->
          <span>{{ user.nickname }}</span><i class="el-icon-s-tools" style="margin-left: 8px"></i>
        </div>

        <el-dropdown-menu slot="dropdown" style="width: 100px;text-align: center">
          <el-dropdown-item>
            <router-link to="/person">个人信息</router-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <router-link to="/password">修改密码</router-link>
          </el-dropdown-item>
          <el-dropdown-item>
            <span style="text-decoration: none" @click="logout">退出</span>
          </el-dropdown-item>

        </el-dropdown-menu>
      </el-dropdown>
    </div>
</template>

<script>
export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    collapse: Boolean,
    user: Object,
  },
  computed:{
    currentPathName(){
      return this.$store.state.currentPathName;   //需要监听的数据
    }
  },
  data(){
    return{
      // user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
    }
  },
  methods:{
    logout(){   //退出按钮功能
      // this.$router.push("/login")
      // localStorage.removeItem("user")
      this.$store.commit("logout")
      this.$message.success("退出成功")
    }
  },
  watch:{   //监听路由变化
    currentPathName (newVal,oldVal){
      console.log(newVal)
    }
  },
}
</script>

<style scoped>
.router-link-active {
  text-decoration: none;
  color: #409EFF;
}
a{
  text-decoration: none;
  color: gray;
}
</style>
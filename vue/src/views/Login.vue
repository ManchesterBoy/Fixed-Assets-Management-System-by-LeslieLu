<template>
<!--    登录模块-->
  <div class="wrapper">
    <div style="margin: 200px auto;background-color:#fff;width: 350px;height: 300px;padding: 20px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>登 录</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input  size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
         <div style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplet="off" @click="login">登录</el-button>
          <el-button type="warning" size="small" autocomplet="off" @click="$router.push('/register')">注册</el-button>
        </div>
      </el-form>
  </div>
  </div>


</template>

<script>
import {setRoutes} from "@/router";

export default {
  name: "Login",
  data(){
    return{
      user: {},
      rules: {    //登录校验规则
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
      },
    }
  },
  methods:{
    login(){    //登录跳转
      this.$refs["userForm"].validate((valid) => {
        if (valid) {    //表单校验合法
          this.request.post("/user/login",this.user).then(res => {
            if(res.code === '200'){
              localStorage.setItem("user",JSON.stringify(res.data))   //存储用户信息到浏览器
              localStorage.setItem("menus",JSON.stringify(res.data.menus))   //存储菜单信息到浏览器


              //动态设置当前用户的路由
              setRoutes()
              this.$router.push("/")
              this.$message.success("登录成功")
            }
            else{
              this.$message.error(res.msg)
            }
          })
        }
      });

    },
  }
}
</script>

<style scoped>
/*登录页面背景颜色渐变*/
  .wrapper{
    height: 100vh;
    /*background-image: linear-gradient(to bottom right,#FC466B,#3F5EFB);*/
    background-image: linear-gradient(to bottom left, #fc46b9, #3fe5fb);
    overflow: hidden;
  }


</style>
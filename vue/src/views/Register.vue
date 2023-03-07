<template>
<!--    注册模块-->
  <div class="wrapper">
    <div style="margin: 150px auto;background-color:#fff;width: 380px;height: 450px;padding: 30px;border-radius: 10px">
      <div style="margin: 20px 0;text-align: center;font-size: 24px"><b>注 册</b></div>
      <el-form :model="user" :rules="rules" ref="userForm">
        <el-form-item prop="username">
          <el-input placeholder="请输入账号" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user" v-model="user.username"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.password"></el-input>
        </el-form-item>
        <el-form-item prop="confirmPassword">
          <el-input placeholder="请确认密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock" show-password v-model="user.confirmPassword"></el-input>
        </el-form-item>
        <el-form-item prop="role">
          <el-select  v-model="user.role" placeholder="请选择用户角色"  style="margin: 10px 0;width: 320px" >
            <el-option label="普通用户" value="ROLE_USER" prefix-icon="el-icon-user"></el-option>
          </el-select>
        </el-form-item>
         <div style="margin: 10px 0;text-align: right">
          <el-button type="primary" size="small" autocomplet="off" @click="login">注册</el-button>
          <el-button type="warning" size="small" autocomplet="off" @click="$router.push('/login')">返回登录</el-button>
        </div>
      </el-form>
  </div>
  </div>


</template>

<script>
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
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        role:[
          { required: true, message: '请选择用户角色', trigger: 'change' }
        ],
      },
    }
  },
  methods:{
    login(){    //登录跳转
      this.$refs["userForm"].validate((valid) => {
        if (valid) {    //表单校验合法
          if(this.user.password !== this.user.confirmPassword){
            this.$message.error("两次输入的密码不一致")
            return false
          }
          this.request.post("/user/register",this.user).then(res => {
            if(res.code === '200'){
              this.$message.success("注册成功")
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
/*注册页面背景颜色渐变*/
  .wrapper{
    height: 100vh;
    /*background-image: linear-gradient(to bottom right,#FC466B,#3F5EFB);*/
    background-image: linear-gradient(to bottom left, #fc46b9, #3fe5fb);
    overflow: hidden;
  }


</style>
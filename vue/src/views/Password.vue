<template>
  <div>
<!--    <h1>修改密码</h1>-->
    <el-card style="width: 500px;padding: 20px;margin: auto">
      <el-form label-width="100px" size="mid" :model="form" :rules="rules" ref="pass">

        <el-form-item label="原密码" prop="password" >
          <el-input v-model="form.password" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword" >
          <el-input v-model="form.newPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item label="确认新密码" prop="confirmPassword" >
          <el-input v-model="form.confirmPassword" autocomplete="off" show-password></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="save">确 定</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "Password",
  data() {
    return{
      form: {},
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules: {
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        confirmPassword: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
      }
    }
  },
  created() {
    this.form.username = this.user.username
  },
  methods:{
    save(){ //编辑完成时的保存功能
      this.$refs.pass.validate((valid) =>{
        if(valid){  //合法
          if(this.form.newPassword !== this.form.confirmPassword){
            this.$message.error("两次输入的新密码不相同")
            return false
          }
          this.request.post("/user/password",this.form).then(res =>{
            if(res.code === '200'){
              this.$message.success("修改成功")
              this.$store.commit("logout")
            }else {
              this.$message.error(res.msg)
            }
          })
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
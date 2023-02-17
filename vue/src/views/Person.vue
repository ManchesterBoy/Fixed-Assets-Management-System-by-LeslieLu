<template>
<!--  个人信息表单模块-->
  <el-card style="width: 500px;padding: 20px;margin: auto">
    <el-form label-width="80px" size="mid" :model="form" :rules="rules" ref="pass">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
      <el-form-item label="用户名" >
        <el-input v-model="form.username" :disabled="true" autocomplete="off" ></el-input>
      </el-form-item>
      <el-form-item label="昵称" prop="nickname">
        <el-input v-model="form.nickname" autocomplete="off" placeholder="请输入昵称"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="form.email" autocomplete="off" placeholder="请输入邮箱"></el-input>
      </el-form-item>
      <el-form-item label="电话" prop="phone">
        <el-input v-model="form.phone" autocomplete="off" placeholder="请输入电话"></el-input>
      </el-form-item>
      <el-form-item label="地址" prop="address">
        <el-input type="textarea" v-model="form.address" autocomplete="off" placeholder="请输入地址" ></el-input>
      </el-form-item>
<!--      <el-form-item label="头像" prop="avatarUrl">-->
<!--        <img :src="form.avatarUrl" style="width: 170px;height: 200px;">-->
<!--      </el-form-item>-->
      <el-form-item>
        <el-button style="margin-left: 105px" type="primary" @click="save">确 定</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
export default {
  name: "Person",
  data(){
    return{
      form: {
        nickname:'',
        email:'',
        phone:'',
        address:'',
      },
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      rules:{
        nickname:[
          { required: true, message: '请输入昵称', trigger: 'change' },
        ],
        email:[
          { required: true, message: '请输入邮箱', trigger: 'change' },
        ],
        phone:[
          { required: true, message: '请输入电话', trigger: 'change' },
          { min: 11, max: 11, message: '长度为11个数字', trigger: 'blur' },
        ],
        address:[
          { required: true, message: '请输入地址', trigger: 'change' },
        ],
      },
    }
  },
  created() {
    this.getUser().then(res =>{
      this.form = res
    })
  },
  methods: {
    async getUser(){
      return (await this.request.get("/user/username/" + this.user.username )).data
    },
    save(){ //编辑完成时的保存功能

      this.$refs.pass.validate((valid) => {
        if (valid) {  //合法
          this.request.post("/user",this.form).then(res => {
            if(res.code === '200'){
              this.$message.success("保存成功")

              //触发父级更新user的方法
              this.$emit("refreshUser")

              //更新浏览器存储的用户信息
              this.getUser().then(res => {
                res.token = JSON.parse(localStorage.getItem("user")).token
                localStorage.setItem("user",JSON.stringify(res))
              })

              // location.reload()
            }
            else{
              this.$message.error("保存失败")
            }
          })

        }
      })

    },
    handleAvatarSuccess(res){   //更换头像
      this.form.avatarUrl = res
    },
    beforeAvatarUpload(file){   //上传头像相关限制
      const isJPG = file.type === 'image/jpeg';
      // const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      // if (!isLt2M) {
      //   this.$message.error('上传头像图片大小不能超过 2MB!');
      // }
      // return isJPG && isLt2M;
      return isJPG;
    }
  }
}
</script>

<style>


.avatar-uploader{
  text-align: center;
  padding-bottom: 10px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 158px;
  height: 178px;
  display: block;
}
</style>
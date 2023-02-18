<template>
<!--  用户管理模块-->
    <div>


      <!--          搜索与其它功能按钮-->
      <div style="padding: 10px 0">
        <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" class="ml-5" v-model="username"></el-input>
        <el-input style="width: 200px" placeholder="请输入邮箱" suffix-icon="el-icon-share" class="ml-5" v-model="email"></el-input>
        <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5" v-model="address"address></el-input>
        <el-button class="ml-5" type="primary" @click="load" >搜索</el-button>
        <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>
      </div>

      <div style="margin: 10px 0">
        <el-button class="ml-5" type="primary" @click="handleAdd" >新增<i class="el-icon-circle-plus-outline"></i></el-button>

        <el-popconfirm
            class="ml-5"
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定删除吗？"
            @confirm="delBatch"
        >
          <el-button  type="danger"  slot="reference" >批量删除<i class="el-icon-remove-outline"></i></el-button>
        </el-popconfirm>

        <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">
          <el-button class="ml-5" type="primary" >导入<i class="el-icon-bottom"></i></el-button>
        </el-upload>

        <el-popconfirm
            confirm-button-text='确定'
            cancel-button-text='我再想想'
            icon="el-icon-info"
            icon-color="red"
            title="您确定导出当前所有数据吗？"
            @confirm="exp"
        >
        <el-button class="ml-5" type="primary" slot="reference" >导出<i class="el-icon-top"></i></el-button>
        </el-popconfirm>
      </div>

      <!--          表单主体-->
      <el-table  :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="username" label="用户名" width="140"></el-table-column>
        <el-table-column prop="role" label="角色" width="150">
          <template slot-scope="scope">
            <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>
            <el-tag type="info" v-if="scope.row.role === 'ROLE_USER'">普通用户</el-tag>
            <el-tag type="warning" v-if="scope.row.role === 'ROLE_TEACHER'">老师</el-tag>
            <el-tag type="success" v-if="scope.row.role === 'ROLE_STUDENT'">学生</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="nickname" label="昵称" width="120"></el-table-column>
        <el-table-column prop="email" label="邮箱" width="120"></el-table-column>
        <el-table-column prop="phone" label="电话" width="120"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column label="操作" width="300">
          <template slot-scope="scope">

                     <!--                编辑与删除按钮-->
            <el-button type="success" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit-outline"></i></el-button>

            <el-popconfirm
                class="ml-5"
                confirm-button-text='确定'
                cancel-button-text='我再想想'
                icon="el-icon-info"
                icon-color="red"
                title="您确定删除吗？"
                @confirm="handleDel(scope.row.id)"
            >
              <el-button type="danger" slot="reference"  >删除<i class="el-icon-folder-remove"></i></el-button>
<!--              <el-button type="primary" slot="reference"  >提交<i class="el-icon-thumb"></i></el-button>-->
            </el-popconfirm>
            <el-button class="ml-5" type="primary" @click="lookCourse(scope.row.courses)" v-if="scope.row.role === 'ROLE_TEACHER'">查看教授课程<i class="el-icon-document"></i></el-button>
            <el-button class="ml-5" type="warning" @click="lookStuCourse(scope.row.stuCourses)" v-if="scope.row.role === 'ROLE_STUDENT'">查看已选课程<i class="el-icon-document"></i></el-button>


          </template>
        </el-table-column>
      </el-table>

      <!--          分页设置, page-sizes =>> 每一页可有多少条数据-->
      <div style="padding: 10px 0">
        <el-pagination

            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="pageNum"
            :page-sizes="[5, 10, 15, 20]"
            :page-size="pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
        </el-pagination>
      </div>

      <!--          编辑时的表单-->
      <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%" >
        <el-form label-width="80px" size="mid" :model="form" :rules="rules" ref="pass">
          <el-form-item label="用户名" prop="username">
            <el-input v-model="form.username" autocomplete="off" placeholder="请输入用户名"></el-input>
          </el-form-item>
          <el-form-item label="角色" prop="role" >
            <el-select clearable v-model="form.role" placeholder="请选择角色" style= "width: 100%">
              <el-option v-for="item in roles"  :key="item.name" :label="item.name" :value="item.flag" />
            </el-select>
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
          <el-form-item label="地址" prop="address" >
            <el-input v-model="form.address" autocomplete="off" placeholder="请输入地址"></el-input>
          </el-form-item>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>

<!--&lt;!&ndash;      查看教授课程模块&ndash;&gt;-->
<!--      <el-dialog title="课程信息" :visible.sync="vis" width="30%" >-->
<!--        <el-table :data="courses" stripe>-->
<!--          <el-table-column prop="name" label="课程名称"></el-table-column>-->
<!--          <el-table-column prop="score" label="学分"></el-table-column>-->
<!--        </el-table>-->
<!--      </el-dialog>-->

<!--&lt;!&ndash;      查看已选课程模块&ndash;&gt;-->
<!--      <el-dialog title="课程信息" :visible.sync="stuVis" width="30%" >-->
<!--        <el-table :data="stuCourses" stripe>-->
<!--          <el-table-column prop="name" label="课程名称"></el-table-column>-->
<!--          <el-table-column prop="score" label="学分"></el-table-column>-->
<!--        </el-table>-->
<!--      </el-dialog>-->

      <!--    回到顶部-->
      <template>
        <el-backtop :bottom="30" :right="30">
          <div
              style="{
        height: 100%;
        width: 100%;
        background-color: #f2f5f6;
        box-shadow: 0 0 6px rgba(0,0,0, .12);
        text-align: center;
        line-height: 40px;
        color: #1989fa;
      }"
          >
            ↑
          </div>
        </el-backtop>
      </template>


    </div>



</template>

<script>
export default {
  name: "User",
  data(){
    return{
      tableData: [],  //表单主体数据初始化
      total: 0,       //总条数初始化
      pageNum: 1,     //初始页码
      pageSize: 10,    //一页中的初始信息的数量
      username: "",   //用户名
      nickname: "",   //昵称
      email: "",      //邮箱
      address: "",    //地址
      dialogFormVisible: false,   //编辑时的表单
      multipleSelection: [],      //批量选择
      form: {
        username:'',
        role:'',
        nickname:'',
        email:'',
        phone:'',
        address:'',
      },                   //点击编辑时，将原有数据送至编辑表单的初始化
      roles: [],
      courses:[],
      stuCourses:[],
      vis: false,
      stuVis: false,
      rules:{
        username:[
          { required: true, message: '请输入用户名', trigger: 'change' },
        ],
        role:[
          {required: true, message: '请选择角色', trigger: 'change'},
        ],
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
      // headerBg:'headerBg'
    }
  },
  created(){
    //请求分页查询数据
    this.load()
  },
  methods:{
    load(){ //请求分页查询数据，加载页面
      this.request.get("user/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          username: this.username,
          email: this.email,
          address: this.address,
        }
      }).then(res => {
        console.log(res)
        localStorage.setItem("users",JSON.stringify(res.data.records))   //存储用户表的信息到浏览器
        this.tableData = res.data.records
        this.total = res.data.total

      })



      this.request.get("/role").then(res => {
        this.roles = res.data
      })

    },
    handleAdd(){  //点击添加功能时弹出表单并清除上一次表单的信息
      this.dialogFormVisible = true
      this.form = {}

    },
    handleEdit(row){  //点击编辑功能时弹出表单并渲染所选的信息到表单
      this.form = row
      this.dialogFormVisible = true

    },
    handleDel(id){  //点击删除按钮时，删除所选id的信息
      this.request.delete("/user/" + id).then(res => {
        if(res.code === '200'){
          this.$message.success("删除成功")
          this.dialogFormVisible = false
          this.load()
        }
        else{
          this.$message.error("删除失败")
        }
      })
    },
    handleSelectionChange(val){ //批量选择
      console.log(val)
      this.multipleSelection = val
    },
    delBatch(){ //批量删除所选的信息
      let ids = this.multipleSelection.map(v => v.id) // [{},{},{}] => [1,2,3]
      this.request.post("/user/del/batch",ids).then(res => {
        if(res.code === '200'){
          this.$message.success("批量删除成功")
          this.dialogFormVisible = false
          this.load()
        }
        else{
          this.$message.error("删除失败")
        }
      })
    },
    save(){ //编辑完成时的保存功能

      this.$refs.pass.validate((valid) => {
        if (valid) {  //合法
          this.request.post("/user",this.form).then(res => {
            if(res.code === '200'){
              this.$message.success("保存成功")
              this.dialogFormVisible = false
              this.load()
            }
            else{
              this.$message.error("保存失败")
            }
          })
        }
      })

    },
    reset(){ //重置按钮实现重置功能
      this.username = ""
      this.email = ""
      this.address = ""
      this.load()
    },
    handleSizeChange(pageSize) {  //每一页信息的最多条数
      console.log(`每页 ${pageSize} 条`);
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {  //当前页的信息
      console.log(`当前页: ${pageNum}`);
      this.pageNum = pageNum
      this.load()
    },
    exp(){    //导出功能
      window.open("http://localhost:9090/user/export")
    },
    handleExcelImportSuccess(){   //导入成功提示
      this.$message.success("文件导入成功")
      this.load()
    },
    lookCourse(courses){
      this.courses = courses
      this.vis = true
      // console.log(courses)
    },
    lookStuCourse(stuCourses){
      this.stuCourses = stuCourses
      this.stuVis = true
      // console.log(stuCourses)
    },
  }
}
</script>

<style>
.headerBg{
  background: #eee!important;
}
</style>
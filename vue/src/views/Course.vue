<!--<template>-->
<!--&lt;!&ndash;  课程管理模块&ndash;&gt;-->
<!--    <div>-->

<!--      <div style="padding: 10px 0">-->
<!--        <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>-->
<!--        <el-button class="ml-5" type="primary" @click="load" >搜索</el-button>-->
<!--        <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>-->
<!--      </div>-->

<!--        <div style="margin: 10px 0">-->
<!--          <el-button class="ml-5" type="primary" @click="handleAdd" v-if="user.role === 'ROLE_ADMIN'" >新增<i class="el-icon-circle-plus-outline"></i></el-button>-->
<!--        <el-popconfirm-->
<!--            class="ml-5"-->
<!--            confirm-button-text='确定'-->
<!--            cancel-button-text='我再想想'-->
<!--            icon="el-icon-info"-->
<!--            icon-color="red"-->
<!--            title="您确定删除吗？"-->
<!--            @confirm="delBatch"-->
<!--        >-->
<!--          <el-button  type="danger"  slot="reference" v-if="user.role === 'ROLE_ADMIN'" >批量删除<i class="el-icon-remove-outline"></i></el-button>-->
<!--        </el-popconfirm>-->


<!--      </div>-->

<!--      <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">>-->
<!--        <el-table-column type="selection" width="55"></el-table-column>-->
<!--        <el-table-column prop="id" label="ID" width="80"></el-table-column>-->
<!--        <el-table-column prop="name" label="课程名称" ></el-table-column>-->
<!--        <el-table-column prop="score" label="学分" ></el-table-column>-->
<!--        <el-table-column prop="times" label="课时" ></el-table-column>-->
<!--        <el-table-column prop="teacher" label="授课老师" ></el-table-column>-->
<!--        <el-table-column  label="启用">-->
<!--&lt;!&ndash;          prop="enable"&ndash;&gt;-->
<!--          <template slot-scope="scope">-->
<!--            <el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ccc"-->
<!--                       @change="changeEnable(scope.row)"></el-switch>-->
<!--          </template>-->
<!--        </el-table-column>-->
<!--        <el-table-column label="操作" width="300">-->
<!--          <template slot-scope="scope">-->
<!--            <el-button type="primary" @click="selectCourse(scope.row.id)">选课</el-button>-->
<!--            <el-button type="success" @click="handleEdit(scope.row)" v-if="user.role === 'ROLE_ADMIN'">编辑<i class="el-icon-edit-outline"></i></el-button>-->

<!--            &lt;!&ndash;                编辑与删除按钮&ndash;&gt;-->
<!--            <el-popconfirm-->
<!--                class="ml-5"-->
<!--                confirm-button-text='确定'-->
<!--                cancel-button-text='我再想想'-->
<!--                icon="el-icon-info"-->
<!--                icon-color="red"-->
<!--                title="您确定删除吗？"-->
<!--                @confirm="handleDel(scope.row.id)"-->
<!--            >-->
<!--              <el-button type="danger" slot="reference" v-if="user.role === 'ROLE_ADMIN'" >删除<i class="el-icon-folder-remove"></i></el-button>-->
<!--              &lt;!&ndash;              <el-button type="primary" slot="reference"  >提交<i class="el-icon-thumb"></i></el-button>&ndash;&gt;-->
<!--            </el-popconfirm>-->

<!--          </template>-->
<!--        </el-table-column>-->
<!--      </el-table>-->

<!--      &lt;!&ndash;          分页设置, page-sizes =>> 每一页可有多少条数据&ndash;&gt;-->
<!--      <div style="padding: 10px 0">-->
<!--        <el-pagination-->

<!--            @size-change="handleSizeChange"-->
<!--            @current-change="handleCurrentChange"-->
<!--            :current-page="pageNum"-->
<!--            :page-sizes="[5, 10, 15, 20]"-->
<!--            :page-size="pageSize"-->
<!--            layout="total, sizes, prev, pager, next, jumper"-->
<!--            :total="total">-->
<!--        </el-pagination>-->
<!--      </div>-->

<!--      &lt;!&ndash;          编辑时的表单&ndash;&gt;-->
<!--      <el-dialog title="课程信息" :visible.sync="dialogFormVisible" width="30%" >-->
<!--        <el-form label-width="80px" size="mid">-->
<!--          <el-form-item label="名称" >-->
<!--            <el-input v-model="form.name" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="学分" >-->
<!--            <el-input v-model="form.score" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="课时" >-->
<!--            <el-input v-model="form.times" autocomplete="off"></el-input>-->
<!--          </el-form-item>-->
<!--          <el-form-item label="授课老师">-->
<!--            <el-select v-model="form.teacherId" placeholder="请选择">-->
<!--              <el-option v-for="item in teachers" :key="item.id" :label="item.nickname" :value="item.id"></el-option>-->
<!--            </el-select>-->
<!--          </el-form-item>-->
<!--        </el-form>-->
<!--        <div slot="footer" class="dialog-footer">-->
<!--          <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--          <el-button type="primary" @click="save">确 定</el-button>-->
<!--        </div>-->
<!--      </el-dialog>-->

<!--    </div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  name: "Course",-->
<!--  data(){-->
<!--    return{-->
<!--      tableData: [],-->
<!--      name: '',-->
<!--      multipleSelection: [],-->
<!--      pageNum: 1,-->
<!--      pageSize: 10,-->
<!--      total: 0,-->
<!--      dialogFormVisible: false,-->
<!--      form: {},-->
<!--      teachers: [],-->
<!--      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},-->

<!--    }-->
<!--  },-->
<!--  created() {-->
<!--    this.load()-->
<!--  },-->
<!--  methods:{-->
<!--    load(){ //请求分页查询数据，加载页面-->
<!--      this.request.get("/course/page",{-->
<!--        params:{-->
<!--          pageNum: this.pageNum,-->
<!--          pageSize: this.pageSize,-->
<!--          name: this.name,-->
<!--        }-->
<!--      }).then(res => {-->
<!--        console.log(res)-->

<!--        this.tableData = res.data.records-->
<!--        this.total = res.data.total-->

<!--      })-->

<!--      this.request.get("/user/role/ROLE_TEACHER").then(res => {-->
<!--        this.teachers = res.data-->

<!--      })-->


<!--    },-->
<!--    handleDel(id){  //点击删除按钮时，删除所选id的信息-->
<!--      this.request.delete("/course/" + id).then(res => {-->
<!--        if(res.code === '200'){-->
<!--          this.$message.success("删除成功")-->
<!--          this.dialogFormVisible = false-->
<!--          this.load()-->
<!--        }-->
<!--        else{-->
<!--          this.$message.error("删除失败")-->
<!--        }-->
<!--      })-->
<!--    },-->
<!--    handleSelectionChange(val){ //批量选择-->
<!--      console.log(val)-->
<!--      this.multipleSelection = val-->
<!--    },-->
<!--    delBatch(){ //批量删除所选的信息-->
<!--      let ids = this.multipleSelection.map(v => v.id) // [{},{},{}] => [1,2,3]-->
<!--      this.request.post("/course/del/batch",ids).then(res => {-->
<!--        if(res.code === '200'){-->
<!--          this.$message.success("批量删除成功")-->
<!--          this.dialogFormVisible = false-->
<!--          this.load()-->
<!--        }-->
<!--        else{-->
<!--          this.$message.error("删除失败")-->
<!--        }-->
<!--      })-->
<!--    },-->
<!--    reset(){ //重置按钮实现重置功能-->
<!--      this.name = ""-->
<!--      this.load()-->
<!--    },-->
<!--    handleSizeChange(pageSize) {  //每一页信息的最多条数-->
<!--      console.log(`每页 ${pageSize} 条`);-->
<!--      this.pageSize = pageSize-->
<!--      this.load()-->
<!--    },-->
<!--    handleCurrentChange(pageNum) {  //当前页的信息-->
<!--      console.log(`当前页: ${pageNum}`);-->
<!--      this.pageNum = pageNum-->
<!--      this.load()-->
<!--    },-->
<!--    download(url){  //下载-->
<!--      window.open(url)-->
<!--    },-->
<!--    changeEnable(row){  //启用按钮-->

<!--      this.request.post("/course",row).then(res => {-->
<!--        if(res.code === '200'){-->
<!--          this.$message.success("操作成功")-->
<!--        }-->
<!--      })-->

<!--    },-->
<!--    save(){ //编辑完成时的保存功能-->
<!--      this.request.post("/course",this.form).then(res => {-->
<!--        if(res.code === '200'){-->
<!--          this.$message.success("保存成功")-->
<!--          this.dialogFormVisible = false-->
<!--          this.load()-->
<!--        }-->
<!--        else{-->
<!--          this.$message.error("保存失败")-->
<!--        }-->
<!--      })-->
<!--    },-->
<!--    handleAdd(){  //点击添加功能时弹出表单并清除上一次表单的信息-->
<!--      this.dialogFormVisible = true-->
<!--      this.form = {}-->

<!--    },-->
<!--    handleEdit(row){  //点击编辑功能时弹出表单并渲染所选的信息到表单-->
<!--      this.form = row-->
<!--      this.dialogFormVisible = true-->

<!--    },-->
<!--    selectCourse(courseId){-->
<!--      this.request.post("/course/studentCourse/" + courseId + "/" + this.user.id).then(res =>{-->
<!--        if(res.code === '200'){-->
<!--          this.$message.success("选课成功")-->
<!--        }else {-->
<!--          this.$message.error(res.msg)-->
<!--        }-->
<!--      })-->
<!--    },-->

<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->

<!--</style>-->
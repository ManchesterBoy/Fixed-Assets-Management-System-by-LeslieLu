<template>
  <!--  台帐管理模块-->
  <div>


    <!--          搜索与其它功能按钮-->
    <div style="padding: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>
      <el-input style="width: 200px" placeholder="请输入内容" suffix-icon="el-icon-document" class="ml-5" v-model="content"></el-input>
      <!--      <el-input style="width: 200px" placeholder="请输入提交者名字" suffix-icon="el-icon-search" class="ml-5" v-model="submitter"></el-input>-->
      <el-button class="ml-5" type="primary" @click="load" >搜索</el-button>
      <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>
    </div>

    <div style="margin: 10px 0">
      <!--      <el-button class="ml-5" type="primary" @click="handleAdd" >新增<i class="el-icon-circle-plus-outline"></i></el-button>-->

      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定删除吗？"
          @confirm="delBatch"
      >
        <el-button  type="danger"  slot="reference" v-if="userRights === 'ROLE_ADMIN'">批量删除<i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>


      <el-popconfirm
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定导出当前所有数据吗？"
          @confirm="exp"
      >
        <el-button class="ml-5" type="primary" slot="reference" v-if="userRights === 'ROLE_ADMIN'" >导出<i class="el-icon-top"></i></el-button>
      </el-popconfirm>
    </div>

    <!--          表单主体-->

    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">>
      <el-table-column type="selection" ></el-table-column>
      <el-table-column prop="id" label="ID"  width="50"></el-table-column>
      <el-table-column prop="name" label="事件名称" width="80"></el-table-column>
      <el-table-column prop="content" label="具体内容" ></el-table-column>
      <el-table-column prop="price" label="申请金额" ></el-table-column>
      <el-table-column prop="management" label="事件状态" >
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.management === 2">已结束</el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column prop="role" label="用户权限" >-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>-->
<!--          <el-tag type="info" v-if="scope.row.role === 'ROLE_USER'">普通用户</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="submitter" label="提交者" ></el-table-column>
      <el-table-column prop="adminPurchaseName" label="购置审批" ></el-table-column>
      <el-table-column prop="adminAcceptName" label="验收审批" ></el-table-column>
      <el-table-column prop="adminAllocationName" label="调拨审批" ></el-table-column>
      <el-table-column prop="adminManagementName" label="处置审批" ></el-table-column>
      <el-table-column label="操作" width="200" >
        <template slot-scope="scope">

          <!--                编辑与删除按钮-->
                    <el-button type="success" @click="handleEdit(scope.row)"  >查看内容<i class="el-icon-document-checked"></i></el-button>

          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDel(scope.row.id)"
          >
            <el-button type="danger" slot="reference" v-if="userRights === 'ROLE_ADMIN'" >删除<i class="el-icon-folder-remove"></i></el-button>
            <!--              <el-button type="primary" slot="reference"  >提交<i class="el-icon-thumb"></i></el-button>-->
          </el-popconfirm>
          <!--            <el-button class="ml-5" type="primary" @click="lookCourse(scope.row.courses)" v-if="scope.row.role === 'ROLE_TEACHER'">查看教授课程<i class="el-icon-document"></i></el-button>-->
<!--          <el-button class="ml-5" type="primary" @click="commitCheck(scope.row.id)" v-if="scope.row.management === 1-->
<!--          && userRights === 'ROLE_ADMIN'">审批<i class="el-icon-document"></i></el-button>-->


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
    <el-dialog title="业务信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" size="mid">
        <el-form-item label="事件名称" >
          <el-input v-model="form.name" autocomplete="off" disabled></el-input>
        </el-form-item>
        <el-form-item label="具体内容" >
          <el-input type="textarea" v-model="form.content" autocomplete="off" disabled></el-input>
        </el-form-item>


      </el-form>
<!--      <div slot="footer" class="dialog-footer">-->
<!--        <el-button @click="dialogFormVisible = false">取 消</el-button>-->
<!--        <el-button type="primary" @click="save">确 定</el-button>-->
<!--      </div>-->
    </el-dialog>


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
      name: "",   //用户名
      content: "",   //内容
      submitter: "",      //提交者
      dialogFormVisible: false,   //编辑时的表单
      multipleSelection: [],      //批量选择
      form: {},                   //点击编辑时，将原有数据送至编辑表单的初始化
      management: '',   //购置请求
      customers: [],
      userRights: '',
      price:'',
      adminPurchaseName: '',  //购置管理审批人名称
      adminAcceptName: '',  //验收管理审批人名称
      adminAllocationName: '',  //调拨管理审批人名称
      adminManagementName: '',  //处置管理审批人名称
      // roles: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
      assets: localStorage.getItem("assets") ? JSON.parse(localStorage.getItem("assets")) : {},
    }
  },
  created(){
    //请求分页查询数据
    this.load()
  },
  methods:{
    load(){ //请求分页查询数据，加载页面
      this.request.get("assets/page/account",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          price: this.price,
          content: this.content,
          submitter: this.submitter,
          management: this.management,
          adminPurchaseName: this.adminPurchaseName,
          adminAcceptName: this.adminAcceptName,
          adminAllocationName: this.adminAllocationName,
          adminManagementName: this.adminManagementName,
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.data.records
        this.total = res.data.total

      })

      this.userRights = this.user.role

      this.request.get("/user/assets/" + this.user.nickname ).then(res => {
        this.customers = res.data
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
      this.request.delete("/assets/" + id).then(res => {
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
      this.request.post("/assets/del/batch",ids).then(res => {
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
      this.request.post("/assets",this.form).then(res => {
        if(res.code === '200'){
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        }
        else{
          this.$message.error("保存失败")
        }
      })
    },
    reset(){ //重置按钮实现重置功能
      this.name = ""
      this.content = ""
      this.submitter = ""
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
      window.open("http://localhost:9090/assets/export")
    },
    commitCheck(id){  //处置流程

      this.request.post("/assets/updateFinal/" + id + "/" + this.user.id ) .then(res => {
        if (res.code === '200') {
          this.$message.success("审批成功")
          this.load()
        } else {
          this.$message.error("审批失败")
        }
        // console.log("*****",res.data)
      })


    },
  }
}
</script>

<style>
.headerBg{
  background: #eee!important;
}
</style>
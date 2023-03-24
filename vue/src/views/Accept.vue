<template>
  <!--  验收管理模块-->
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
        <el-button  type="danger"  slot="reference" v-if="userRights === 'ROLE_ADMIN'" >批量删除<i class="el-icon-remove-outline"></i></el-button>
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
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column prop="id" label="ID" width="40"></el-table-column>
      <el-table-column prop="name" label="事件名称" ></el-table-column>
      <el-table-column prop="content" label="具体内容" width="180"></el-table-column>
      <el-table-column prop="price" label="申请金额" ></el-table-column>
      <el-table-column prop="quantity" label="数量" width="50"></el-table-column>
      <el-table-column prop="unit_price" label="单价" width="80">
        <template slot-scope="scope">
          {{ (scope.row.price / scope.row.quantity).toFixed(2)}}
        </template>
      </el-table-column>
      <el-table-column prop="supplier" label="供应商" ></el-table-column>
      <el-table-column prop="manufacturer" label="生产商" ></el-table-column>
      <el-table-column prop="accept" label="验收流程" >
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.accept === 0">未处理</el-tag>
          <el-tag type="primary" v-if="scope.row.accept === 1">待处理</el-tag>
          <el-tag type="success" v-if="scope.row.accept === 2">已处理</el-tag>
        </template>
      </el-table-column>
<!--      <el-table-column prop="role" label="用户权限" >-->
<!--        <template slot-scope="scope">-->
<!--          <el-tag type="primary" v-if="scope.row.role === 'ROLE_ADMIN'">管理员</el-tag>-->
<!--          <el-tag type="info" v-if="scope.row.role === 'ROLE_USER'">普通用户</el-tag>-->
<!--        </template>-->
<!--      </el-table-column>-->
      <el-table-column prop="submitter" label="提交者" ></el-table-column>
      <el-table-column prop="adminAcceptName" label="审批" ></el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">

          <!--                编辑与删除按钮-->
<!--          <el-button type="success" @click="handleEdit(scope.row)" v-if="scope.row.accept === 1" >编辑<i class="el-icon-edit-outline"></i></el-button>-->

          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="handleDel(scope.row.id)"
          >
            <el-button type="danger" slot="reference" v-if="scope.row.accept === 1
            && userRights === 'ROLE_ADMIN'" >删除<i class="el-icon-folder-remove"></i></el-button>
            <!--              <el-button type="primary" slot="reference"  >提交<i class="el-icon-thumb"></i></el-button>-->
          </el-popconfirm>
          <!--            <el-button class="ml-5" type="primary" @click="lookCourse(scope.row.courses)" v-if="scope.row.role === 'ROLE_TEACHER'">查看教授课程<i class="el-icon-document"></i></el-button>-->
          <el-button class="ml-5" type="primary" @click="commitCheck(scope.row.id)" v-if="scope.row.accept === 1
          && userRights === 'ROLE_ADMIN'">审批<i class="el-icon-document"></i></el-button>


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
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="具体内容" >
          <el-input type="textarea" v-model="form.content" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="提交者" >
          <el-select v-model="form.userId" placeholder="请选择">
            <el-option v-for="item in customers" :key="item.id" :label="item.nickname" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="购置申请" >
          <el-radio-group v-model="form.accept">
            <el-radio label="1" >
              待处理
            </el-radio>
          </el-radio-group>
        </el-form-item>


      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

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
      name: "",   //用户名
      content: "",   //内容
      submitter: "",      //提交者
      dialogFormVisible: false,   //编辑时的表单
      multipleSelection: [],      //批量选择
      form: {},                   //点击编辑时，将原有数据送至编辑表单的初始化
      accept: '',   //购置请求
      customers: [],   //表单提交角色下拉框所显示当前用户
      userRights: '',   //用户的权限
      price: '',    //申请金额
      adminAcceptName: '',  //购置管理审批人名称
      // roles: [],
      user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
    }
  },
  created(){
    //请求分页查询数据
    this.load()
  },
  methods:{
    load(){ //请求分页查询数据，加载页面
      this.request.get("assets/page/accept",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
          price: this.price,
          content: this.content,
          submitter: this.submitter,
          accept: this.accept,
          adminAcceptName: this.adminAcceptName,
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
    commitCheck(id){  //验收流程转向调拨流程
      this.request.post("/assets/updateAllocation/" + id + "/" + this.user.id) .then(res => {
        if (res.code === '200') {
          this.$message.success("审批成功")
          this.load()
        } else {
          this.$message.error("审批失败")
        }
        console.log(res)
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
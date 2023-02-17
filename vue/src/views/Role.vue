<template>
<!--  角色管理模块-->
    <div>


      <!--          搜索与其它功能按钮-->
      <div style="padding: 10px 0">
        <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>
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

<!--        <el-upload action="http://localhost:9090/user/import" :show-file-list="false" accept="xlsx" :on-success="handleExcelImportSuccess" style="display: inline-block">-->
<!--          <el-button class="ml-5" type="primary" >导入<i class="el-icon-bottom"></i></el-button>-->
<!--        </el-upload>-->

<!--        <el-popconfirm-->
<!--            confirm-button-text='确定'-->
<!--            cancel-button-text='我再想想'-->
<!--            icon="el-icon-info"-->
<!--            icon-color="red"-->
<!--            title="您确定导出当前所有数据吗？"-->
<!--            @confirm="exp"-->
<!--        >-->
<!--        <el-button class="ml-5" type="primary" slot="reference" >导出<i class="el-icon-top"></i></el-button>-->
<!--        </el-popconfirm>-->
      </div>

      <!--          表单主体-->
      <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="名称" ></el-table-column>
        <el-table-column prop="flag" label="唯一标识" ></el-table-column>
        <el-table-column prop="description" label="描述" ></el-table-column>
        <el-table-column label="操作" width="300">
          <template slot-scope="scope">
            <el-button type="info" @click="selectMenu(scope.row)"  >分配菜单<i class="el-icon-menu"></i></el-button>
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
      <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%" >
        <el-form label-width="80px" size="mid" :model="form" :rules="rules" ref="pass">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" autocomplete="off" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="唯一标识" prop="flag">
            <el-input v-model="form.flag" autocomplete="off" placeholder="请输入唯一标识"></el-input>
          </el-form-item>
          <el-form-item label="描述" >
            <el-input v-model="form.description" autocomplete="off" placeholder="请输入描述"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="save">确 定</el-button>
        </div>
      </el-dialog>


      <!--          编辑时的表单-->
      <el-dialog title="菜单分配" :visible.sync="menuDialogFormVisible" width="30%" >
          <el-tree
              :props="props"
              :data="menuData"
              show-checkbox
              node-key="id"
              ref="tree"
              :default-expanded-keys="expends"
              :default-checked-keys="checks">
            <span class="custom-tree-node" slot-scope="{ node, data }">
              <span><i :class="data.icon" />{{ data.name }}</span>
            </span>

          </el-tree>
        <div slot="footer" class="dialog-footer">
          <el-button @click="menuDialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
        </div>
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
      dialogFormVisible: false,   //编辑时的表单
      multipleSelection: [],      //批量选择
      form: {
        name:'',
        flag:'',
        description:'',
      },                   //点击编辑时，将原有数据送至编辑表单的初始化
      menuDialogFormVisible: false,
      menuData: [],
      props: {
        label: 'name',

      },
      expends: [],  //菜单分配初始化
      checks: [], //菜单分配初始化
      roleId: 0,  //角色id初始化
      roleFlag: '', //角色唯一标识
      rules:{
        name:[
          { required: true, message: '请输入名称', trigger: 'change' },
        ],
        flag:[
          { required: true, message: '请输入唯一标识', trigger: 'change' },

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
      this.request.get("role/page",{
        params:{
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.data.records
        this.total = res.data.total

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
      this.request.delete("/role/" + id).then(res => {
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
      this.request.post("/role/del/batch",ids).then(res => {
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
          this.request.post("/role",this.form).then(res => {
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
      this.name = ""
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
    selectMenu(role){ //选择菜单
      this.menuDialogFormVisible = true
      this.roleId = role.id
      this.roleFlag = role.flag

      //请求菜单数据
      this.request.get("/menu").then(res => {
        this.menuData = res.data

        //把后台返回的菜单数据处理成id的数组    自动展开
        this.expends = this.menuData.map(v => v.id)
      })

      this.request.get("/role/roleMenu/" + this.roleId).then(res => {
        //先渲染弹窗里的元素
        this.menuDialogFormVisible = true   //方法1

        this.checks = res.data
        this.request.get("/menu/ids").then(r => {
          const ids = r.data
          ids.forEach(id => {
            if(!this.checks.includes(id)){
              // this.$nextTick(() => {       //方法2
              //   this.$refs.tree.setChecked(id,false)
              // })
              this.$refs.tree.setChecked(id,false)
            }
          })
        })

      })

    },
    saveRoleMenu(){   //绑定用户具有相应功能模块的权限
      this.request.post("/role/roleMenu/" + this.roleId,this.$refs.tree.getCheckedKeys()).then(res => {
        // console.log(res)
        if(res.code === '200'){
          this.$message.success("绑定成功")
          this.menuDialogFormVisible = false

          // 操作管理员角色后需要重新登录
          if(this.roleFlag === 'ROLE_ADMIN'){
            this.$store.commit("logout")
          }


        }else {
          this.$message.error(res.msg)
        }
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
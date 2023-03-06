<template>
<!--  菜单管理模块-->
    <div>


      <!--          搜索与其它功能按钮-->
<!--      <div style="padding: 10px 0">-->
<!--        <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>-->
<!--        <el-button class="ml-5" type="primary" @click="load" >搜索</el-button>-->
<!--        <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>-->
<!--      </div>-->

      <div style="margin: 10px 0">
        <el-button class="ml-5" type="primary" @click="handleAdds" >新增<i class="el-icon-circle-plus-outline"></i></el-button>

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
      <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
                row-key="id" default-expand-all  @selection-change="handleSelectionChange">>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="名称" ></el-table-column>
        <el-table-column prop="path" label="路径" ></el-table-column>
        <el-table-column prop="pagePath" label="页面路径" ></el-table-column>
        <el-table-column  label="图标" class-name="fontSize18" align="center" label-class-name="fontSize12" >
          <template slot-scope="scope">
            <span :class="scope.row.icon" />
          </template>
        </el-table-column>
        <el-table-column prop="description" label="描述" ></el-table-column>
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
            <el-button class="ml-5" type="primary" @click="handleAdd(scope.row.id)" v-if="!scope.row.pid && !scope.row.path">新增菜单<i class="el-icon-plus"></i></el-button>

          </template>
        </el-table-column>
      </el-table>


      <!--          编辑时的表单-->
      <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="30%" >
        <el-form label-width="80px" size="mid" :model="form" :rules="rules" ref="pass">
          <el-form-item label="名称" prop="name">
            <el-input v-model="form.name" autocomplete="off" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="路径" prop="path">
            <el-input v-model="form.path" autocomplete="off" placeholder="请输入路径"></el-input>
          </el-form-item>
          <el-form-item label="页面路径" prop="pagePath" >
            <el-input v-model="form.pagePath" autocomplete="off" placeholder="请输入页面路径"></el-input>
          </el-form-item>
          <el-form-item label="图标" prop="icon">
            <el-select clearable v-model="form.icon" placeholdeer="请选择" style= "width: 100%">
              <el-option v-for="item in options"  :key="item.name" :label="item.name" :value="item.value">
                <i :class="item.value" />{{item.name}}
              </el-option>
            </el-select>
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
      dialogFormVisible: false,   //编辑时的表单
      multipleSelection: [],      //批量选择
      form: {
        name:'',
        path:'',
        pagePath:'',
        icon:'',
      },                   //点击编辑时，将原有数据送至编辑表单的初始化
      options: [],
      rules:{
        name:[
          { required: true, message: '请输入名称', trigger: 'change' },
        ],
        path:[
          { required: true, message: '请输入路径', trigger: 'change' },
        ],
        pagePath:[
          { required: true, message: '请输入页面路径', trigger: 'change' },
        ],
        icon:[
          { required: true, message: '请选择图标', trigger: 'change' }
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
      this.request.get("/menu",{
        params:{
          name: this.name,
        }
      }).then(res => {
        console.log(res)

        this.tableData = res.data
      })


    },
    handleAdds(){   //对应新增按钮
      this.dialogFormVisible = true
      this.form = {}

      //请求图标的数据
      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })

    },
    handleAdd(pid){  //点击添加功能时弹出表单并清除上一次表单的信息
      this.dialogFormVisible = true //对应新增菜单按钮
      this.form = {}

      //请求图标的数据
      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })

      if(pid){
        this.form.pid = pid
      }
    },
    handleEdit(row){  //点击编辑功能时弹出表单并渲染所选的信息到表单
      this.form = row
      this.dialogFormVisible = true

      //请求图标的数据
      this.request.get("/menu/icons").then(res => {
        this.options = res.data
      })

    },
    handleDel(id){  //点击删除按钮时，删除所选id的信息
      this.request.delete("/menu/" + id).then(res => {
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
      this.request.post("/menu/del/batch",ids).then(res => {
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
          this.request.post("/menu",this.form).then(res => {
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
    exp(){    //导出功能
      window.open("http://localhost:9090/menu/export")
    },
    handleExcelImportSuccess(){   //导入成功提示
      this.$message.success("文件导入成功")
      this.load()
    },

      }
}
</script>

<style>
.headerBg{
  background: #eee!important;
}
.fontSize18{
  font-size: 18px;
}
.fontSize12{
  font-size: 12px;
}
</style>
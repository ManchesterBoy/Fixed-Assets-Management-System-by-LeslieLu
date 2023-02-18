<template>
<!--  文件管理模块-->
    <div>

      <div style="padding: 10px 0">
        <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" class="ml-5" v-model="name"></el-input>
        <el-button class="ml-5" type="primary" @click="load" >搜索</el-button>
        <el-button class="ml-5" type="warning" @click="reset" >重置</el-button>
      </div>

        <div style="margin: 10px 0">
        <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" accept="xlsx" :on-success="handleFileUploadSuccess" style="display: inline-block">
          <el-button class="ml-5" type="primary" >上传文件<i class="el-icon-top"></i></el-button>
        </el-upload>
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


      </div>

      <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="id" label="ID" width="80"></el-table-column>
        <el-table-column prop="name" label="文件名称" ></el-table-column>
        <el-table-column prop="type" label="文件类型" ></el-table-column>
        <el-table-column prop="size" label="文件大小（KB）" ></el-table-column>
        <el-table-column label="下载" >
          <template slot-scope="scope">
            <el-button type="primary"  @click="download(scope.row.url)">
              下载<i class="el-icon-download"></i>
            </el-button>
          </template>
        </el-table-column>
        <el-table-column  label="启用">
<!--          prop="enable"-->
          <template slot-scope="scope">
            <el-switch v-model="scope.row.enable" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">

            <!--                编辑与删除按钮-->
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
  name: "File",
  data(){
    return{
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
    }
  },
  created() {
    this.load()
  },
  methods:{
    load(){ //请求分页查询数据，加载页面
      this.request.get("/file/page",{
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
    handleDel(id){  //点击删除按钮时，删除所选id的信息
      this.request.delete("/file/del/" + id).then(res => {
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
      this.request.post("/file/del/batch",ids).then(res => {
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
    handleFileUploadSuccess(res){ //上传
      console.log(res)

      this.$message.success("文件上传成功")
      this.load()
    },
    download(url){  //下载
      window.open(url)
    },
    changeEnable(row){  //启用按钮

      this.request.post("/file/update",row).then(res => {
        if(res.code === '200'){
          this.$message.success("操作成功")
        }
      })

    },
  }
}
</script>

<style scoped>

</style>
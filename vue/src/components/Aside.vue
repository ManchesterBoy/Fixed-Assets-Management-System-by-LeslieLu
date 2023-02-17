<template>

<!--  侧边栏-->

<!--  background-color="rgb(48, 65, 86)"-->
  <el-menu :default-openeds="opens" style="height: 100%;overflow-x: hidden"
           background-color="rgb(58, 55, 86)"
           text-color="#fff"
           active-text-color="#ffd04b"
           :collapse-transition="false"
           :collapse="isCollapse"
           router
           @select="handleSelect"


  >

    <div style="height: 60px;line-height: 80px;text-align: center">
      <!--            标识图片-->
      <img src="../assets/angel.jpg" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 5px">
      <b style="color: #ccc " v-show="logoTextShow">固定资产管理系统</b>
    </div>

    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
<!--        <el-submenu :index="item.id">-->
<!--          <template slot="title"><i class="el-icon-info"></i>-->
<!--            <span slot="title">主页面</span>-->
<!--          </template>-->
          <el-menu-item :index="item.path">
            <i :class="item.icon"></i>
            <span slot="title" style="margin-left: 15px"> {{item.name}}</span>
          </el-menu-item>
<!--        </el-submenu>-->
      </div>
      <div v-else>
        <el-submenu :index="item.id + ''" >
          <template slot="title">
            <i :class="item.icon"></i>
            <span slot="title" style="margin-left: 15px">{{item.name}}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
              <i :class="subItem.icon"></i>
              <span slot="title"> {{subItem.name}}</span>
            </el-menu-item>
          </div>

        </el-submenu>
      </div>

    </div>

  </el-menu>

</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  data(){
    return{
      menus: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")) : [],
      opens: localStorage.getItem("menus") ? JSON.parse(localStorage.getItem("menus")).map(v => v.id + '') : [],
    }
  },
  methods:{
    handleSelect(){
      this.$route.fullPath.split("/")
    }
  }
}
</script>

<style scoped>

</style>
<template>
<!--  主页模块-->
  <div>
    <el-row :gutter="10" style="margin-bottom: 60px">
<!--      <el-col :span="6">-->
<!--        <el-card style="color: #409EFF">-->
<!--          <div><i class="el-icon-user-solid"></i>用户总数</div>-->
<!--          <div  style="padding: 10px 0;text-align: center;font-weight: bold" v-model="userOnline">-->
<!--            {{userOnline}}-->
<!--          </div>-->
<!--        </el-card>-->
<!--      </el-col>-->
<!--      <el-col :span="6">-->
<!--        <el-card style="color: #67C23A">-->
<!--          <div><i class="el-icon-ice-cream-square"></i>文件总数</div>-->
<!--          <div style="padding: 10px 0;text-align: center;font-weight: bold" v-model="fileOnline">-->
<!--            {{ fileOnline }}-->
<!--          </div>-->
<!--        </el-card>-->
<!--      </el-col>-->
      <el-col :span="6">
        <el-card style="color: #409EFF">
          <div style="text-align: center"><i class="el-icon-basketball"></i>购置管理待处理事件总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold" v-model="purchaseOnline">
            <el-tag style="font-size: 20px" >{{ purchaseOnline }}</el-tag>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #67C23A">
          <div style="text-align: center"><i class="el-icon-football"></i>验收管理待处理事件总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold" v-model="acceptOnline">
            <el-tag style="font-size: 20px" type="success">{{ acceptOnline }}</el-tag>

          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #E6A23C">
          <div style="text-align: center"><i class="el-icon-soccer"></i>调拨管理待处理事件总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold" v-model="allocationOnline">
            <el-tag style="font-size: 20px" type="warning">{{ allocationOnline }}</el-tag>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card style="color: #909399">
          <div style="text-align: center"><i class="el-icon-baseball"></i>处置管理待处理事件总数</div>
          <div style="padding: 10px 0;text-align: center;font-weight: bold" v-model="managementOnline">
            <el-tag style="font-size: 20px" type="info">{{ managementOnline }}</el-tag>
          </div>
        </el-card>
      </el-col>

      <el-row>
        <el-col :span="24" style="margin-top:10px; ">
          <el-card style="color: #F56C6C">
            <div style="text-align: center"><i class="el-icon-coffee-cup"></i>已完成处理事件总数</div>
            <div style="padding: 10px 0;text-align: center;font-weight: bold" v-model="finalOnline">
              <el-tag style="font-size: 20px" type="danger" >{{ finalOnline }}</el-tag>
            </div>
          </el-card>
        </el-col>
      </el-row>

    </el-row>



<!--    <h1>这是主页</h1>-->
    <el-row>
      <el-col :span="12">
        <div id="chart" style="width: 500px;height: 450px"></div>
      </el-col>
      <el-col :span="12">
        <div id="chart1" style="width: 500px;height: 450px"></div>
      </el-col>
      <el-col :span="12">
        <div id="pie" style="width: 800px;height: 700px;margin-left: 230px"></div>
      </el-col>
    </el-row>


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

<!--    日历-->
    <el-calendar v-model="value">
    </el-calendar>


  </div>

</template>

<script>

import * as echarts from 'echarts'



export default {
  name: "HomeView",
  data(){
    return{
      // userOnline: '',
      // fileOnline: '',
      purchaseOnline: '', //显示处于购置管理的人数
      acceptOnline: '',   //显示处于验收管理的人数
      allocationOnline: '',   //显示处于调拨管理的人数
      managementOnline: '',   //显示处于处置管理的人数
      finalOnline: '',    //显示处于事件结束的人数
      value: new Date(),
    }
  },
  created() {
    // this.userAmount()
    this.assetsAmount()
  },
  methods:{
    // //显示用户总数
    // userAmount(){
    //   this.request.get("/echarts/userOnline").then(res => {
    //     // console.log(res)
    //     this.userOnline = res.data
    //   })
    //
    //   this.request.get("/echarts/fileOnline").then(res => {
    //     // console.log(res)
    //     this.fileOnline = res.data
    //   })
    // },
    assetsAmount(){
      this.request.get("/echarts/purchaseOnline").then(res => {   //购置管理待处理事件总数
            // console.log(res)
            this.purchaseOnline = res.data
          })

      this.request.get("/echarts/acceptOnline").then(res => {   //验收管理待处理事件总数
        // console.log(res)
        this.acceptOnline = res.data
      })

      this.request.get("/echarts/allocationOnline").then(res => {   //调拨管理待处理事件总数
        // console.log(res)
        this.allocationOnline = res.data
      })

      this.request.get("/echarts/managementOnline").then(res => {   //处置管理待处理事件总数
        // console.log(res)
        this.managementOnline = res.data
      })

      this.request.get("/echarts/finalOnline").then(res => {   //处置管理待处理事件总数
        console.log(res)
        this.finalOnline = res.data
      })

    }
  },
  mounted() {   //页面元素渲染之后再触发

    //折线图
    var option = {
      title: {
        text: '各季度注册用户人数',
        subtext: '趋势图',
        left: 'center',
        // padding: [0,0,100,0]
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
        // right: 'right',
      },

      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      xAxis: {
        type: 'category',
        data: ['第一季度','第二季度','第三季度','第四季度']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: "数量",
          data: [],
          type: 'bar',

        },
        {
          name: "数量 ",
          data: [],
          type: 'line',
          label: {
            show: true,
            formatter: "{c} ",
            // rich: {
            //   c:{
            //     color:"#67C23A",
            //     fontSize: 20,
            //     lineHeight: 20,
            //     align: 'center',
            //   }
            // },
            textStyle: {
              color: "#409EFF",
              align: "center",
              fontSize: 20,
            },
          },

        },

      ]
    };

    //折线图
    var option1 = {
      title: {
        text: '各季度事件提交件数',
        subtext: '趋势图',
        left: 'center',
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left'
        // right: 'right',
      },
      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      xAxis: {
        type: 'category',
        data: ['第一季度','第二季度','第三季度','第四季度']
      },
      yAxis: {
        type: 'value'
      },
      series: [
        {
          name: "数量",
          data: [],
          type: 'bar',

        },
        {
          name: "数量 ",
          data: [],
          type: 'line',
          label: {
            show: true,
            formatter: "{c} ",
            // rich: {
            //   c:{
            //     color:"#67C23A",
            //     fontSize: 20,
            //     lineHeight: 20,
            //     align: 'center',
            //   }
            // },
            textStyle: {
              color: "#409EFF",
              align: "center",
              fontSize: 20,
            },
          },
        },


      ]
    };


    //饼图
    var pieOption = {
      title: {
        text: '各地区人数数量统计',
        subtext: '比例图',
        left: 'center'
      },
      tooltip: {
        trigger: 'item'
      },
      legend: {
        orient: 'vertical',
        left: 'left',
        // padding:[10,500,0,0],
      },
      toolbox: {
        show: true,
        feature: {
          mark: { show: true },
          dataView: { show: true, readOnly: false },
          restore: { show: true },
          saveAsImage: { show: true }
        }
      },
      series: [
        {
          name: '地区',
          type: 'pie',
          // radius: '70%',
          radius: ["45%", "65%"],
          // center: ["50%", "50%"],
          avoidLabelOverlap: false,
          label: {
            show: true,
            formatter: "{d|{d}%} ({c|{c}}) \n {b|{b}}",
            rich: {
              d: {
                color: '#303133',
                fontSize: 16,
                lineHeight: 24,
                height: 24
              },
              b: {
                color: "#E6A23C",
                fontSize: 14,
                lineHeight: 20,
                align: 'left',
              },
              c:{
                color:"#409EFF",
                fontSize: 16,
                lineHeight: 20,
                align: 'left',
              }
            },
            textStyle: {
              color: "black",
              align: "right",
              fontSize: 14,
            },
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: "rgba(0, 0, 0, 0.5)",
            },
          },
          // label:{   //饼图图形上的文本标签
          //   normal:{
          //     show:true,
          //     formatter: "{d}%({c})",
          //     position: "inner",  //标签的位置
          //     textStyle: {
          //       fontWeight: "bold",
          //       fontSize: 12 , //字体大小
          //       color: "black",
          //     },
          //
          //
          //   }
          // },
          data: [], //填空
          // emphasis: {
          //   itemStyle: {
          //     shadowBlur: 10,
          //     shadowOffsetX: 0,
          //     shadowColor: 'rgba(0, 0, 0, 0.5)'
          //   }
          // }
        }
      ]
    };



    var chartDom = document.getElementById('chart');
    var myChart = echarts.init(chartDom);

    var chartDom1 = document.getElementById('chart1');
    var myChart1 = echarts.init(chartDom1);

    var pieDom = document.getElementById('pie');
    var pieChart = echarts.init(pieDom);


    this.request.get("/echarts/address").then(res => {
      pieOption.series[0].data = [
        {name: "广东" ,value: res.data[0]},
        {name: "上海" ,value: res.data[1]},
        {name: "北京" ,value: res.data[2]},
        {name: "香港" ,value: res.data[3]},
        {name: "澳门" ,value: res.data[4]},
        {name: "广西" ,value: res.data[5]},
      ]

      pieChart.setOption(pieOption);
    })

    this.request.get("/echarts/members").then(res => {
      //填空
      // option.xAxis.data = res.data.x
      option.series[0].data = res.data
      option.series[1].data = res.data


      //在数据准备完毕之后在set
      myChart.setOption(option);

      // pieOption.series[0].data = [
      //   {name: "第一季度" ,value: res.data[0]},
      //   {name: "第二季度" ,value: res.data[1]},
      //   {name: "第三季度" ,value: res.data[2]},
      //   {name: "第四季度" ,value: res.data[3]},
      // ]
      //
      // pieChart.setOption(pieOption);
    })

    this.request.get("/echarts/event").then(res => {
      //填空
      // option.xAxis.data = res.data.x
      option1.series[0].data = res.data
      option1.series[1].data = res.data


      //在数据准备完毕之后在set
      myChart1.setOption(option1);

      // pieOption.series[0].data = [
      //   {name: "第一季度" ,value: res.data[0]},
      //   {name: "第二季度" ,value: res.data[1]},
      //   {name: "第三季度" ,value: res.data[2]},
      //   {name: "第四季度" ,value: res.data[3]},
      // ]
      //
      // pieChart.setOption(pieOption);
    })


  },

}
</script>

<style scoped>

</style>
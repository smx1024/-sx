<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item>
        <el-select v-model="searchObj.type" clearable placeholder="请选择">
          <el-option label="学员登录数统计" value="login_num" />
          <el-option label="学员注册数统计" value="register_num" />
          <el-option label="课程播放数统计" value="video_view_num" />
          <el-option label="每日课程数统计" value="course_num" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="searchObj.begin" type="date" placeholder="选择开始日期" value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="searchObj.end" type="date" placeholder="选择截止日期" value-format="yyyy-MM-dd" />
      </el-form-item>
      <el-button :disabled="btnDisabled" type="primary" icon="el-icon-search" @click="showChart()">查询</el-button>
    </el-form>
    <div class="chart-container">
      <el-row style="margin-left: 60px ;">
        <el-col :span="12">
          <div id="chart1" class="chart1" style="height: 300px; width: 500px" />
        </el-col>
        <el-col :span="12">
          <div id="chart2" class="chart2" style="height: 300px; width: 500px" />
        </el-col>
      </el-row>
      <el-row style="margin-left: 60px;">
        <el-col :span="12">
          <div id="chart3" class="chart3" style="height: 300px; width: 500px" />
        </el-col>
        <el-col :span="12">
          <div id="chart4" class="chart4" style="height: 300px; width: 500px" />
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import echarts from "echarts";
import staApi from "@/api/sta";
export default {
  data() {
    return {
      searchObj: {
        begin: "",
        end: "",
        type: "",
      },
      btnDisabled: false,
      chart: null,
      title: "",
      xData: [],
      yData: [],
    };
  },
  methods: {
    showChart() {
      staApi.getShowData(this.searchObj).then(resp => {
        this.xData = resp.data.xlist
        this.yData = resp.data.ylist
        this.setChart()
      }
      )
    },
    setChart() {
      // 基于准备好的dom，初始化echarts实例
      this.chart1 = echarts.init(document.getElementById("chart1"));
      this.chart2 = echarts.init(document.getElementById("chart2"));
      this.chart3 = echarts.init(document.getElementById("chart3"));
      this.chart4 = echarts.init(document.getElementById("chart4"));
      // console.log(this.chart)
      // 指定图表的配置项和数据
      var option = {
        // x轴是类目轴（离散数据）,必须通过data设置类目数据
        xAxis: {
          type: "category",
          data: this.xData
        },
        // y轴是数据轴（连续数据）
        yAxis: {
          type: "value",
        },
        // 系列列表。每个系列通过 type 决定自己的图表类型
        series: [
          {
            // 系列中的数据内容数组
            data: this.yData,
            // 折线图
            type: "line",
          },
        ],
      };
      this.chart1.setOption(option);
      this.chart2.setOption(option);
      this.chart3.setOption(option);
      this.chart4.setOption(option);
    },
  },
  created() { },
};
</script>

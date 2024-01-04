<!--
 * new page
 * @author: sx
 * @since: 2023-10-29
 * list.vue
-->
<template>
  <div>
    <el-form :inline="true" class="demo-form-inline" style="margin-left: 20px; margin-top: 12px;">
      <el-form-item label="名称">
        <el-input v-model="teacherQuery.name" placeholder="请输入名称"></el-input>
      </el-form-item>
      <el-form-item label="级别">
        <el-select v-model="teacherQuery.level" placeholder="讲师头衔">
          <el-option label="高级讲师" :value="1"></el-option>
          <el-option label="特级讲师" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
        <el-time-picker placeholder="选择开始时间" v-model="teacherQuery.begin" value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00" type="datetime"></el-time-picker>
      </el-form-item>
      <el-form-item>
        <el-time-picker placeholder="选择截止时间" v-model="teacherQuery.end" value-format="yyyy-MM-dd HH:mm:ss"
          default-time="00:00:00" type="datetime"></el-time-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="teacherList" style="width: 100%;" border fit highlight-current-row>
      <el-table-column prop="date" label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="名称" width="80"> </el-table-column>
      <el-table-column label="头衔" width="80">
        <template slot-scope="scope">
          {{ scope.row.level === 1 ? "高级讲师" : "首席讲师" }}
        </template>
      </el-table-column>
      <el-table-column prop="intro" label="资历" />
      <el-table-column prop="gmtCreate" label="添加时间" width="160" />
      <el-table-column prop="sort" label="排序" width="60" />
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/teacher/edit/' + scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination @size-change="handleSizeChange" @current-change="getTeacher" :current-page="page"
      :page-sizes="[3, 5, 7, 9]" :page-size="limit" style="padding: 20px ;text-align: center;"
      layout=" prev,pager, next, jumper,sizes,total" :total="total">
    </el-pagination>
  </div>
</template>


<script >
import teacher from '@/api/teacher.js'

export default {
  data() {
    return {
      page: 1,
      limit: 3,
      total: 10,
      teacherQuery: {},
      teacherList: null
    }
  },
  created() {
    this.getTeacher()
  },
  methods: {
    async getTeacher(page = 1) {
      this.page = page
      let res = await teacher.getTeacher(this.page, this.limit, this.teacherQuery)
      // this.teacherList = res.data.rows
      if (res.code === 20000) {
        this.teacherList = res.data.rows
        this.total = res.data.total
        console.log(res.data.rows)
        console.log(this.teacherList)
      }
    },
    handleSizeChange(val) {
      this.limit = val
      this.getTeacher()
    },
    getList() {
      //模糊查询
      this.getTeacher()
    },
    resetData() {
      //清空
      this.teacherQuery = {}
      this.getTeacher()
    },
    async removeDataById(id) {
      console.log(id)
      //删除
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async () => {
        let result = await teacher.removeTeacher(id)
        if (result.code === 20000) {
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          this.getTeacher()
        } else {
          this.$message({
            type: 'error',
            message: '删除失败'
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    }
  }


}

</script>

<style scoped></style>

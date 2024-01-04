<!--
 * new page
 * @author: sx
 * @since: 2023-10-29
 * save.vue
-->
<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="teacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number v-model="teacher.sort" controls-position="right" :min="0" />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="teacher.level" clearable placeholder="选择讲师头衔">
          <!--
数据类型一定要和取出的json中的一致，否则没法回填
因此，这里value使用动态绑定的值，保证其数据类型是number
-->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="teacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="teacher.intro" :rows="10" type="textarea" />
      </el-form-item>
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="teacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow = true">更换头像
        </el-button>
        <!--
v-show：是否显示上传组件
:key：类似于id，如果一个页面多个图片上传控件，可以做区分
:url：后台上传的url地址
@close：关闭上传组件
@crop-upload-success：上传成功后的回调 -->
        <image-cropper v-show="imagecropperShow" :width="300" :height="300" :key="imagecropperKey"
          :url="BASE_API + '/eduoss/fileoss/upload'" field="file" @close="close" @crop-upload-success="cropSuccess" />
      </el-form-item>

      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="updateOrSave">保存</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="clear">取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script >
import teacher from '@/api/teacher';
import panThumb from '@/components/PanThumb';
import imageCropper from '@/components/ImageCropper';

export default {
  //引入组件
  components: {
    panThumb,
    imageCropper
  },
  data() {
    return {
      teacher: {
        id: '',
        name: '',
        level: '',
        intro: '',
        career: '',
        sort: '',
        //默认头像
        avatar: 'https://guliedu-sx.oss-cn-beijing.aliyuncs.com/2023/10/31/af0225f7ee634ed9b98945d1d5ad1647file.png'
      },
      //保存按钮的禁用状态
      saveBtnDisabled: false,
      //图片裁剪组件的显示状态
      imagecropperShow: false,
      //图片裁剪组件的key
      imagecropperKey: 0,
      BASE_API: process.env.VUE_APP_BASE_API

    };
  },
  created() {
    //获取路由中的参数
    let id = this.$route.params.id
    if (id) {
      this.getTeacherInfo(id)
    }
  },
  methods: {
    updateOrSave() {
      if (this.teacher.id) {
        this.updateTeacher()
      } else {
        // this.teacher = {}
        this.saveTeacher()
      }
    },
    async saveTeacher() {
      console.log(this.teacher.name);
      let result = await teacher.addTeacher(this.teacher)
      if (result.code === 20000) {
        console.log('添加成功');
        this.$message({
          type: 'success',
          message: '添加成功'
        })
        //路由跳转
        this.$router.push('/teacher/table')
      }
    },
    clear() {
      this.teacher = {}
      this.$router.push('/teacher/table')
    },
    async getTeacherInfo(id) {
      let result = await teacher.getTeacherInfo(id)
      if (result.code === 20000) {
        this.teacher = result.data.teacher
      }
    },
    async updateTeacher() {
      let result = await teacher.updateTeacherInfo(this.teacher)
      if (result.code === 20000) {
        this.$message({
          type: 'success',
          message: '修改成功'
        })
        //路由跳转
        this.$router.push('/teacher/table')
      }
    },
    close() {
      this.imagecropperShow = false
    },
    //图片裁剪成功后的回调
    cropSuccess(data) {
      this.teacher.avatar = data.url
      this.imagecropperShow = false
      this.imagecropperKey += 1
    }
  }
}
</script>

<style scoped></style>

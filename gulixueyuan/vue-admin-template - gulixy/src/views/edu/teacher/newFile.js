import teacher from '@/api/teacher';

export default (await import('vue')).default.extend({
data() {
return {
page: 1,
limit: 3,
total: 10,
teacherQuery: {},
teacherList: null
};
},
created() {
this.getTeacher();
},
methods: {
async getTeacher(page = 1) {
this.page = page;
let res = await teacher.getTeacher(this.page, this.limit, this.teacherQuery);
// this.teacherList = res.data.rows
if (res.code === 20000) {
this.teacherList = res.data.rows;
this.total = res.data.total;
console.log(res.data.rows);
console.log(this.teacherList);

}
},
handleSizeChange(val) {
this.limit = val;
this.getTeacher();
},
getList() {
//模糊查询
this.getTeacher();
},
resetData() {
//清空
this.teacherQuery = {};
this.getTeacher();
},
async removeDataById(id) {
//删除
let result = await teacher.removeTeacher(id), {
if:  }; (result.code === 20000); {
this.$message({
type: 'success',
message: '删除成功'
});
this.getTeacher();
}
}
}
});

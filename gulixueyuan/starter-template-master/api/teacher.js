import request from '@/utils/request'

export default{
    // 1.讲师列表（条件查询分页）
    // current当前页 limit每页记录数 teacherQuery条件对象
    getTeacherListPage(current,limit,teacherQuery){
        return request({
            // url: '/table/list'+current+"/"+limit,
            url: `/eduservice/teacher/getTeacherFrontList/${current}/${limit}`,
            method: 'post',
          })
    },
    // 2.讲师详情的方法
    getTeacherInfo(id){
        return request({
            url: `/eduservice/teacher/getTeacherFrontInfo/${id}`,
            method: 'get',
          })
    }
}
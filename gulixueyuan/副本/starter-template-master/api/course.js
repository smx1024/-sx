import request from '@/utils/request'

export default {
    //条件查询课程
    getCourseList(page,limit,searchObj) {
        return request({
            url: `/eduservice/coursefront/getFrontCourseList/${page}/${limit}`,
            method: 'post',
            data:searchObj
          })
    },
    //讲师详情的方法
    getAllSubject() {
        return request({
            url: `/eduservice/subject/getAllSubject`,
            method: 'get'
          })
    },
    //根据课程id查询课程信息
    getCourseInfo(courseId) {
        return request({
            url: `/eduservice/coursefront/getFrontCourseInfo/${courseId}`,
            method: 'get'
          })
    }
}
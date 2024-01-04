import request from '@/utils/request'

const GETTEACHER_URL = '/eduservice/teacher/pageTeacherCondition'
// export const getTeacher = (current, limit, teacherQuery) => {
//   request.post(GETTEACHER_URL + `/${current}/${limit}`, teacherQuery)

export default {
  getTeacher(current, limit, teacherQuery) {
    return request({
      url: GETTEACHER_URL + `/${current}/${limit}`,
      method: 'post',
      data: teacherQuery
    })
  },
  getAllTeacher(){
    return request({
      url: `/eduservice/teacher/findAll`,
      method: 'get'
    })
  },
  removeTeacher(id){
    return request({
      url: `/eduservice/teacher/${id}`,
      method: 'delete'
    })
  },
  addTeacher(teacher){
    return request({
      url: `/eduservice/teacher/addTeacher`,
      method: 'post',
      data: teacher
    })
  },
  getTeacherInfo(id){
    return request({
      url: `/eduservice/teacher/getTeacher/${id}`,
      method: 'get'
    })
  },
  updateTeacherInfo(teacher){
    return request({
      url: `/eduservice/teacher/updateTeacher`,
      method: 'post',
      data: teacher
    })
  },
}
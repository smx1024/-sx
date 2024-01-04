import request from '@/utils/request'

export default {

    // GET /admin/edu/subject/getAllSubject
    // 获取所有分类
    getAllSubject() {
        return request({
            url: '/eduservice/subject/getAllSubject',
            method: 'get'
        })
    },
}


import request from '@/utils/request'
export default {
    getPageList(page, limit, courseId) {
        return request({
            url: `/eduservice/educomment/getCommentList/${page}/${limit}`,
            method: 'get',
            params: { courseId: courseId }
        })
    },
    addComment(comment) {
        return request({
            url: `/eduservice/educomment/addComment`,
            method: 'post',
            data: comment
        })
    }
}

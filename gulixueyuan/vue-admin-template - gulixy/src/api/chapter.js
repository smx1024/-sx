import request from '@/utils/request'

export default {
    //获得课程信息
    getAllChapterVideo(courseId) {
        return request({
            url: '/eduservice/chapter/getChapterVideo/'+courseId,
            method: 'get',
        })
    },
    //根据id查询章节
    getChapterById(chapterID) {
        return request({
            url: `/eduservice/chapter/getChapterInfo/${chapterID}`,
            method: `get`,
        })
    },
    //修改章节
    updateChapter(chapter) {
        return request({
            url: `/eduservice/chapter/updateChapter`,
            method: `post`,
            data: chapter
        })
    },
    //删除章节
    deleteById(chapterID) {
        return request({
            url: `/eduservice/chapter/${chapterID}`,
            method: `delete`,
        })
    },
    //添加章节
    addChapter(chapter) {
        return request({
            url: `/eduservice/chapter/addChapter`,
            method: `post`,
            data: chapter
        })
    },

}
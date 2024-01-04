import request  from '@/utils/request'

export default {
    //1.获取banner列表
    getIndexData(){
        return request({
            url: '/eduservice/indexfront/index',
            method: 'get'
        })
    }
}
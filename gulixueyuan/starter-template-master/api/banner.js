import request from '@/utils/request'

export default {
    //1.获取banner列表
    getListBanner(){
        return request({
            url: '/educms/bannerfront/getAllBanner',
            method: 'get'
        })
    }
}
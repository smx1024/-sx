import request from '@/utils/request'

export default {
    //生成数据
    createStaData(day){
        return request({
            url: `/staservice/sta/registerCount/${day}`,
            method: 'post'
        })
    },
    //根据时间范围查询数据
    getShowData(searchObj){
        return request({
            url: `/staservice/sta/showData/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get'
        })
    }
}
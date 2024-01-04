package com.sx.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sx.eduservice.entity.EduVideo;
import com.sx.eduservice.fegin.VodClient;
import com.sx.eduservice.mapper.EduVideoMapper;
import com.sx.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-02
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    @Override
    public void removeVideoByCourseId(String courseId) {
        // 根据课程id查询所有的视频id
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> list = list(wrapper);
        list.stream().filter(item->item.getVideoSourceId()!=null).map(EduVideo::getVideoSourceId).forEach(vodClient::removeAliyunVideo);

        QueryWrapper<EduVideo> wrapperVideo = new QueryWrapper<>();
        wrapperVideo.eq("course_id", courseId);
        int delete = baseMapper.delete(wrapperVideo);
        if (delete == 0) {
            throw new RuntimeException("删除失败");
        }
    }
}

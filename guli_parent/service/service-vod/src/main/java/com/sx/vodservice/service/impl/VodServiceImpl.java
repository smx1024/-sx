package com.sx.vodservice.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadFileStreamRequest;
import com.aliyun.vod.upload.resp.UploadFileStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.sx.serivcebase.exception.GuliException;
import com.sx.vodservice.Utils.ConstantVodUtils;
import com.sx.vodservice.Utils.InitVodClient;
import com.sx.vodservice.service.VodService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadAliyunVideo(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String title = filename.substring(0, filename.lastIndexOf("."));
        String videoId = null;
        try {
            InputStream inputStream = file.getInputStream();

            UploadFileStreamRequest request = new UploadFileStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET, title, filename);
            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadFileStreamResponse response = uploader.uploadFileStream(request);

            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else {
                /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
                videoId = response.getVideoId();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return videoId;
    }

    @Override
    public void removeAliyunVideo(String id) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            deleteVideoRequest.setVideoIds(id);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(deleteVideoRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    @Override
    public void removeMoreAliyunVideo(List videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            String s = StringUtils.join(videoIdList.toArray(), ",");
            deleteVideoRequest.setVideoIds(s);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(deleteVideoRequest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除多个视频失败");
        }
    }

    //根据视频id获取视频凭证
    @Override
    public String getPlayAuth(String id) {
        String accesskeyId = ConstantVodUtils.ACCESS_KEY_ID;
        String accesskeySecret = ConstantVodUtils.ACCESS_KEY_SECRET;

        try {
            //创建初始化对象
            DefaultAcsClient cl = InitVodClient.initVodClient(accesskeyId,accesskeySecret);
            //创建获取视频地址request对象和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request对象设置视频id值
            request.setVideoId(id);

            GetVideoPlayAuthResponse response = cl.getAcsResponse(request);

            //获取视频播放凭证
            return response.getPlayAuth();

        } catch (Exception e) {
            e.printStackTrace();
            throw new GuliException(20001,"获取视频凭证失败");
        }

    }

}

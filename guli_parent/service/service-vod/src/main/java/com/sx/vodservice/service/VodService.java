package com.sx.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadAliyunVideo(MultipartFile file);

    void removeAliyunVideo(String id);

    void removeMoreAliyunVideo(List videoIdList);

    String getPlayAuth(String id);
}

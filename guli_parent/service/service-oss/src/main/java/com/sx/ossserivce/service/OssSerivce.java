package com.sx.ossserivce.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssSerivce {


     String uploadFile(MultipartFile file);

}

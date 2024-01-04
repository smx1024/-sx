package com.sx.eduservice.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class VideoVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String videoSourceId;//视频id

}

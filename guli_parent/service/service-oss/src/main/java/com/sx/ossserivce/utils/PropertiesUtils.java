package com.sx.ossserivce.utils;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Setter
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class PropertiesUtils implements InitializingBean {
    private String endpoint;
    private String keyid;
    private String keysecret;
    private String bucketname;

    //定义公开静态常量
    public static String END_POINT;
    public static String KEY_ID;
    public static String KEY_SECRET;
    public static String BUCKET_NAME;
    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        KEY_ID = keyid;
        KEY_SECRET = keysecret;
        BUCKET_NAME = bucketname;
    }
}

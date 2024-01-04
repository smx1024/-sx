package com.sx.vodservice.Utils;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.vod.file")
@Setter
//不加这设置不进去
public class ConstantVodUtils implements InitializingBean {

    private String keyid;
    private String keysecret;
    //读取配置文件内容
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    //定义公开静态常量

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID=keyid;
        ACCESS_KEY_SECRET=keysecret;
    }
}

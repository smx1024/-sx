package com.sx.Userservice.utils;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("aliyun.app")
@Setter
public class AliEntityUtils  implements InitializingBean {
    private String appid;
    private String privateKey;
    private String alipayPublicKey;

    public static String APPID;
    public static String PRIVATEKEY;
    public static String ALIPAYPUBLICKEY;
    @Override
    public void afterPropertiesSet() throws Exception {
            APPID=appid;
            PRIVATEKEY=privateKey;
            ALIPAYPUBLICKEY=alipayPublicKey;
    }
}

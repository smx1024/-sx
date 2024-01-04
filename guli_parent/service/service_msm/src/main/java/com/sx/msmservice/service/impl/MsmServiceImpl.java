package com.sx.msmservice.service.impl;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.sx.msmservice.service.MsmService;
import darabonba.core.client.ClientOverrideConfiguration;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean send(String phone, String Code) {
        StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
                .accessKeyId("LTAI5tBomaXoFY8cb4opP6es")
                .accessKeySecret("xmx3UlvbJ4pMc8HG88NvBdUroo4fAP")
                .build());
        AsyncClient client = AsyncClient.builder()
                .region("cn-beijing") // Region ID
                .credentialsProvider(provider)
                .overrideConfiguration(
                        ClientOverrideConfiguration.create()
                                // Endpoint 请参考 https://api.aliyun.com/product/Dysmsapi
                                .setEndpointOverride("dysmsapi.aliyuncs.com")).build();
        // Parameter settings for API request
        SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
                .signName("阿里云短信测试")
                .templateCode("SMS_154950909")
                .phoneNumbers(phone)
                .templateParam("{\"code\":\"" + Code + "\"}")
                .build();
        CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
        SendSmsResponse resp = null;
        try {
            resp = response.get();
            client.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            client.close();
            return false;
        }
    }
}

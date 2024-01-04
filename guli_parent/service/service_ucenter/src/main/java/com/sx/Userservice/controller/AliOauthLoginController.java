package com.sx.Userservice.controller;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.alibaba.nacos.client.utils.JSONUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoAuthRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoAuthResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sx.Userservice.entity.UcenterMember;
import com.sx.Userservice.service.UcenterMemberService;
import com.sx.Userservice.utils.AliEntityUtils;
import com.sx.commonutils.JwtUtils;
import com.sx.serivcebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;

@Controller
//@CrossOrigin
@RequestMapping("/admin/user")
public class AliOauthLoginController {
    @Autowired
    private UcenterMemberService ucenterMemberService;


    @GetMapping("/login")
    public String AliLogin() throws AlipayApiException {
        String url = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm?app_id=2021004117619151&scope=auth_user&redirect_uri=https://quetzal-right-mite.ngrok-free.app/admin/user/callback";
        return "redirect:" + url;

    }

    @RequestMapping("callback")
    public String getTokenByCode(@RequestParam(value = "auth_code") String code) throws AlipayApiException {
        AlipayClient alipayClient = getClient();
        AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
        request.setCode(code);
        request.setGrantType("authorization_code");
        request.setRefreshToken("201208134b203fe6c11548bcabd8da5bb087a83b");
        AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());
        if (response.isSuccess()) {
            //判断数据库中是否存在该用户
            String userId = response.getUserId();
            UcenterMember ucenterMember = ucenterMemberService.getByOpenId(userId);
            if (ucenterMember == null) {
                String accessToken = response.getAccessToken();
                String userSponse = getUserByToken(accessToken);
                ObjectMapper mapper = new ObjectMapper();
                HashMap hashMap = null;
                try {
                    hashMap = mapper.readValue(userSponse, HashMap.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                HashMap<String,Object> userInfo = (HashMap<String, Object>) hashMap.get("alipay_user_info_share_response");
                String id = (String) userInfo.get("user_id");
                String nickName = (String) userInfo.get("nick_name");
                String avatar = (String) userInfo.get("avatar");
                ucenterMember = new UcenterMember();
                ucenterMember.setAvatar(avatar);
                ucenterMember.setOpenid(id);
                ucenterMember.setNickname(nickName);
                ucenterMemberService.save(ucenterMember);
            }
            String jwtToken = JwtUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());

            //最后，返回首页，通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } else {
            throw new GuliException(20001, "登录失败");
        }
    }

    public String getUserByToken(String accessToken) throws AlipayApiException {
        AlipayClient alipayClient = getClient();
        AlipayUserInfoShareRequest request = new AlipayUserInfoShareRequest();
        AlipayUserInfoShareResponse response = alipayClient.execute(request, accessToken);
        if (response.isSuccess()) {

            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return response.getBody();

    }


    public AlipayClient getClient() {
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", AliEntityUtils.APPID, AliEntityUtils.PRIVATEKEY, "json", "UTF-8", AliEntityUtils.ALIPAYPUBLICKEY, "RSA2");
        System.out.println(AliEntityUtils.APPID);
        System.out.println(AliEntityUtils.PRIVATEKEY);
        System.out.println(AliEntityUtils.ALIPAYPUBLICKEY);
        return alipayClient;
    }
}

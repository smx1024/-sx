package com.sx.msmservice.controlelr;

import com.sx.commonutils.Result;
import com.sx.msmservice.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@RestController
//@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    //发送短信
    @GetMapping("send/{phone}")
    public Result sendMsm(@PathVariable("phone") String phone) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }
        String code = sb.toString();
        boolean isSend = msmService.send(phone, code);
        if (isSend) {
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return Result.ok();
        } else {
            return Result.error().message("短信发送失败");
        }
    }
}

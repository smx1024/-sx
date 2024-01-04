package com.sx.staservice.fegin;

import com.sx.commonutils.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("service-ucenter")
public interface UserFegin {

    @GetMapping("/educenter/member/countRegister/{day}")
    public Result countRegister(@PathVariable String day);
}

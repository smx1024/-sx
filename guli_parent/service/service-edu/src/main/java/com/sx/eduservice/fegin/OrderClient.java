package com.sx.eduservice.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient(value = "service-order",fallback = OrderClientImpl.class)
public interface OrderClient {

    @GetMapping("/eduorder/order/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable("memberId") String memberId,@PathVariable("course") String courseId);


}

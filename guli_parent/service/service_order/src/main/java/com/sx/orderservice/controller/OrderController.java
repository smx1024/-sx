package com.sx.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sx.commonutils.JwtUtils;
import com.sx.commonutils.Result;
import com.sx.orderservice.entity.Order;
import com.sx.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduorder/order")
public class OrderController {
    @Autowired
 private     OrderService orderService;
        // 1. 生成订单的方法
    @GetMapping("/createOrder/{courseId}")
    public Result createOrder(@PathVariable  String courseId, HttpServletRequest request){
        if(JwtUtils.getMemberIdByJwtToken(request)==null){
            return Result.error().code(28004).message("请登录");
        }
        String id = JwtUtils.getMemberIdByJwtToken(request);
        String orderId =orderService.createOrder(courseId,id);
        return Result.ok().data("orderId",orderId);
    }
    // 2. 根据订单id查询订单信息
    @GetMapping("/getOrderInfo/{orderId}")
    public Result getOrderInfo(@PathVariable String orderId){
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("order_no",orderId);
        Order order = orderService.getOne(orderQueryWrapper);
        return Result.ok().data("item",order);
    }

    @GetMapping("/isBuyCourse/{memberId}/{courseId}")
    public Boolean isBuyCourse(@PathVariable String memberId,@PathVariable String courseId)	  {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);//支付状态 【1】代表已支付
        int result = orderService.count(wrapper);

        if (result>0){//已支付
            return true;
        }else {
            return false;
        }
    }

}


package com.sx.orderservice.controller;


import com.sx.commonutils.Result;
import com.sx.orderservice.service.PayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduorder/pay-log")
public class PayLogController {
    @Autowired
    private PayLogService payLogService;
        //生成支付二维码‘
    @GetMapping("/createNative/{orderNo}")
    public Result createNative(@PathVariable String orderNo){
        Map map = payLogService.createNative(orderNo);
        return Result.ok().data(map);
    }
        //根据订单号查询订单支付状态
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(@PathVariable String orderNo){
        Map<String,String> map = payLogService.queryPayStatus(orderNo);
        if(map==null){
            return Result.error().message("支付出错了");
        }
        System.out.println(map);
        if(map.get("trade_status").equals("TRADE_SUCCESS")){
            //添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            System.out.println("支付成功");
            return Result.ok().message("支付成功");
        }
        return Result.ok().code(25000).message("支付中");
    }
}


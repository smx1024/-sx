package com.sx.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sx.orderservice.entity.Order;
import com.sx.orderservice.entity.PayLog;
import com.sx.orderservice.mapper.PayLogMapper;
import com.sx.orderservice.service.OrderService;
import com.sx.orderservice.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sx.serivcebase.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        String privatekey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDINUybDzm5sxbvaxRVqKT1aqwINv1uJHWYBWTqRV3d0NV1MBihbF3bs+qItLYxukO743qzrS9L6RawMQbEy+szB5B0Y2d9J0ONK6ye5SeD5I90Rzqh/wy9G10AdIp3ZAuWeSbk/39Liyyt6dHWpkxe14fASifH6MSwaxg9FNhSfhXc1TV2mR/eu4UveHkHFIdNL/kbVpE55onf9sz6i/f0ail3kKNZWekBS+6ZkUnK7uI5v+dMSnTqA2SI13Tikw3CIOhCEy0DEB6DFg7GGhaUm2k9EJ6b7+cn3V4WLd0JSPxtrpJIvUGmEndN2kX6G8u5JkNMsJU8GksXCF4YdNX9AgMBAAECggEAOA3cNenMYrbGrB/R4K3ICcJlCiPDTww8NGUGPSVQw6MNsLLXopKBsNqtxrq57DBXeIqXNyk8/cb0CD6hw4XIg1AR3NRcDElq+2KIATDtoFrk40xyGRscyPGZV2BJyGWlbOrcve7fCBqeVs8JZkzHQyKbKnZYrvr33uENN0TX0DhoQ7ve+57DvrO1eVQd8nhm3j9210LpeXQdeuNMRA8k+SckpRaxn6Hrnobu9VvQsduwq9ylHy3+MWKvoZ8bJJGNlCgFoZsEkdkRXK53BY6gGACpxW6oEMPceP6w3o+q2OAIXjq9S7S45NcphFAgIapcfcMf5XZzez3gNtWhHo9hLQKBgQDlnbsXtsiimWIC5bkB8RCfpn5GIe8o2RCWkvrmGQ1+4nMEWagNn61Qbcl0DsAy87wH93LdQRKG/gmdp85Rp8tbMha6xSpsdrz2T2BSHtvDt/6yYNCnLVpV6XlYtqDTYs0wcwK2eulQNQT1Kljw8iXyxe1NyZYyCp8v1RIH3G1zKwKBgQDfNoUS8ZyR5iMGaQiKWFnNBa/M6g982rIb/3GG0XxWfmHvKY2xhsji9eOzjw+tBXws5resIyThTdzTqyFDb0jcRLTBGi2Eh5082qFw+piN5YtuaGOM553LqVsxAryUVjUEL94iHUvu9tzlRd+OSrZhAWcdtnfrqMbp6hxJQbpndwKBgCG8fAmu7GmfeZwNXpJfdd7PGox5Xec1hU9qRvzZv3beFKIV/jAKPu+rQccQh8K0tvLAs5q4fd1ho84LIrAypBlBEb/dSi9nfrn9zZwjNozaEszolp+JdVVcGV0kcBq+0IEG1OY0xuTm9VSEJU85dMPnuscMEEpH7z2a46xOAbLDAoGAJPe8lmSZj7hTuK4QkgkL5l64vPFO21jFAsoNnUx4kUKhKaDyLKKsBpq+uXBKIle0NyOG6+VuKDpjBlNjUO3PXVurrAeP3lEvg3KaJkH6nhWUQBbd1epOQfqsdbOEU/TrFuRun6vGda9A6l/mJITPK8W1tEASIF91giwZvzA5lQUCgYAkdlpHey/JxNmKrp0fg/GzAmoY9MLFXdxx+qDTL9J60QZc12pGA9Oxzik2dAlXw3SpqKocJUcbYQjzN/8+sGQeKFyCBKM4t1zfalaMPdVU6JJCLZLZ/AbL2aGFf3ElLqh3bZGD2+yY68gOirdyQsPz9PA2ZH5jIJN5k/TnAzJJTw==";
        String aLiPayKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAur79JmPv1KGARi1h5dTPzKztc7EKnERFCacxm/Rt9SqJqOr+i3iXwRfHr12cE6UxBQgzEo8lkED7OBri0KJvhQ2x/DAjHERxu2mHB1QalnZwFeAjFac8MiN1V+f67d4xiiYhKxoifAGw2/xDcHixFpncNFvZ6KooeimoYfpeC8m4uNqLrkZEAeLMVy20L4bx4CzBSoTFU4hQAQJosFxwU0S6IUivDCNeMis3WRsxzhNow7h80NhZdSGgyqn/PCqX06eRqtf+pn17ext2SheElR7si7rIyF0+nct4i7c+gS2OKYn+TfKBjehOijMP75CVgDnLlNRQIrxkug8e44W7RQIDAQAB";
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi-sandbox.dl.alipaydev.com/gateway.do", "2021000122674485", privatekey, "json", "GBK", aLiPayKey, "RSA2");
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();

        request.setNotifyUrl("");
        JSONObject bizContent = new JSONObject();
        //根据订单id查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order = orderService.getOne(wrapper);
        if (order == null) {
            throw new GuliException(20001, "订单不存在");
        }
        //请求参数
        bizContent.put("out_trade_no", order.getOrderNo());
        bizContent.put("total_amount", new BigDecimal(String.valueOf(order.getTotalFee())));
        bizContent.put("subject", order.getCourseTitle());
        bizContent.put("store_id", "2088721003576100");

        request.setBizContent(bizContent.toString());
        AlipayTradePrecreateResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        Map map = new HashMap();
        if (response.isSuccess()) {
            System.out.println("调用成功");
            String qrCode = response.getQrCode();
            String outTradeNo = response.getOutTradeNo();
            map.put("qrCode", qrCode);
            map.put("outTradeNo", outTradeNo);
            map.put("courseId", order.getCourseId());
            map.put("totalFee", order.getTotalFee());
            map.put("msg", response.getMsg());
            return map;
        }if(response.getCode().equals("40004")){
            throw new GuliException(20001, "已经支付过了");
        }
        else {
            System.out.println(response.getBody());
            throw new GuliException(20001, "生成二维码失败");
        }

    }

    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        String privatekey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDINUybDzm5sxbvaxRVqKT1aqwINv1uJHWYBWTqRV3d0NV1MBihbF3bs+qItLYxukO743qzrS9L6RawMQbEy+szB5B0Y2d9J0ONK6ye5SeD5I90Rzqh/wy9G10AdIp3ZAuWeSbk/39Liyyt6dHWpkxe14fASifH6MSwaxg9FNhSfhXc1TV2mR/eu4UveHkHFIdNL/kbVpE55onf9sz6i/f0ail3kKNZWekBS+6ZkUnK7uI5v+dMSnTqA2SI13Tikw3CIOhCEy0DEB6DFg7GGhaUm2k9EJ6b7+cn3V4WLd0JSPxtrpJIvUGmEndN2kX6G8u5JkNMsJU8GksXCF4YdNX9AgMBAAECggEAOA3cNenMYrbGrB/R4K3ICcJlCiPDTww8NGUGPSVQw6MNsLLXopKBsNqtxrq57DBXeIqXNyk8/cb0CD6hw4XIg1AR3NRcDElq+2KIATDtoFrk40xyGRscyPGZV2BJyGWlbOrcve7fCBqeVs8JZkzHQyKbKnZYrvr33uENN0TX0DhoQ7ve+57DvrO1eVQd8nhm3j9210LpeXQdeuNMRA8k+SckpRaxn6Hrnobu9VvQsduwq9ylHy3+MWKvoZ8bJJGNlCgFoZsEkdkRXK53BY6gGACpxW6oEMPceP6w3o+q2OAIXjq9S7S45NcphFAgIapcfcMf5XZzez3gNtWhHo9hLQKBgQDlnbsXtsiimWIC5bkB8RCfpn5GIe8o2RCWkvrmGQ1+4nMEWagNn61Qbcl0DsAy87wH93LdQRKG/gmdp85Rp8tbMha6xSpsdrz2T2BSHtvDt/6yYNCnLVpV6XlYtqDTYs0wcwK2eulQNQT1Kljw8iXyxe1NyZYyCp8v1RIH3G1zKwKBgQDfNoUS8ZyR5iMGaQiKWFnNBa/M6g982rIb/3GG0XxWfmHvKY2xhsji9eOzjw+tBXws5resIyThTdzTqyFDb0jcRLTBGi2Eh5082qFw+piN5YtuaGOM553LqVsxAryUVjUEL94iHUvu9tzlRd+OSrZhAWcdtnfrqMbp6hxJQbpndwKBgCG8fAmu7GmfeZwNXpJfdd7PGox5Xec1hU9qRvzZv3beFKIV/jAKPu+rQccQh8K0tvLAs5q4fd1ho84LIrAypBlBEb/dSi9nfrn9zZwjNozaEszolp+JdVVcGV0kcBq+0IEG1OY0xuTm9VSEJU85dMPnuscMEEpH7z2a46xOAbLDAoGAJPe8lmSZj7hTuK4QkgkL5l64vPFO21jFAsoNnUx4kUKhKaDyLKKsBpq+uXBKIle0NyOG6+VuKDpjBlNjUO3PXVurrAeP3lEvg3KaJkH6nhWUQBbd1epOQfqsdbOEU/TrFuRun6vGda9A6l/mJITPK8W1tEASIF91giwZvzA5lQUCgYAkdlpHey/JxNmKrp0fg/GzAmoY9MLFXdxx+qDTL9J60QZc12pGA9Oxzik2dAlXw3SpqKocJUcbYQjzN/8+sGQeKFyCBKM4t1zfalaMPdVU6JJCLZLZ/AbL2aGFf3ElLqh3bZGD2+yY68gOirdyQsPz9PA2ZH5jIJN5k/TnAzJJTw==";
        String aLiPayKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAur79JmPv1KGARi1h5dTPzKztc7EKnERFCacxm/Rt9SqJqOr+i3iXwRfHr12cE6UxBQgzEo8lkED7OBri0KJvhQ2x/DAjHERxu2mHB1QalnZwFeAjFac8MiN1V+f67d4xiiYhKxoifAGw2/xDcHixFpncNFvZ6KooeimoYfpeC8m4uNqLrkZEAeLMVy20L4bx4CzBSoTFU4hQAQJosFxwU0S6IUivDCNeMis3WRsxzhNow7h80NhZdSGgyqn/PCqX06eRqtf+pn17ext2SheElR7si7rIyF0+nct4i7c+gS2OKYn+TfKBjehOijMP75CVgDnLlNRQIrxkug8e44W7RQIDAQAB";
        AlipayClient alipayClient = new DefaultAlipayClient("https://openapi-sandbox.dl.alipaydev.com/gateway.do", "2021000122674485", privatekey, "json", "GBK", aLiPayKey, "RSA2");
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderNo);
//        request.setBizContent("{" +
//                "  \"out_trade_no\":" + orderNo + "," +
//                "  \"query_options\":[" +
//                "    \"trade_settle_info\"" +
//                "  ]" +
//                "}");
        request.setBizContent(bizContent.toString());
        AlipayTradeQueryResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
            System.out.println(response.getBody());
            //将返回信息转为map
            HashMap<String, String> map = new HashMap<>();
            map.put("out_trade_no", response.getOutTradeNo());
            map.put("trade_no", response.getTradeNo());
            map.put("trade_status", response.getTradeStatus());
            map.put("total_amount", response.getTotalAmount());
            //判断订单状态
            return  map;
        } else {
            System.out.println(response.getBody());
           throw new GuliException(20001,"调用失败");
        }
    }

    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //获取前一步生成二维码中的订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号，查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        Order order =orderService.getOne(wrapper);


        //判断订单状态是否为1，为1就是支付过了
        if (order.getStatus().intValue() == 1) {
            return;
        }

        //更新订单表中的叮当状态
        order.setStatus(1);//1代表已支付
        orderService.updateById(order);

        //向支付表里添加支付记录
        PayLog tPayLog = new PayLog();
        tPayLog.setOrderNo(orderNo);//支付订单号
        tPayLog.setPayTime(new Date());//支付时间
        tPayLog.setPayType(2);//支付类型
        tPayLog.setTotalFee(order.getTotalFee());//总金额(分)
        tPayLog.setTradeState(map.get("trade_status"));//支付状态
        tPayLog.setTransactionId(map.get("trade_no"));//订单流水号
        tPayLog.setAttr(JSONObject.toJSONString(map));
        save(tPayLog);//插入到支付表
    }
}

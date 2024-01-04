package com.sx.orderservice.service;

import com.sx.orderservice.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author sx
 * @since 2023-11-14
 */
public interface OrderService extends IService<Order> {

    String createOrder(String courseId, String id);
}

package com.henryma.flashsale.services;

import com.alibaba.fastjson.JSON;
import com.henryma.flashsale.db.dao.FlashsaleActivityDao;
import com.henryma.flashsale.db.dao.FlashsaleCommodityDao;
import com.henryma.flashsale.db.dao.OrderDao;
import com.henryma.flashsale.db.po.FlashsaleActivity;
import com.henryma.flashsale.db.po.FlashsaleCommodity;
import com.henryma.flashsale.db.po.Order;
import com.henryma.flashsale.mq.RocketMQService;
import com.henryma.flashsale.util.RedisService;
import com.henryma.flashsale.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class FlashsaleActivityService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private FlashsaleActivityDao flashsaleActivityDao;

    @Autowired
    private RocketMQService rocketMQService;

    @Autowired
    FlashsaleCommodityDao flashsaleCommodityDao;

    @Autowired
    OrderDao orderDao;

    private final SnowFlake snowFlake = new SnowFlake(1, 1);

    public Order createOrder(long flashsaleActivityId, long userId) throws Exception{
        FlashsaleActivity flashsaleActivity = flashsaleActivityDao.queryFlashsaleActivityById(flashsaleActivityId);
        Order order = new Order();
        order.setOrderNo(String.valueOf(snowFlake.nextId()));
        order.setFlashsaleActivityId(flashsaleActivity.getId());
        order.setUserId(userId);
        order.setOrderAmount(flashsaleActivity.getFlashsalePrice().longValue());
        rocketMQService.sendMessage("flashsale_order", JSON.toJSONString(order));

        rocketMQService.sendDelayMessage("pay_check", JSON.toJSONString(order), 3);
        return order;
    }

    public boolean flashsaleStockValidator(long activityId) {
        String key = "stock:" + activityId;
        return redisService.stockDeductValidator(key);
    }

    public void pushFlashsaleInfoToRedis(long flashsaleActivityId) {
		FlashsaleActivity flashsaleActivity = flashsaleActivityDao.queryFlashsaleActivityById(flashsaleActivityId);
		redisService.setValue("flashsaleActivity:" + flashsaleActivityId, JSON.toJSONString(flashsaleActivity));

		FlashsaleCommodity flashsaleCommodity = flashsaleCommodityDao.queryFlashsaleCommodityById(flashsaleActivity.getCommodityId());
		redisService.setValue("flashsaleCommodity:" + flashsaleActivity.getCommodityId(), JSON.toJSONString(flashsaleCommodity));
	}

    public void payOrderProcess(String orderNo) throws Exception{
        log.info("Payment Complete, Order Number: " + orderNo);
        Order order = orderDao.queryOrder(orderNo);
        if (order == null) {
            log.error("Order Corresponding to The Order Number Does Not Exist: " + orderNo);
            return;
        } else if (order.getOrderStatus() != 1) {
            log.error("Invalid Order Status: " + orderNo);
            return;
        }
        order.setPayTime(new Date());
        order.setOrderStatus(2);
        orderDao.updateOrder(order);
        rocketMQService.sendMessage("pay_done", JSON.toJSONString(order));
    }

}

package com.henryma.flashsale.mq;

import com.alibaba.fastjson.JSON;
import com.henryma.flashsale.db.dao.FlashsaleActivityDao;
import com.henryma.flashsale.db.dao.OrderDao;
import com.henryma.flashsale.db.po.Order;
import com.henryma.flashsale.util.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RocketMQMessageListener(topic="pay_check", consumerGroup = "pay_check_group")
public class PayStatusCheckListener implements RocketMQListener<MessageExt> {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private FlashsaleActivityDao flashsaleActivityDao;

    @Resource
    private RedisService redisService;

    @Override
    @Transactional
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("Received Message For Verify Order Payment Status: " + message);
        Order order = JSON.parseObject(message, Order.class);
        Order orderInfo = orderDao.queryOrder(order.getOrderNo());
        if (orderInfo.getOrderStatus() != 2) {
            log.info("Order Payment Due, Cancelling Order: " + orderInfo.getOrderNo());
            orderInfo.setOrderStatus(99);
            orderDao.updateOrder(orderInfo);
            flashsaleActivityDao.revertStock(order.getFlashsaleActivityId());
            redisService.revertStock("stock:" + order.getFlashsaleActivityId());
            redisService.removeLimitMember(order.getFlashsaleActivityId(), order.getUserId());
        }
    }
}

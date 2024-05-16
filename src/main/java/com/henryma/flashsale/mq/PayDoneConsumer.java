package com.henryma.flashsale.mq;

import com.alibaba.fastjson.JSON;
import com.henryma.flashsale.db.dao.FlashsaleActivityDao;
import com.henryma.flashsale.db.po.Order;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.StandardCharsets;

@Slf4j
@RocketMQMessageListener(topic = "pay_done", consumerGroup = "pay_done_group")
public class PayDoneConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private FlashsaleActivityDao flashsaleActivityDao;

    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("Received Request to Create Order: " + message);
        Order order = JSON.parseObject(message, Order.class);
        flashsaleActivityDao.deductStock(order.getFlashsaleActivityId());
    }
}

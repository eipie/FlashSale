package com.henryma.flashsale.component;

import com.henryma.flashsale.db.dao.FlashsaleActivityDao;
import com.henryma.flashsale.db.po.FlashsaleActivity;
import com.henryma.flashsale.util.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisPreheatRunner implements ApplicationRunner {

    @Autowired
    private RedisService redisService;

    @Autowired
    private FlashsaleActivityDao flashsaleActivityDao;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<FlashsaleActivity> flashsaleActivities = flashsaleActivityDao.queryFlashsaleActivitysByStatus(1);
        for (FlashsaleActivity flashsaleActivity : flashsaleActivities) {
            redisService.setValue("stock:" + flashsaleActivity.getId(), (long) flashsaleActivity.getAvailableStock());
        }
    }

}

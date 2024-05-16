package com.henryma.flashsale;

import com.henryma.flashsale.db.dao.FlashsaleActivityDao;
import com.henryma.flashsale.db.mappers.FlashsaleActivityMapper;
import com.henryma.flashsale.db.po.FlashsaleActivity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
public class DaoTest {
    @Resource
    private FlashsaleActivityMapper flashsaleActivityMapper;
    @Autowired
    private FlashsaleActivityDao flashsaleActivityDao;

    @Test
    void flashsaleActivityTest() {
        FlashsaleActivity flashsaleActivity = new FlashsaleActivity();
        flashsaleActivity.setName("测试");
        flashsaleActivity.setCommodityId(999L);
        flashsaleActivity.setTotalStock(100L);
        flashsaleActivity.setFlashsalePrice(new BigDecimal(99));
        flashsaleActivity.setActivityStatus(16);
        flashsaleActivity.setOldPrice(new BigDecimal(99));
        flashsaleActivity.setAvailableStock(100);
        flashsaleActivity.setLockStock(0L);
        flashsaleActivityMapper.insert(flashsaleActivity);
    }

    @Test
    void setflashsaleActivityQuery() {
        List<FlashsaleActivity> flashsaleActivitys = flashsaleActivityDao.queryFlashsaleActivitysByStatus(0);
        System.out.println(flashsaleActivitys.size());
        flashsaleActivitys.forEach(flashsaleActivity -> System.out.println(flashsaleActivity.toString()));
    }
}
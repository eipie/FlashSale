package com.henryma.flashsale;

import com.henryma.flashsale.services.FlashsaleActivityService;
import com.henryma.flashsale.util.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.UUID;

@SpringBootTest
public class RedisDemoTest {

    @Resource
    private RedisService redisService;

    @Autowired
    FlashsaleActivityService flashsaleActivityService;

    @Test
    public void stockTest() {
        redisService.setValue("stock:19", 10L);
    }

    @Test
    public void getStockTest() {
        String stock = redisService.getValue("stock:19");
        System.out.println(stock);
    }

    @Test
    public void stockDeductValidatorTest() {
        boolean result = redisService.stockDeductValidator("stock:19");
        System.out.println("Result: " + result);
        String stock = redisService.getValue("stock:19");
        System.out.println("Available Stock: " + stock);
    }

    @Test
    public void pushFlashsaleInfoToRedisTest() {
        flashsaleActivityService.pushFlashsaleInfoToRedis(19);
    }

    @Test
    public void getFlashsaleInfoFromRedis() {
        String flashsaleInfo = redisService.getValue("flashsaleActivity:" + 19);
        System.out.println(flashsaleInfo);
        String flashsaleCommodity = redisService.getValue("flashsaleCommodity:" + 1001);
        System.out.println(flashsaleCommodity);
    }

    @Test
    public void testConcurrentAddLock() {
        for (int i = 0; i < 10; i++) {
            String requestId = UUID.randomUUID().toString();
            String key = "A";
            System.out.println(redisService.tryGetDistributedLock(key, requestId, 1000));
            redisService.releaseDistributedLock(key, requestId);
        }
    }
}

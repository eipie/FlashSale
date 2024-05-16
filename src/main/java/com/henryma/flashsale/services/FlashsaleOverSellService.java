package com.henryma.flashsale.services;

import com.henryma.flashsale.db.dao.FlashsaleActivityDao;
import com.henryma.flashsale.db.po.FlashsaleActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashsaleOverSellService {

    @Autowired
    private FlashsaleActivityDao flashsaleActivityDao;

    public String processFlashsale(long activityId) {
        FlashsaleActivity flashsaleActivity = flashsaleActivityDao.queryFlashsaleActivityById(activityId);
        long availableStock = flashsaleActivity.getAvailableStock();

        String result;
        if (availableStock > 0) {
            result = "Congratulation, Flashsale Success";
            System.out.println(result);
            availableStock = availableStock - 1;
            flashsaleActivity.setAvailableStock(new Integer("" + availableStock));
            flashsaleActivityDao.updateFlashsaleActivity(flashsaleActivity);
        } else {
            result = "Sorry, The item is not available for flashsale now.";
            System.out.println(result);
        }
        return result;
    }
}

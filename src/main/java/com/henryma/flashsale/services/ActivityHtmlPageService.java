package com.henryma.flashsale.services;

import com.henryma.flashsale.db.dao.FlashsaleActivityDao;
import com.henryma.flashsale.db.dao.FlashsaleCommodityDao;
import com.henryma.flashsale.db.po.FlashsaleActivity;
import com.henryma.flashsale.db.po.FlashsaleCommodity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ActivityHtmlPageService {

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private FlashsaleActivityDao flashsaleActivityDao;

    @Autowired
    private FlashsaleCommodityDao flashsaleCommodityDao;

    public void createActivityHtml(long flashsaleActivityId) {
        PrintWriter writer = null;
        try {
            FlashsaleActivity flashsaleActivity = flashsaleActivityDao.queryFlashsaleActivityById(flashsaleActivityId);
            FlashsaleCommodity flashsaleCommodity = flashsaleCommodityDao.queryFlashsaleCommodityById(flashsaleActivity.getCommodityId());
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("flashsaleActivity", flashsaleActivity);
            resultMap.put("flashsaleCommodity", flashsaleCommodity);
            resultMap.put("flashsalePrice", flashsaleActivity.getFlashsalePrice());
            resultMap.put("oldPrice", flashsaleActivity.getOldPrice());
            resultMap.put("commodityId", flashsaleActivity.getCommodityId());
            resultMap.put("commodityName", flashsaleCommodity.getCommodityName());
            resultMap.put("commodityDesc", flashsaleCommodity.getCommodityDesc());

            Context context = new Context();
            context.setVariables(resultMap);
            File file = new File("src/main/resources/templates" + "flashsale_item_" + flashsaleActivityId + ".html");
            writer = new PrintWriter(file);
            templateEngine.process("flashsale_item", context, writer);
        } catch (Exception e) {
            log.error(e.toString());
            log.error("Static Webpage Error: " + flashsaleActivityId);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}

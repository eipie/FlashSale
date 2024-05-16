package com.henryma.flashsale.web;

import com.henryma.flashsale.services.FlashsaleActivityService;
import com.henryma.flashsale.services.FlashsaleOverSellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FlashsaleOverSellController {

    @Autowired
    private FlashsaleOverSellService flashsaleOverSellService;

    @Autowired
    private FlashsaleActivityService flashsaleActivityService;

    @ResponseBody
    @RequestMapping("/flashsale/{flashsaleActivityId}")
    public String flashsaleCommodity(@PathVariable long flashsaleActivityId) {
        boolean stockValidateResult = flashsaleActivityService.flashsaleStockValidator(flashsaleActivityId);
        return stockValidateResult ? "Congratulations, Flashsale Success" : "Item is Out of Stock, Maybe Next Time?";
    }

//    @ResponseBody
//    @RequestMapping("/flashsale/{flashsaleActivityId}")
    public String flashsale(@PathVariable long flashsaleActivityId) {
        return flashsaleOverSellService.processFlashsale(flashsaleActivityId);
    }



}

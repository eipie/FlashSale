package com.henryma.flashsale.db.dao;

import com.henryma.flashsale.db.mappers.FlashsaleCommodityMapper;
import com.henryma.flashsale.db.po.FlashsaleCommodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class FlashsaleCommodityDaoImpl implements FlashsaleCommodityDao {
    @Resource
    private FlashsaleCommodityMapper flashsaleCommodityMapper;

    @Override
    public FlashsaleCommodity queryFlashsaleCommodityById(long commodityId) {
        return flashsaleCommodityMapper.selectByPrimaryKey(commodityId);
    }
}

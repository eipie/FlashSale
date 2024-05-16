package com.henryma.flashsale.db.mappers;

import com.henryma.flashsale.db.po.FlashsaleActivity;

import java.util.List;

public interface FlashsaleActivityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlashsaleActivity record);

    int insertSelective(FlashsaleActivity record);

    FlashsaleActivity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlashsaleActivity record);

    int updateByPrimaryKey(FlashsaleActivity record);

    List<FlashsaleActivity> queryFlashsaleActivitysByStatus(int status);

    int lockStock(long id);

    int deductStock(long id);

    void revertStock(long flashsaleActivityId);
}
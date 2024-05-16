package com.henryma.flashsale.db.mappers;

import com.henryma.flashsale.db.po.FlashsaleCommodity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FlashsaleCommodityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(FlashsaleCommodity record);

    int insertSelective(FlashsaleCommodity record);

    FlashsaleCommodity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FlashsaleCommodity record);

    int updateByPrimaryKey(FlashsaleCommodity record);
}
package com.heroland.competition.dal.mapper;

import com.alicp.jetcache.anno.Cached;
import com.heroland.competition.dal.pojo.HerolandQuestionBankDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandQuestionBankDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandQuestionBankDetail record);

    int insertSelective(HerolandQuestionBankDetail record);

    HerolandQuestionBankDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandQuestionBankDetail record);

    int updateByPrimaryKey(HerolandQuestionBankDetail record);

    int deleteByQtId( @Param("qtIds") List<Long> qtIds);

    List<HerolandQuestionBankDetail> getByQtId( @Param("qtIds") List<Long> qtIds);

    @Cached(name="HerolandQuestionBankDetailMapper.getById", expire = 3600)
    List<HerolandQuestionBankDetail> getById( @Param("ids") List<Long> ids);
}
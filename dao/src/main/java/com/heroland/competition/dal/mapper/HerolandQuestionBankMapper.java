package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandQuestionBank;
import com.heroland.competition.domain.dp.HerolandQuestionUniqDP;
import com.heroland.competition.domain.dp.QuestionTopicDP;
import com.heroland.competition.domain.qo.HeroLandTopicGroupQO;
import com.heroland.competition.domain.qo.HerolandQuestionBankQo;
import com.heroland.competition.domain.qo.HerolandQuestionSelectQo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandQuestionBankMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HerolandQuestionBank record);

    int insertSelective(HerolandQuestionBank record);

    HerolandQuestionBank selectByPrimaryKey(Long id);

    HerolandQuestionBank selectByQtId(@Param("qtId") String qtId);

    int updateByPrimaryKeySelective(HerolandQuestionBank record);

    int updateByPrimaryKeyWithBLOBs(HerolandQuestionBank record);

    int updateByPrimaryKey(HerolandQuestionBank record);

    int deleteByQtIds(@Param("qtIds") List<String> qtIds);

    List<HerolandQuestionBank> getByIds(@Param("ids") List<Long> ids);

    List<HerolandQuestionBank> getByIdsWithDelete(@Param("ids") List<Long> ids);

    List<HerolandQuestionBank> getByQuery(@Param("item") HerolandQuestionBankQo qo);

    List<HerolandQuestionBank> getByQueryV2(@Param("item") HerolandQuestionBankQo qo);

    List<HerolandQuestionBank> questionSelect(@Param("item") HerolandQuestionSelectQo qo);

    List<HerolandQuestionUniqDP> selectSimpleSnaphot(@Param("qtIds") List<String> qtIds);

    List<QuestionTopicDP> selectQuestionTopicByQuestion(@Param("item") HeroLandTopicGroupQO qo);

    List<QuestionTopicDP> selectQuestionTopicByTopic(@Param("item") HeroLandTopicGroupQO qo);

    List<HerolandQuestionBank> selectQuestionsByGradeAndCoursesForS(@Param("grade") String grade,@Param("course") String course, @Param("bankType") Integer bankType,@Param("lastId") Long lastId, @Param("count") Integer count );

    Long maxId(@Param("grade") String grade,@Param("course") String course, @Param("bankType") Integer bankType );
}
package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.HerolandSchool;
import com.heroland.competition.dal.pojo.basic.HerolandSchoolSimple;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HerolandSchoolMapper {

    int deleteByPrimaryKey(Long id);

    int batchDeleteByIds(@Param("ids") List<Long> ids);

//    int insert(HerolandSchool record);

    int insertSelective(HerolandSchool record);

    HerolandSchool selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HerolandSchool record);



    HerolandSchool getByKey(@Param("key") String key);

    List<HerolandSchool> getByKeyAndCode(@Param("key") String key, @Param("code") String code);

    List<HerolandSchool> getByParentAndName(@Param("parentKey") String parentKey, @Param("name") String name);

    List<HerolandSchool> getByCodeAndName(@Param("code") String code, @Param("name") String name);

    List<HerolandSchool> getByParent(@Param("parent") String key);

    List<HerolandSchool> getByParents(@Param("parents") List<String> keys);


    List<HerolandSchool> getByParentAndKey(@Param("parentKey") String parentKey, @Param("key") String key);
}
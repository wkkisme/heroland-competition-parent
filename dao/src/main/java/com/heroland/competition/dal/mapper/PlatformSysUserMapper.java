package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.PlatformSysUser;
import com.heroland.competition.dal.pojo.PlatformSysUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatformSysUserMapper {
    long countByExample(PlatformSysUserExample example);

    int deleteByExample(PlatformSysUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlatformSysUser record);

    int insertSelective(PlatformSysUser record);

    List<PlatformSysUser> selectByExample(PlatformSysUserExample example);

    PlatformSysUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlatformSysUser record, @Param("example") PlatformSysUserExample example);

    int updateByExample(@Param("record") PlatformSysUser record, @Param("example") PlatformSysUserExample example);

    int updateByPrimaryKeySelective(PlatformSysUser record);

    int updateByPrimaryKey(PlatformSysUser record);
}
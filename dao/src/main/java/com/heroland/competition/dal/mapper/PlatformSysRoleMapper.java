package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.PlatformSysRole;
import com.heroland.competition.dal.pojo.PlatformSysRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatformSysRoleMapper {
    long countByExample(PlatformSysRoleExample example);

    int deleteByExample(PlatformSysRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlatformSysRole record);

    int insertSelective(PlatformSysRole record);

    List<PlatformSysRole> selectByExample(PlatformSysRoleExample example);

    PlatformSysRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlatformSysRole record, @Param("example") PlatformSysRoleExample example);

    int updateByExample(@Param("record") PlatformSysRole record, @Param("example") PlatformSysRoleExample example);

    int updateByPrimaryKeySelective(PlatformSysRole record);

    int updateByPrimaryKey(PlatformSysRole record);
}
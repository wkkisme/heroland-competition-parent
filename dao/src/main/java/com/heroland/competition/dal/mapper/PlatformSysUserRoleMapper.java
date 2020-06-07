package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.PlatformSysUserRole;
import com.heroland.competition.dal.pojo.PlatformSysUserRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatformSysUserRoleMapper {
    long countByExample(PlatformSysUserRoleExample example);

    int deleteByExample(PlatformSysUserRoleExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlatformSysUserRole record);

    int insertSelective(PlatformSysUserRole record);

    List<PlatformSysUserRole> selectByExample(PlatformSysUserRoleExample example);

    PlatformSysUserRole selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlatformSysUserRole record, @Param("example") PlatformSysUserRoleExample example);

    int updateByExample(@Param("record") PlatformSysUserRole record, @Param("example") PlatformSysUserRoleExample example);

    int updateByPrimaryKeySelective(PlatformSysUserRole record);

    int updateByPrimaryKey(PlatformSysUserRole record);
}
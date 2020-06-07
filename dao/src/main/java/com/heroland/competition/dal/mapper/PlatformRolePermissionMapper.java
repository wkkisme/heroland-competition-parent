package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.PlatformSysRolePermission;
import com.heroland.competition.dal.pojo.PlatformSysRolePermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatformRolePermissionMapper {
    long countByExample(PlatformSysRolePermissionExample example);

    int deleteByExample(PlatformSysRolePermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlatformSysRolePermission record);

    int insertSelective(PlatformSysRolePermission record);

    List<PlatformSysRolePermission> selectByExample(PlatformSysRolePermissionExample example);

    PlatformSysRolePermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlatformSysRolePermission record, @Param("example") PlatformSysRolePermissionExample example);

    int updateByExample(@Param("record") PlatformSysRolePermission record, @Param("example") PlatformSysRolePermissionExample example);

    int updateByPrimaryKeySelective(PlatformSysRolePermission record);

    int updateByPrimaryKey(PlatformSysRolePermission record);
}
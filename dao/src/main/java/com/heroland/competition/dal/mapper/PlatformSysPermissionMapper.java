package com.heroland.competition.dal.mapper;

import com.heroland.competition.dal.pojo.PlatformSysPermission;
import com.heroland.competition.dal.pojo.PlatformSysPermissionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlatformSysPermissionMapper {
    long countByExample(PlatformSysPermissionExample example);

    int deleteByExample(PlatformSysPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlatformSysPermission record);

    int insertSelective(PlatformSysPermission record);

    List<PlatformSysPermission> selectByExample(PlatformSysPermissionExample example);

    PlatformSysPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlatformSysPermission record, @Param("example") PlatformSysPermissionExample example);

    int updateByExample(@Param("record") PlatformSysPermission record, @Param("example") PlatformSysPermissionExample example);

    int updateByPrimaryKeySelective(PlatformSysPermission record);

    int updateByPrimaryKey(PlatformSysPermission record);
}
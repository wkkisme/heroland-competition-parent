package com.heroland.competition.service.admin;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandLocationDP;
import com.heroland.competition.domain.qo.HerolandBasicDataQO;
import com.heroland.competition.domain.qo.HerolandLocationDataQO;

import java.util.List;


/**
 */
public interface HeroLandAdminService {
    /**
     * 增加后台管理类别
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> addDict(HerolandBasicDataDP dp);

    /**
     * 编辑后台管理类别
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> editDict(HerolandBasicDataDP dp);

    /**
     * 删除后台管理字典值
     * 原则上不支持删除，一旦删除，相应的业务数据都会受到影响，不可见
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> deleteDict(HerolandBasicDataDP dp);

    /**
     * 获取某一个subject
     * @param qo 对象
     * @return 正确
     */
    ResponseBody<HerolandBasicDataDP> getDictInfoById(HerolandBasicDataQO qo);


    /**
     * 根据字典key获取数据值信息
     * @param keys
     * @return
     */
    ResponseBody<List<HerolandBasicDataDP>> getDictInfoByKeys(List<String> keys);

    /**
     * 获取所有的管理类别
     * 无任何层级结构
     * 分页获取
     * @return 正确
     */
    ResponseBody<List<HerolandBasicDataDP>> pageQueryDict(HerolandBasicDataQO qo);

    /**
     * 获取学校管理的相关内容
     * 层级关系
     * @param qo
     * @return
     */
    ResponseBody<List<HerolandLocationDP>> listQueryLocale(HerolandLocationDataQO qo);


    ResponseBody<List<HerolandBasicDataDP>> listValidLoation(String code);

    /**
     * 获取学校管理的相关内容
     * 层级关系
     * @param dp
     * @return
     */
    ResponseBody<Boolean> addClass(HerolandLocationDP dp);




}

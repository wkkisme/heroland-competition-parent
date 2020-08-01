package com.heroland.competition.service.admin;

import com.anycommon.response.common.ResponseBody;
import com.heroland.competition.common.pageable.PageResponse;
import com.heroland.competition.dal.pojo.basic.HerolandBasicData;
import com.heroland.competition.domain.dp.HerolandBasicDataDP;
import com.heroland.competition.domain.dp.HerolandLocationDP;
import com.heroland.competition.domain.qo.HerolandBasicDataQO;
import com.heroland.competition.domain.qo.HerolandLocationDataQO;
import com.heroland.competition.domain.request.HerolandBasicDataPageRequest;
import com.heroland.competition.domain.request.HerolandDataPageRequest;

import java.util.List;


/**
 */
public interface HeroLandAdminService {
    /**
     * 增加后台管理类别
     * @param dp 对象
     * @return 正确
     */
    HerolandBasicData addDict(HerolandBasicDataDP dp);

    /**
     * 编辑后台管理类别
     * @param dp 对象
     * @return 正确
     */
    ResponseBody<Boolean> editDict(HerolandBasicDataDP dp);

    /**
     * 删除后台管理字典值
     *
     * @param keys
     * @return 正确
     */
   Boolean deleteDict(List<String> keys);

    /**
     * 获取某一个subject
     * @param qo 对象
     * @return 正确
     */
    ResponseBody<HerolandBasicDataDP> getDictInfoById(Long id);


    /**
     * 根据字典key获取数据值信息
     * @param keys
     * @return
     */
    List<HerolandBasicDataDP> getDictInfoByKeys(List<String> keys);

    /**
     * 获取所有的管理类别
     * 无任何层级结构
     * 分页获取
     * @return 正确
     */
    PageResponse<HerolandBasicDataDP> pageQueryDict(HerolandBasicDataPageRequest request);

    /**
     * 获取学校管理的相关内容
     * 层级关系
     * @param qo
     * @return
     */
    ResponseBody<List<HerolandLocationDP>> listQueryLocale(HerolandLocationDataQO qo);


    ResponseBody<List<HerolandBasicDataDP>> listValidLoation(String code);


    PageResponse<HerolandBasicDataDP> pageDataByCode(HerolandDataPageRequest request);

    /**
     * 获取学校管理的相关内容
     * 层级关系
     * @param dp
     * @return
     */
    ResponseBody<Boolean> addClass(HerolandLocationDP dp);




}

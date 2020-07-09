package com.heroland.competition.service.diamond.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandSkuMapper;
import com.heroland.competition.dal.pojo.HerolandSku;
import com.heroland.competition.domain.dp.HerolandOrderDP;
import com.heroland.competition.domain.dp.HerolandSkuDP;
import com.heroland.competition.domain.qo.HerolandSkuQO;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author smjyouzan
 * @date 2020/7/9
 */
@Component
@Slf4j
public class HerolandDiamondServiceImpl implements HerolandDiamondService {

    @Resource
    private HerolandSkuMapper skuMapper;

    @Override
    public void createDiamondSku(HerolandSkuDP herolandSkuDP) {
        HerolandOrderDP orderDP = null;
        try {
            HerolandSku sku = BeanUtil.insertConversion(herolandSkuDP.checkAndBuildBeforeCreate(), new HerolandSku());
            skuMapper.insert(sku);
        } catch (Exception e) {
            log.error("createOrder error, [{}]", JSON.toJSONString(herolandSkuDP));
            ResponseBodyWrapper.failSysException();
        }
    }

    @Override
    public void updateDiamondSku(HerolandSkuDP dp) {
        if (NumberUtils.nullOrZeroLong(dp.getId())){
            ResponseBodyWrapper.failSysException();
        }
        try {
            skuMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp, new HerolandSku()));
        } catch (Exception e) {
            log.error("editSubject error", e);
            ResponseBodyWrapper.failSysException();
        }
    }

    @Override
    public HerolandSkuDP getById(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failSysException();
        }
        HerolandSku sku = skuMapper.selectByPrimaryKey(id);
        try {
            HerolandSkuDP conversion = BeanUtil.conversion(sku, new HerolandSkuDP());
            return conversion;
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public int deleteById(Long id) {
        if (NumberUtils.nullOrZeroLong(id)){
            ResponseBodyWrapper.failSysException();
        }
        return skuMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<HerolandSkuDP> listSku(HerolandSkuQO skuQO) {
        List<HerolandSku> list = skuMapper.list(skuQO);
        try {
            List<HerolandSkuDP> herolandSkuDPS = BeanUtil.mappingListConversion(list, HerolandSkuDP.class);
            return null;
        } catch (Exception e) {


        }
        return new ArrayList<>();
    }
}

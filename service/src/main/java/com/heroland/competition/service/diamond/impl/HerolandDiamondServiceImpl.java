package com.heroland.competition.service.diamond.impl;

import com.alibaba.fastjson.JSON;
import com.anycommon.response.common.ResponseBody;
import com.anycommon.response.utils.BeanUtil;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.heroland.competition.common.constants.DiamBizGroupEnum;
import com.heroland.competition.common.constants.StockEnum;
import com.heroland.competition.common.utils.AssertUtils;
import com.heroland.competition.common.utils.BeanCopyUtils;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.NumberUtils;
import com.heroland.competition.dal.mapper.HerolandDiamondStockLogMapper;
import com.heroland.competition.dal.mapper.HerolandSkuMapper;
import com.heroland.competition.dal.pojo.HerolandDiamondStockLog;
import com.heroland.competition.dal.pojo.HerolandSku;
import com.heroland.competition.domain.dp.HeroLandAccountDP;
import com.heroland.competition.domain.dp.HerolandDiamondStockLogDP;
import com.heroland.competition.domain.dp.HerolandSkuDP;
import com.heroland.competition.domain.dto.HerolandDiamMonthRecordDto;
import com.heroland.competition.domain.dto.HerolandDiamondStockDto;
import com.heroland.competition.domain.qo.HeroLandAccountManageQO;
import com.heroland.competition.domain.qo.HeroLandAccountQO;
import com.heroland.competition.domain.qo.HerolandDiamRecordQO;
import com.heroland.competition.domain.qo.HerolandSkuQO;
import com.heroland.competition.domain.request.HerolandDiamMonthRecordRequest;
import com.heroland.competition.domain.request.HerolandDiamRequest;
import com.heroland.competition.service.HeroLandAccountService;
import com.heroland.competition.service.diamond.HerolandDiamondService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @date 2020/7/9
 */
@Component
@Slf4j
public class HerolandDiamondServiceImpl implements HerolandDiamondService {

    @Resource
    private HerolandSkuMapper skuMapper;

    @Resource
    private HerolandDiamondStockLogMapper herolandDiamondStockLogMapper;

    @Resource
    private HeroLandAccountService heroLandAccountService;

    @Override
    public void createDiamondSku(HerolandSkuDP herolandSkuDP) {
        try {
            HerolandSku sku = BeanUtil.insertConversion(herolandSkuDP.checkAndBuildBeforeCreate(), new HerolandSku());
            skuMapper.insertSelective(sku);
        } catch (Exception e) {
            log.error("createOrder error, [{}]", JSON.toJSONString(herolandSkuDP));
            ResponseBodyWrapper.failSysException();
        }
    }

    @Override
    public void updateDiamondSku(HerolandSkuDP dp) {
        try {
            skuMapper.updateByPrimaryKeySelective(BeanUtil.updateConversion(dp.checkAndBuildBeforeEdit(), new HerolandSku()));
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
        HerolandSku sku = skuMapper.selectByPrimaryKeyExcludeDel(id);
        if (sku == null){
            return null;
        }
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
        List<HerolandSku> list = skuMapper.list(skuQO.getSpuId(),skuQO.getSkuId());
        try {
            List<HerolandSkuDP> herolandSkuDPS = BeanUtil.mappingListConversion(list, HerolandSkuDP.class);
            return herolandSkuDPS;
        } catch (Exception e) {

        }
        return new ArrayList<>();
    }

    @Override
    @Transactional
    public Boolean createDiamondRecord(HerolandDiamRequest request) {
        HerolandDiamondStockLogDP dp = new HerolandDiamondStockLogDP();
        dp.setBizGroup(request.getBizGroup());
        dp.setBizType(request.getBizName());
        dp.setNum(request.getNum());
        dp.setType(request.getChangeStockType());
        dp.setUserId(request.getUserId());
        dp = dp.checkAndBuildBeforeCreate();
        HerolandDiamondStockLog stockLog = BeanCopyUtils.copyByJSON(dp, HerolandDiamondStockLog.class);
        if (Objects.equals(request.getChangeStockType(), StockEnum.INCREASE.getLevel())){
            HeroLandAccountManageQO heroLandAccountManageQO = new HeroLandAccountManageQO();
            heroLandAccountManageQO.setUserId(request.getUserId());
            heroLandAccountManageQO.setNum(request.getNum());
            if (!Objects.equals(request.getBizGroup(), DiamBizGroupEnum.BUY.getGroup())){
                heroLandAccountManageQO.setCompetitionType(request.getCompetitionEnum());
            }
            heroLandAccountService.incrUserDiamond(heroLandAccountManageQO);
        }
        return herolandDiamondStockLogMapper.insertSelective(stockLog) > 0;
    }

    @Override
    public HerolandDiamondStockDto recordList(HerolandDiamMonthRecordRequest request) {
        HerolandDiamondStockDto stockDto = new HerolandDiamondStockDto();
        stockDto.setUserId(request.getUserId());
        HeroLandAccountQO qo = new HeroLandAccountQO();
        qo.setUserId(request.getUserId());
        ResponseBody<List<HeroLandAccountDP>> account = heroLandAccountService.getAccount(qo);
        AssertUtils.assertThat(!CollectionUtils.isEmpty(account.getData()), "用户账户不存在");
        stockDto.setStockNum(NumberUtils.nullOrZeroLong(account.getData().get(0).getBalance()) ? 0 : account.getData().get(0).getBalance().intValue());
        HerolandDiamRecordQO recordQO = new HerolandDiamRecordQO();
        recordQO.setType(request.getChangeStockType());
        recordQO.setBizType(request.getBizName());
        recordQO.setBizGroup(request.getBizGroup());
        recordQO.setUserId(request.getUserId());
        if (StringUtils.isBlank(request.getYear())){
            String nowYear = DateUtils.dateToYear(new Date());
            request.setYear(nowYear);

        }
        Date beginTime = DateUtils.string2Date(request.getYear() + "-01-01 00:00:00", DateUtils.PATTERN_STANDARD);
        Date endTime = DateUtils.string2Date(request.getYear() + "-12-31 23:59:59", DateUtils.PATTERN_STANDARD);
        recordQO.setBeginTime(beginTime);
        recordQO.setEndTime(endTime);
//        List<HerolandDiamRecordDto> result = new ArrayList<>();
        Map<Integer,List<HerolandDiamMonthRecordDto>> monthRecordMap = Maps.newHashMap();
        List<HerolandDiamondStockLog> stockLogs = herolandDiamondStockLogMapper.getByQuery(recordQO);
        Map<Integer, List<HerolandDiamondStockLog>> monthMap = Maps.newHashMap();
        stockLogs.stream().forEach(e -> {

            Calendar instance = Calendar.getInstance();
            instance.setTime(e.getGmtCreate());
            int month = instance.get(Calendar.MONTH);
            if (monthMap.containsKey(month)){
                monthMap.get(month).add(e);
            }else {
                List<HerolandDiamondStockLog> monthList = Lists.newArrayList();
                monthList.add(e);
                monthMap.put(month,monthList);
            }
        });
        for (Map.Entry<Integer, List<HerolandDiamondStockLog>> entry : monthMap.entrySet()){
            List<HerolandDiamMonthRecordDto> list = new ArrayList<>();
            Map<String, List<HerolandDiamondStockLog>> bizMap = Maps.newHashMap();
            entry.getValue().stream().forEach(e -> {
                String biz = e.getBizGroup()+ "_" +e.getBizType();
                if (bizMap.containsKey(biz)){
                    bizMap.get(biz).add(e);
                }else {
                    List<HerolandDiamondStockLog> bizList = Lists.newArrayList();
                    bizList.add(e);
                    bizMap.put(biz,bizList);
                }
            });
            for (Map.Entry<String, List<HerolandDiamondStockLog>> subEntry : bizMap.entrySet()){
                HerolandDiamMonthRecordDto recordDto = new HerolandDiamMonthRecordDto();
                recordDto.setBizGroup(subEntry.getValue().get(0).getBizGroup());
                recordDto.setBizName(subEntry.getValue().get(0).getBizType());
                recordDto.setUserId(subEntry.getValue().get(0).getUserId());
                recordDto.setChangeStockType(subEntry.getValue().get(0).getType());
                recordDto.setTotal(subEntry.getValue().stream().mapToInt(HerolandDiamondStockLog::getNum).sum());
                list.add(recordDto);
            }
            monthRecordMap.put(entry.getKey(), list);
        }
        stockDto.setMonthRecordMap(monthRecordMap);
        return stockDto;
    }
}

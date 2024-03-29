package com.heroland.competition.common.utils;


import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wushuaiping
 * @date 2020/7/25 11:51
 */
@Configuration
public class HeroLevelUtils {

    @Value("${hero.level.adversityHero}")
    private Integer adversityHero;

    @Value("${hero.level.courageousHero}")
    private Integer courageousHero;

    @Value("${hero.level.supremeHero}")
    private Integer supremeHero;

    /**
     * 两个分数等级比较
     * inLevelScore相对于beLevelScore高多少等级或低多少等级
     *
     * @param inLevelScore
     * @param beLevelScore
     * @return 1：高一个等级， 2：高两个等级， 0：同等级，-1：低一个等级，-2：低两个等级
     */
    public Integer compare(Integer inLevelScore, Integer beLevelScore) {
        return level(inLevelScore) - level(beLevelScore);
    }

    /**
     * 获取当前分数等级
     *
     * @param levelScore
     * @return
     */
    public Integer level(Integer levelScore) {
        return isAdversityHero(levelScore) ? 1 :
                isCourageousHero(levelScore) ? 2 :
                        isSupremeHero(levelScore) ? 3 : 1;
    }

    /**
     * 获取等级code
     *
     * @param levelScore
     * @return
     */
    public String getLevelCode(Integer levelScore) {
        return level(levelScore).equals(1) ? "ADVERSITY_HERO" : level(levelScore).equals(2) ? "COURAGEOUS_HERO" : "SUPREME_HERO";
    }

    /**
     * 比较tlevelCode比slevelCode高多少等级或低多少等级
     * @param tlevelCode
     * @param slevelCode
     * @return 1：高一个等级， 2：高两个等级， 0：同等级，-1：低一个等级，-2：低两个等级
     */
    public Integer compare(String tlevelCode, String slevelCode){
        int tTevel = StrUtil.equals(tlevelCode, "ADVERSITY_HERO") ? 1 : StrUtil.equals(tlevelCode, "COURAGEOUS_HERO") ? 2 : 3;
        int sTevel = StrUtil.equals(slevelCode, "ADVERSITY_HERO") ? 1 : StrUtil.equals(tlevelCode, "COURAGEOUS_HERO") ? 2 : 3;
        return tTevel - sTevel;
    }

    /**
     * 是否为逆境英雄
     *
     * @param levelScore
     * @return
     */
    public boolean isAdversityHero(Integer levelScore) {
        return levelScore < courageousHero;
    }

    /**
     * 是否为奋勇英雄
     *
     * @param levelScore
     * @return
     */
    public boolean isCourageousHero(Integer levelScore) {
        return levelScore >= courageousHero && courageousHero < supremeHero;
    }

    /**
     * 是否为至尊英雄
     *
     * @param levelScore
     * @return
     */
    public boolean isSupremeHero(Integer levelScore) {
        return levelScore >= supremeHero;
    }
}

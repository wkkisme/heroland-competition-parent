package com.heroland.competition.task;

import com.google.common.collect.Maps;
import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计
 *
 * @author wangk
 */
//@Component
public class StatisticsTask {
    @Resource
    private HeroLandCompetitionRecordService heroLandCompetitionRecordService;

    @Resource
    private HeroLandCompetitionStatisticsService heroLandCompetitionStatisticsService;

    /**
     * 统计所有人的比赛记录
     * 1 总表统计所有人的总记录，
     * 1 总得分
     * 2 总平均分
     * 3 完成率
     * 4 答对率
     * 5 胜率
     * 6 总时长
     * 2 详细表根据科目来统计所有人的记录
     * 1 总得分
     * *      2 总平均分
     * *      3 完成率
     * *      4 答对率
     * *      5 胜率
     * *      6 总时长
     * 7 总场数
     * 0 15 10 * * ? *
     */
    @Scheduled(cron = "0 0 0 ? * *")
    public void statistics() {

        // 1 先清除历史版本
        HeroLandStatisticsTotalQO heroLandStatisticsTotalQO = new HeroLandStatisticsTotalQO();
        heroLandStatisticsTotalQO.setHistory(false);
        heroLandCompetitionStatisticsService.updateHistoryStatisticsTotalAndDetailByQO(heroLandStatisticsTotalQO);

        /*
         * 同步作业赛总分数 、总平均分、 总场次 total------>
         */


        List<HeroLandStatisticsTotalDP> totalSyncTotalScore = heroLandCompetitionRecordService.getTotalScore(heroLandStatisticsTotalQO);

        Map<String, HeroLandStatisticsTotalDP> mergeMap = Maps.newHashMapWithExpectedSize(totalSyncTotalScore.size());
        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : totalSyncTotalScore) {
            mergeMap.put(heroLandStatisticsTotalDP.getOrgCode()+heroLandStatisticsTotalDP.getUserId(),heroLandStatisticsTotalDP);
        }
        /*
         *
         *   4 答对率
         *
         */
        List<HeroLandStatisticsTotalDP> answerRightRate = heroLandCompetitionRecordService.getAnswerRightRate(heroLandStatisticsTotalQO);

        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : answerRightRate) {
            HeroLandStatisticsTotalDP totalDP = mergeMap.get(heroLandStatisticsTotalDP.getOrgCode() + heroLandStatisticsTotalDP.getUserId());
            if (totalDP != null){
                totalDP.setAnswerRightRate(heroLandStatisticsTotalDP.getAnswerRightRate());
            }
        }

        /*
         *  3 完成率
         */
        List<HeroLandStatisticsTotalDP> completeRate = heroLandCompetitionRecordService.getCompleteRate(heroLandStatisticsTotalQO);

        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : completeRate) {
            HeroLandStatisticsTotalDP totalDP = mergeMap.get(heroLandStatisticsTotalDP.getOrgCode() + heroLandStatisticsTotalDP.getUserId());
            if (totalDP != null){
                totalDP.setCompleteRate(heroLandStatisticsTotalDP.getCompleteRate());
            }
        }
        /*
         *5 胜率
         */
        List<HeroLandStatisticsTotalDP> winRate = heroLandCompetitionRecordService.getWinRate(heroLandStatisticsTotalQO);
        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : winRate) {
            HeroLandStatisticsTotalDP totalDP = mergeMap.get(heroLandStatisticsTotalDP.getOrgCode() + heroLandStatisticsTotalDP.getUserId());
            if (totalDP != null){
                totalDP.setWinRate(heroLandStatisticsTotalDP.getWinRate());
            }
        }
        /*
         * 6 总时长
         */
        List<HeroLandStatisticsTotalDP> totalTime = heroLandCompetitionRecordService.getTotalTime(heroLandStatisticsTotalQO);
        for (HeroLandStatisticsTotalDP heroLandStatisticsTotalDP : totalTime) {
            HeroLandStatisticsTotalDP totalDP = mergeMap.get(heroLandStatisticsTotalDP.getOrgCode() + heroLandStatisticsTotalDP.getUserId());
            if (totalDP != null){
                totalDP.setTotalTime(heroLandStatisticsTotalDP.getTotalTime());
            }
        }
        heroLandCompetitionStatisticsService.saveStatisticsTotal(totalSyncTotalScore);

        /*
         * detail ------------>
         * 同步作业赛总分数 、总平均分、 总场次
         *
         *
         */

        List<HeroLandStatisticsDetailDP> detailSyncTotalScore = heroLandCompetitionRecordService.getTotalScoreDetail(heroLandStatisticsTotalQO);

        heroLandCompetitionStatisticsService.saveStatisticsTotalAndDetail(null,detailSyncTotalScore);


    }


}

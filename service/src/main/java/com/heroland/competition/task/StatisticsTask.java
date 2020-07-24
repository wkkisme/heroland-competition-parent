package com.heroland.competition.task;

import com.heroland.competition.domain.dp.HeroLandCompetitionRecordDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

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
         * 同步作业赛总分数 、总平均分、 总场次
         */

        List<HeroLandStatisticsTotalDP> totalSyncTotalScore = heroLandCompetitionRecordService.getTotalScore(heroLandStatisticsTotalQO);

        /*
         *
         *   4 答对率
         *
         */
        List<HeroLandStatisticsTotalDP> answerRightRate =  heroLandCompetitionRecordService.getAnswerRightRate(heroLandStatisticsTotalQO);

        /*
         *  3 完成率
         */
        List<HeroLandStatisticsTotalDP> completeRate=  heroLandCompetitionRecordService.getCompleteRate(heroLandStatisticsTotalQO);

        /*
         *5 胜率
         */
        List<HeroLandStatisticsTotalDP> winRate=  heroLandCompetitionRecordService.getWinRate(heroLandStatisticsTotalQO);
        /*
         * 6 总时长
         */
        List<HeroLandStatisticsTotalDP> totalTime=  heroLandCompetitionRecordService.getTotalTime(heroLandStatisticsTotalQO);







        List<HeroLandStatisticsDetailDP> detailSyncTotalScore = heroLandCompetitionRecordService.getTotalScoreDetail(heroLandStatisticsTotalQO);





        heroLandCompetitionStatisticsService.saveStatisticsTotal(totalSyncTotalScore);

    }
}

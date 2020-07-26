package com.heroland.competition.task;

import com.anycommon.response.utils.BeanUtil;
import com.google.common.collect.Maps;
import com.heroland.competition.domain.dp.HeroLandStatisticsDetailDP;
import com.heroland.competition.domain.dp.HeroLandStatisticsTotalDP;
import com.heroland.competition.domain.qo.HeroLandStatisticsAllQO;
import com.heroland.competition.domain.qo.HeroLandStatisticsTotalQO;
import com.heroland.competition.service.HeroLandCompetitionRecordService;
import com.heroland.competition.service.statistics.HeroLandCompetitionStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 统计
 *
 * @author wangk
 */
//@Component
@Slf4j
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

        HeroLandStatisticsTotalQO qo = new HeroLandStatisticsTotalQO();
        qo.setHistory(false);
        heroLandCompetitionStatisticsService.updateHistoryStatisticsTotalAndDetailByQO(qo);


        HeroLandStatisticsAllQO heroLandStatisticsTotalQO = new HeroLandStatisticsAllQO();
        heroLandStatisticsTotalQO.setHistory(false);

        /*
         * 同步作业赛总分数 、总平均分、 总场次 total------>
         * 需要group 机构。--》年级----》  班级----》 科目--》用户
         */
        heroLandStatisticsTotalQO.setGroupByOrgCode(true);
        heroLandStatisticsTotalQO.setGroupByGradeCode(true);
        heroLandStatisticsTotalQO.setGroupByClassCode(true);
        heroLandStatisticsTotalQO.setGroupBySubjectCode(true);
        heroLandStatisticsTotalQO.setGroupByInviteId(true);
        heroLandStatisticsTotalQO.setGroupByOpponentId(true);
        heroLandStatisticsTotalQO.setGroupByType(true);

        List<HeroLandStatisticsDetailDP> totalSyncTotalScore = heroLandCompetitionRecordService.getTotalScore(heroLandStatisticsTotalQO);

        Map<String, HeroLandStatisticsDetailDP> mergeMap = Maps.newHashMapWithExpectedSize(totalSyncTotalScore.size());
        for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDP : totalSyncTotalScore) {
            mergeMap.put(heroLandStatisticsTotalDP.getOrgCode() + heroLandStatisticsTotalDP.getUserId(), heroLandStatisticsTotalDP);
        }

        /*
         *
         *   4 答对率
         *
         */
        List<HeroLandStatisticsDetailDP> answerRightRate = heroLandCompetitionRecordService.getAnswerRightRate(heroLandStatisticsTotalQO);

        for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDP : answerRightRate) {
            HeroLandStatisticsDetailDP totalDP = mergeMap.get(heroLandStatisticsTotalDP.getOrgCode() + heroLandStatisticsTotalDP.getUserId());
            if (totalDP != null) {
                totalDP.setAnswerRightRate(heroLandStatisticsTotalDP.getAnswerRightRate());
            }
        }

        /*
         *  3 完成率
         */
        List<HeroLandStatisticsDetailDP> completeRate = heroLandCompetitionRecordService.getCompleteRate(heroLandStatisticsTotalQO);

        for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDP : completeRate) {
            HeroLandStatisticsDetailDP totalDP = mergeMap.get(heroLandStatisticsTotalDP.getOrgCode() + heroLandStatisticsTotalDP.getUserId());
            if (totalDP != null) {
                totalDP.setCompleteRate(heroLandStatisticsTotalDP.getCompleteRate());
            }
        }

        /*
         *5 胜率
         */
        List<HeroLandStatisticsDetailDP> winRate = heroLandCompetitionRecordService.getWinRate(heroLandStatisticsTotalQO);
        for (HeroLandStatisticsDetailDP heroLandStatisticsTotalDp : winRate) {
            HeroLandStatisticsDetailDP totalDP = mergeMap.get(heroLandStatisticsTotalDp.getOrgCode() + heroLandStatisticsTotalDp.getUserId());
            if (totalDP != null) {
                totalDP.setWinRate(heroLandStatisticsTotalDp.getWinRate());
            }
        }

        /*
         * 6 总时长
         */
        List<HeroLandStatisticsDetailDP> totalTime = heroLandCompetitionRecordService.getTotalTime(heroLandStatisticsTotalQO);
        for (HeroLandStatisticsDetailDP herolandstatisticstotaldp : totalTime) {
            HeroLandStatisticsDetailDP totaldp = mergeMap.get(herolandstatisticstotaldp.getOrgCode() + herolandstatisticstotaldp.getUserId());
            if (totaldp != null) {
                totaldp.setTotalTime(herolandstatisticstotaldp.getTotalTime());
            }
        }

        try {

            heroLandCompetitionStatisticsService.saveStatisticsTotalAndDetail(null, new ArrayList<>(mergeMap.values()));
        } catch (Exception e) {
            log.error("", e);
        }


    }


}

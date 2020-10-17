package com.heroland.competition.factory;

import com.heroland.competition.common.utils.IDGenerateUtils;
import com.heroland.competition.domain.dp.OnlineDP;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import static com.heroland.competition.common.utils.IDGenerateUtils.ModelEnum.ADMIN;

/**
 *  机器人创建工厂
 * @date
 * @author wangkai
 */
public class RobotFactory {

    public static Set<OnlineDP> createRobot(int robotCount,String topicId){

        if (robotCount <= 0){
            return new HashSet<>();
        }
        Set<OnlineDP> robots = new LinkedHashSet<>();
        for (int i = 0; i < robotCount; i++) {
            OnlineDP onlineDP = new OnlineDP();
            onlineDP.setRobot(true);
            onlineDP.setSenderId(IDGenerateUtils.getIdByRandom(ADMIN) + "");
            onlineDP.setSenderName("robot"+i);
            onlineDP.setTopicId(topicId);
            robots.add(onlineDP);
        }
        return robots;
    }
}

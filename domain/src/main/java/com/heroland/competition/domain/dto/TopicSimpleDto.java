package com.heroland.competition.domain.dto;

import com.heroland.competition.common.constant.TopicJoinConstant;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wushuaiping
 * @date 2020/8/3 21:42
 */
@Data
public class TopicSimpleDto implements Serializable {

    /**
     * 题组名称
     */
    private String topicName;

    /**
     * 题组比赛类型
     */
    private Integer topicType;

    /**
     * 题组id
     */
    private Long topicId;

    private Date startTime;

    private Date endTime;

    /**
     * topic状态
     */
    private String topicState;


    public String getTopicState() {
        Date now = new Date();
        if (now.before(startTime)){
            topicState = TopicJoinConstant.NOTSTART;
        }else if (now.after(endTime)){
            topicState = TopicJoinConstant.OVERDUE;
        }else {
            topicState = TopicJoinConstant.DOING;
        }
        return topicState;
    }
}

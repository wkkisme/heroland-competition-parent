package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;

import java.io.Serializable;

public class HeroLandCompetitionRecordDP extends BaseDO implements Serializable {
    /**
     * 题组id
     */
    private String topicId;

    /**
     * 对应某一次比赛名称
     */
    private String topicName;

    /**
     * 作为 0 邀请方 1 被邀请方
     */
    private Integer status;

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局、3 被邀请方手动放弃 4 被邀请方超时自动放弃
     */
    private Integer result;

    /**
     * 对手id
     */
    private String opponentId;

    /**
     * 话题类型（比赛类型）
     */
    private Integer topicType;

    /**
     * 记录唯一id
     */
    private String recordId;

    /**
     * heroland_competition_record
     */
    private static final long serialVersionUID = 1L;

    /**
     * 题组id
     * @return topic_id 题组id
     */
    public String getTopicId() {
        return topicId;
    }

    /**
     * 题组id
     * @param topicId 题组id
     */
    public void setTopicId(String topicId) {
        this.topicId = topicId == null ? null : topicId.trim();
    }

    /**
     * 对应某一次比赛名称
     * @return topic_name 对应某一次比赛名称
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * 对应某一次比赛名称
     * @param topicName 对应某一次比赛名称
     */
    public void setTopicName(String topicName) {
        this.topicName = topicName == null ? null : topicName.trim();
    }

    /**
     * 作为 0 邀请方 1 被邀请方
     * @return status 作为 0 邀请方 1 被邀请方
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 作为 0 邀请方 1 被邀请方
     * @param status 作为 0 邀请方 1 被邀请方
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局、3 被邀请方手动放弃 4 被邀请方超时自动放弃
     * @return result 0 邀请方胜利。1 被邀请方胜利。2 平局、3 被邀请方手动放弃 4 被邀请方超时自动放弃
     */
    public Integer getResult() {
        return result;
    }

    /**
     * 0 邀请方胜利。1 被邀请方胜利。2 平局、3 被邀请方手动放弃 4 被邀请方超时自动放弃
     * @param result 0 邀请方胜利。1 被邀请方胜利。2 平局、3 被邀请方手动放弃 4 被邀请方超时自动放弃
     */
    public void setResult(Integer result) {
        this.result = result;
    }

    /**
     * 对手id
     * @return opponent_id 对手id
     */
    public String getOpponentId() {
        return opponentId;
    }

    /**
     * 对手id
     * @param opponentId 对手id
     */
    public void setOpponentId(String opponentId) {
        this.opponentId = opponentId == null ? null : opponentId.trim();
    }

    /**
     * 话题类型（比赛类型）
     * @return topic_type 话题类型（比赛类型）
     */
    public Integer getTopicType() {
        return topicType;
    }

    /**
     * 话题类型（比赛类型）
     * @param topicType 话题类型（比赛类型）
     */
    public void setTopicType(Integer topicType) {
        this.topicType = topicType;
    }

    /**
     * 记录唯一id
     * @return record_id 记录唯一id
     */
    public String getRecordId() {
        return recordId;
    }

    /**
     * 记录唯一id
     * @param recordId 记录唯一id
     */
    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }
}
package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.anycommon.response.utils.ResponseBodyWrapper;
import com.heroland.competition.common.constants.AdminFieldEnum;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.DateUtils;
import com.heroland.competition.common.utils.IDGenerateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "com.heroland.competition.dal.pojo.HeroLandTopicGroup")
@Data
public class HeroLandTopicGroupDP extends BaseDO implements Serializable {
    /**
     * 机构code
     */
    @ApiModelProperty(value = "orgCode机构code")
    private String orgCode;

    /**
     * 题目组名称
     */
    @ApiModelProperty(value = "topicName题目组名称")
    private String topicName;

    /**
     * 年级code
     */
    @ApiModelProperty(value = "gradeCode年级code")
    private String gradeCode;

    /**
     * 班级code
     */
    @ApiModelProperty(value = "classCode班级code")
    private String classCode;

    /**
     * 科目code
     */
    @ApiModelProperty(value = "courseCode科目code")
    private String courseCode;

    /**
     * 类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛
     */
    @ApiModelProperty(value = "type类型 0同步作业赛 1 寒假作业赛 2 暑假作业赛 3 应试赛 4 校级赛 5 世界赛 ")
    private Integer type;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 题目组id
     */
    @ApiModelProperty(value = "topicId题目组id")
    private String topicId;

    @ApiModelProperty(value = "questions题目")
    private List<HerolandQuestionUniqDP> questions;

    /**
     * 简介
     */
    private String description;

    /**
     * 报名开始时间
     * 针对世界赛
     */
    private Date registerBeginTime;

    /**
     * 报名结束时间
     * 针对世界赛
     */
    private Date registerEndTime;

    /**
     * 比赛的限制人数
     * 针对世界赛
     */
    private Long countLimit;

    /**
     * 比赛的报名人数
     * 针对世界赛
     */
    private Long registerCount;



    public HeroLandTopicGroupDP addCheckAndInit() {
        Date now = new Date();
        if (StringUtils.isBlank(this.getTopicName())) {
            ResponseBodyWrapper.failParamException();
        }

        if (this.getType() == null) {
            ResponseBodyWrapper.failParamException();
        }
        if (startTime == null || endTime == null){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME.getErrorMessage());
        }
        //这四种比赛的时间按天设置
        if (type == 0 || type == 1 || type == 2 || type == 3){
            Date date = DateUtils.formatDate(now, DateUtils.PATTERN_DATE);
            if (startTime.before(date)){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME_1.getErrorMessage());
            }
        }else {
            if (startTime.before(now)){
                ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME_1.getErrorMessage());
            }

        }
        if (startTime.after(endTime)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME.getErrorMessage());
        }

        if (StringUtils.isBlank(topicName)) {
            ResponseBodyWrapper.failParamException();
        }

        this.topicId = IDGenerateUtils.getKey(AdminFieldEnum.TOPIC);
        this.beforeInsert();
        return this;
    }

    public HeroLandTopicGroupDP deleteCheck() {
        if (this.getId() == null) {
            ResponseBodyWrapper.failParamException();
        }
        this.beforeDelete();

        return this;
    }

    public HeroLandTopicGroupDP updateCheck() {
        if (this.getId() == null) {
            ResponseBodyWrapper.failParamException();
        }
        if (this.getType() == null) {
            ResponseBodyWrapper.failParamException();
        }
        if (startTime == null || endTime == null){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME.getErrorMessage());
        }
        if (startTime.after(endTime)){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME.getErrorMessage());
        }
        if (startTime.before(new Date())){
            ResponseBodyWrapper.failException(HerolandErrMsgEnum.ERROR_TIME_1.getErrorMessage());
        }
        if (StringUtils.isBlank(topicName)) {
            ResponseBodyWrapper.failParamException();
        }
        return this;
    }
}
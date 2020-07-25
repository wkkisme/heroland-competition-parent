package com.heroland.competition.domain.dp;

import com.anycommon.response.common.BaseDO;
import com.google.common.collect.Lists;
import com.heroland.competition.common.enums.HerolandErrMsgEnum;
import com.heroland.competition.common.utils.AssertUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author smjyouzan
 * @date 2020/7/13
 */
@Data
public class HerolandCourseDP extends BaseDO implements Serializable {

    private String grade;

    private Integer gradeSlice;

    private String course;

    private String edition;

    private String subType;


    private List<HerolandSchoolCourseDP> schoolCourseDPS = Lists.newArrayList();


    public HerolandCourseDP checkAndBuildBeforeCreate(){
        AssertUtils.notBlank(course, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notBlank(edition, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notNull(gradeSlice, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
        AssertUtils.notNull(subType, HerolandErrMsgEnum.EMPTY_PARAM.getErrorMessage());
//        AssertUtils.assertThat(checkFormat(subType), HerolandErrMsgEnum.PARAM_ERROR.getErrorMessage());
        return this;
    }




    public static final Pattern patternBi = Pattern.compile("^必修((\\d+)|(\\d+-\\d+))?$");
    public static final Pattern patternXuan = Pattern.compile("^(选|選)修((\\d+)|(\\d+-\\d+))?$");

    /**
     * 校验必修|选修格式
     * @param subType
     */
    public static boolean checkFormat(String subType){
       if (StringUtils.isBlank(subType)){
           return true;
       }
        boolean bi = patternBi.matcher(subType).find();
        boolean xuan = patternXuan.matcher(subType).find();
        return (bi || xuan);
    }

    public static void main(String[] args) {
        boolean b = checkFormat("必修3-1");
        boolean b2 = checkFormat("必修");
        boolean b3 = checkFormat("选修");
        boolean b4 = checkFormat("选修2");
        boolean b5 = checkFormat("选修3-1");
        boolean b6 = checkFormat("选修cnda");
        boolean b7 = checkFormat("選修3-2");
        System.out.println("%%%%%%^^^^^^^^^^^^^^^^");
        System.out.println(b);
        System.out.println(b2);
        System.out.println(b3);
        System.out.println(b4);
        System.out.println(b5);
        System.out.println(b6);
        System.out.println(b7);
    }

}

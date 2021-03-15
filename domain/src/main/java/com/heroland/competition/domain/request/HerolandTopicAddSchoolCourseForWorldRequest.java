package com.heroland.competition.domain.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author smjyouzan
 * @date 2020/7/18
 */
@Data
public class HerolandTopicAddSchoolCourseForWorldRequest implements Serializable {

    /**
     * 年级code
     */
    @NotNull
    private String gradeCode;


    /**
     * 科目code
     */
    @NotNull
    private String courseCode;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HerolandTopicAddSchoolCourseForWorldRequest that = (HerolandTopicAddSchoolCourseForWorldRequest) o;
        return Objects.equals(gradeCode, that.gradeCode) &&
                Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gradeCode, courseCode);
    }
}

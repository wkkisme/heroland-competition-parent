package com.heroland.competition.dal.pojo;

import java.util.ArrayList;
import java.util.List;

public class MappingQuestionsExample {
    /**
     * mapping_questions
     */
    protected String orderByClause;

    /**
     * mapping_questions
     */
    protected boolean distinct;

    /**
     * mapping_questions
     */
    protected List<Criteria> oredCriteria;

    public MappingQuestionsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * mapping_questions 2020-11-01
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andQtpyeIsNull() {
            addCriterion("qtpye is null");
            return (Criteria) this;
        }

        public Criteria andQtpyeIsNotNull() {
            addCriterion("qtpye is not null");
            return (Criteria) this;
        }

        public Criteria andQtpyeEqualTo(String value) {
            addCriterion("qtpye =", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeNotEqualTo(String value) {
            addCriterion("qtpye <>", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeGreaterThan(String value) {
            addCriterion("qtpye >", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeGreaterThanOrEqualTo(String value) {
            addCriterion("qtpye >=", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeLessThan(String value) {
            addCriterion("qtpye <", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeLessThanOrEqualTo(String value) {
            addCriterion("qtpye <=", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeLike(String value) {
            addCriterion("qtpye like", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeNotLike(String value) {
            addCriterion("qtpye not like", value, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeIn(List<String> values) {
            addCriterion("qtpye in", values, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeNotIn(List<String> values) {
            addCriterion("qtpye not in", values, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeBetween(String value1, String value2) {
            addCriterion("qtpye between", value1, value2, "qtpye");
            return (Criteria) this;
        }

        public Criteria andQtpyeNotBetween(String value1, String value2) {
            addCriterion("qtpye not between", value1, value2, "qtpye");
            return (Criteria) this;
        }

        public Criteria andDiffIsNull() {
            addCriterion("diff is null");
            return (Criteria) this;
        }

        public Criteria andDiffIsNotNull() {
            addCriterion("diff is not null");
            return (Criteria) this;
        }

        public Criteria andDiffEqualTo(Float value) {
            addCriterion("diff =", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffNotEqualTo(Float value) {
            addCriterion("diff <>", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffGreaterThan(Float value) {
            addCriterion("diff >", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffGreaterThanOrEqualTo(Float value) {
            addCriterion("diff >=", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffLessThan(Float value) {
            addCriterion("diff <", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffLessThanOrEqualTo(Float value) {
            addCriterion("diff <=", value, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffIn(List<Float> values) {
            addCriterion("diff in", values, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffNotIn(List<Float> values) {
            addCriterion("diff not in", values, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffBetween(Float value1, Float value2) {
            addCriterion("diff between", value1, value2, "diff");
            return (Criteria) this;
        }

        public Criteria andDiffNotBetween(Float value1, Float value2) {
            addCriterion("diff not between", value1, value2, "diff");
            return (Criteria) this;
        }

        public Criteria andMd5IsNull() {
            addCriterion("md5 is null");
            return (Criteria) this;
        }

        public Criteria andMd5IsNotNull() {
            addCriterion("md5 is not null");
            return (Criteria) this;
        }

        public Criteria andMd5EqualTo(String value) {
            addCriterion("md5 =", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotEqualTo(String value) {
            addCriterion("md5 <>", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThan(String value) {
            addCriterion("md5 >", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5GreaterThanOrEqualTo(String value) {
            addCriterion("md5 >=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThan(String value) {
            addCriterion("md5 <", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5LessThanOrEqualTo(String value) {
            addCriterion("md5 <=", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Like(String value) {
            addCriterion("md5 like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotLike(String value) {
            addCriterion("md5 not like", value, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5In(List<String> values) {
            addCriterion("md5 in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotIn(List<String> values) {
            addCriterion("md5 not in", values, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5Between(String value1, String value2) {
            addCriterion("md5 between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andMd5NotBetween(String value1, String value2) {
            addCriterion("md5 not between", value1, value2, "md5");
            return (Criteria) this;
        }

        public Criteria andSubjectidIsNull() {
            addCriterion("subjectId is null");
            return (Criteria) this;
        }

        public Criteria andSubjectidIsNotNull() {
            addCriterion("subjectId is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectidEqualTo(Byte value) {
            addCriterion("subjectId =", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotEqualTo(Byte value) {
            addCriterion("subjectId <>", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThan(Byte value) {
            addCriterion("subjectId >", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThanOrEqualTo(Byte value) {
            addCriterion("subjectId >=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThan(Byte value) {
            addCriterion("subjectId <", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThanOrEqualTo(Byte value) {
            addCriterion("subjectId <=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidIn(List<Byte> values) {
            addCriterion("subjectId in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotIn(List<Byte> values) {
            addCriterion("subjectId not in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidBetween(Byte value1, Byte value2) {
            addCriterion("subjectId between", value1, value2, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotBetween(Byte value1, Byte value2) {
            addCriterion("subjectId not between", value1, value2, "subjectid");
            return (Criteria) this;
        }

        public Criteria andGradeidIsNull() {
            addCriterion("gradeId is null");
            return (Criteria) this;
        }

        public Criteria andGradeidIsNotNull() {
            addCriterion("gradeId is not null");
            return (Criteria) this;
        }

        public Criteria andGradeidEqualTo(Integer value) {
            addCriterion("gradeId =", value, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidNotEqualTo(Integer value) {
            addCriterion("gradeId <>", value, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidGreaterThan(Integer value) {
            addCriterion("gradeId >", value, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("gradeId >=", value, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidLessThan(Integer value) {
            addCriterion("gradeId <", value, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidLessThanOrEqualTo(Integer value) {
            addCriterion("gradeId <=", value, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidIn(List<Integer> values) {
            addCriterion("gradeId in", values, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidNotIn(List<Integer> values) {
            addCriterion("gradeId not in", values, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidBetween(Integer value1, Integer value2) {
            addCriterion("gradeId between", value1, value2, "gradeid");
            return (Criteria) this;
        }

        public Criteria andGradeidNotBetween(Integer value1, Integer value2) {
            addCriterion("gradeId not between", value1, value2, "gradeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgesIsNull() {
            addCriterion("knowledges is null");
            return (Criteria) this;
        }

        public Criteria andKnowledgesIsNotNull() {
            addCriterion("knowledges is not null");
            return (Criteria) this;
        }

        public Criteria andKnowledgesEqualTo(String value) {
            addCriterion("knowledges =", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesNotEqualTo(String value) {
            addCriterion("knowledges <>", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesGreaterThan(String value) {
            addCriterion("knowledges >", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesGreaterThanOrEqualTo(String value) {
            addCriterion("knowledges >=", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesLessThan(String value) {
            addCriterion("knowledges <", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesLessThanOrEqualTo(String value) {
            addCriterion("knowledges <=", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesLike(String value) {
            addCriterion("knowledges like", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesNotLike(String value) {
            addCriterion("knowledges not like", value, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesIn(List<String> values) {
            addCriterion("knowledges in", values, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesNotIn(List<String> values) {
            addCriterion("knowledges not in", values, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesBetween(String value1, String value2) {
            addCriterion("knowledges between", value1, value2, "knowledges");
            return (Criteria) this;
        }

        public Criteria andKnowledgesNotBetween(String value1, String value2) {
            addCriterion("knowledges not between", value1, value2, "knowledges");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andYearIsNull() {
            addCriterion("year is null");
            return (Criteria) this;
        }

        public Criteria andYearIsNotNull() {
            addCriterion("year is not null");
            return (Criteria) this;
        }

        public Criteria andYearEqualTo(Integer value) {
            addCriterion("year =", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotEqualTo(Integer value) {
            addCriterion("year <>", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThan(Integer value) {
            addCriterion("year >", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearGreaterThanOrEqualTo(Integer value) {
            addCriterion("year >=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThan(Integer value) {
            addCriterion("year <", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearLessThanOrEqualTo(Integer value) {
            addCriterion("year <=", value, "year");
            return (Criteria) this;
        }

        public Criteria andYearIn(List<Integer> values) {
            addCriterion("year in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotIn(List<Integer> values) {
            addCriterion("year not in", values, "year");
            return (Criteria) this;
        }

        public Criteria andYearBetween(Integer value1, Integer value2) {
            addCriterion("year between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andYearNotBetween(Integer value1, Integer value2) {
            addCriterion("year not between", value1, value2, "year");
            return (Criteria) this;
        }

        public Criteria andPapertpyeIsNull() {
            addCriterion("paperTpye is null");
            return (Criteria) this;
        }

        public Criteria andPapertpyeIsNotNull() {
            addCriterion("paperTpye is not null");
            return (Criteria) this;
        }

        public Criteria andPapertpyeEqualTo(String value) {
            addCriterion("paperTpye =", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeNotEqualTo(String value) {
            addCriterion("paperTpye <>", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeGreaterThan(String value) {
            addCriterion("paperTpye >", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeGreaterThanOrEqualTo(String value) {
            addCriterion("paperTpye >=", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeLessThan(String value) {
            addCriterion("paperTpye <", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeLessThanOrEqualTo(String value) {
            addCriterion("paperTpye <=", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeLike(String value) {
            addCriterion("paperTpye like", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeNotLike(String value) {
            addCriterion("paperTpye not like", value, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeIn(List<String> values) {
            addCriterion("paperTpye in", values, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeNotIn(List<String> values) {
            addCriterion("paperTpye not in", values, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeBetween(String value1, String value2) {
            addCriterion("paperTpye between", value1, value2, "papertpye");
            return (Criteria) this;
        }

        public Criteria andPapertpyeNotBetween(String value1, String value2) {
            addCriterion("paperTpye not between", value1, value2, "papertpye");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andFromsiteIsNull() {
            addCriterion("fromSite is null");
            return (Criteria) this;
        }

        public Criteria andFromsiteIsNotNull() {
            addCriterion("fromSite is not null");
            return (Criteria) this;
        }

        public Criteria andFromsiteEqualTo(String value) {
            addCriterion("fromSite =", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotEqualTo(String value) {
            addCriterion("fromSite <>", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteGreaterThan(String value) {
            addCriterion("fromSite >", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteGreaterThanOrEqualTo(String value) {
            addCriterion("fromSite >=", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteLessThan(String value) {
            addCriterion("fromSite <", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteLessThanOrEqualTo(String value) {
            addCriterion("fromSite <=", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteLike(String value) {
            addCriterion("fromSite like", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotLike(String value) {
            addCriterion("fromSite not like", value, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteIn(List<String> values) {
            addCriterion("fromSite in", values, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotIn(List<String> values) {
            addCriterion("fromSite not in", values, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteBetween(String value1, String value2) {
            addCriterion("fromSite between", value1, value2, "fromsite");
            return (Criteria) this;
        }

        public Criteria andFromsiteNotBetween(String value1, String value2) {
            addCriterion("fromSite not between", value1, value2, "fromsite");
            return (Criteria) this;
        }

        public Criteria andIssubIsNull() {
            addCriterion("isSub is null");
            return (Criteria) this;
        }

        public Criteria andIssubIsNotNull() {
            addCriterion("isSub is not null");
            return (Criteria) this;
        }

        public Criteria andIssubEqualTo(Boolean value) {
            addCriterion("isSub =", value, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubNotEqualTo(Boolean value) {
            addCriterion("isSub <>", value, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubGreaterThan(Boolean value) {
            addCriterion("isSub >", value, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isSub >=", value, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubLessThan(Boolean value) {
            addCriterion("isSub <", value, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubLessThanOrEqualTo(Boolean value) {
            addCriterion("isSub <=", value, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubIn(List<Boolean> values) {
            addCriterion("isSub in", values, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubNotIn(List<Boolean> values) {
            addCriterion("isSub not in", values, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubBetween(Boolean value1, Boolean value2) {
            addCriterion("isSub between", value1, value2, "issub");
            return (Criteria) this;
        }

        public Criteria andIssubNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isSub not between", value1, value2, "issub");
            return (Criteria) this;
        }

        public Criteria andIsnormalIsNull() {
            addCriterion("isNormal is null");
            return (Criteria) this;
        }

        public Criteria andIsnormalIsNotNull() {
            addCriterion("isNormal is not null");
            return (Criteria) this;
        }

        public Criteria andIsnormalEqualTo(Boolean value) {
            addCriterion("isNormal =", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalNotEqualTo(Boolean value) {
            addCriterion("isNormal <>", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalGreaterThan(Boolean value) {
            addCriterion("isNormal >", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isNormal >=", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalLessThan(Boolean value) {
            addCriterion("isNormal <", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalLessThanOrEqualTo(Boolean value) {
            addCriterion("isNormal <=", value, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalIn(List<Boolean> values) {
            addCriterion("isNormal in", values, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalNotIn(List<Boolean> values) {
            addCriterion("isNormal not in", values, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalBetween(Boolean value1, Boolean value2) {
            addCriterion("isNormal between", value1, value2, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIsnormalNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isNormal not between", value1, value2, "isnormal");
            return (Criteria) this;
        }

        public Criteria andIskonwIsNull() {
            addCriterion("isKonw is null");
            return (Criteria) this;
        }

        public Criteria andIskonwIsNotNull() {
            addCriterion("isKonw is not null");
            return (Criteria) this;
        }

        public Criteria andIskonwEqualTo(Boolean value) {
            addCriterion("isKonw =", value, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwNotEqualTo(Boolean value) {
            addCriterion("isKonw <>", value, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwGreaterThan(Boolean value) {
            addCriterion("isKonw >", value, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwGreaterThanOrEqualTo(Boolean value) {
            addCriterion("isKonw >=", value, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwLessThan(Boolean value) {
            addCriterion("isKonw <", value, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwLessThanOrEqualTo(Boolean value) {
            addCriterion("isKonw <=", value, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwIn(List<Boolean> values) {
            addCriterion("isKonw in", values, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwNotIn(List<Boolean> values) {
            addCriterion("isKonw not in", values, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwBetween(Boolean value1, Boolean value2) {
            addCriterion("isKonw between", value1, value2, "iskonw");
            return (Criteria) this;
        }

        public Criteria andIskonwNotBetween(Boolean value1, Boolean value2) {
            addCriterion("isKonw not between", value1, value2, "iskonw");
            return (Criteria) this;
        }

        public Criteria andTiidIsNull() {
            addCriterion("tiid is null");
            return (Criteria) this;
        }

        public Criteria andTiidIsNotNull() {
            addCriterion("tiid is not null");
            return (Criteria) this;
        }

        public Criteria andTiidEqualTo(String value) {
            addCriterion("tiid =", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidNotEqualTo(String value) {
            addCriterion("tiid <>", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidGreaterThan(String value) {
            addCriterion("tiid >", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidGreaterThanOrEqualTo(String value) {
            addCriterion("tiid >=", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidLessThan(String value) {
            addCriterion("tiid <", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidLessThanOrEqualTo(String value) {
            addCriterion("tiid <=", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidLike(String value) {
            addCriterion("tiid like", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidNotLike(String value) {
            addCriterion("tiid not like", value, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidIn(List<String> values) {
            addCriterion("tiid in", values, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidNotIn(List<String> values) {
            addCriterion("tiid not in", values, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidBetween(String value1, String value2) {
            addCriterion("tiid between", value1, value2, "tiid");
            return (Criteria) this;
        }

        public Criteria andTiidNotBetween(String value1, String value2) {
            addCriterion("tiid not between", value1, value2, "tiid");
            return (Criteria) this;
        }

        public Criteria andSimilarityIsNull() {
            addCriterion("Similarity is null");
            return (Criteria) this;
        }

        public Criteria andSimilarityIsNotNull() {
            addCriterion("Similarity is not null");
            return (Criteria) this;
        }

        public Criteria andSimilarityEqualTo(Integer value) {
            addCriterion("Similarity =", value, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityNotEqualTo(Integer value) {
            addCriterion("Similarity <>", value, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityGreaterThan(Integer value) {
            addCriterion("Similarity >", value, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityGreaterThanOrEqualTo(Integer value) {
            addCriterion("Similarity >=", value, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityLessThan(Integer value) {
            addCriterion("Similarity <", value, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityLessThanOrEqualTo(Integer value) {
            addCriterion("Similarity <=", value, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityIn(List<Integer> values) {
            addCriterion("Similarity in", values, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityNotIn(List<Integer> values) {
            addCriterion("Similarity not in", values, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityBetween(Integer value1, Integer value2) {
            addCriterion("Similarity between", value1, value2, "similarity");
            return (Criteria) this;
        }

        public Criteria andSimilarityNotBetween(Integer value1, Integer value2) {
            addCriterion("Similarity not between", value1, value2, "similarity");
            return (Criteria) this;
        }

        public Criteria andIsuniqueIsNull() {
            addCriterion("isunique is null");
            return (Criteria) this;
        }

        public Criteria andIsuniqueIsNotNull() {
            addCriterion("isunique is not null");
            return (Criteria) this;
        }

        public Criteria andIsuniqueEqualTo(Byte value) {
            addCriterion("isunique =", value, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueNotEqualTo(Byte value) {
            addCriterion("isunique <>", value, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueGreaterThan(Byte value) {
            addCriterion("isunique >", value, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueGreaterThanOrEqualTo(Byte value) {
            addCriterion("isunique >=", value, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueLessThan(Byte value) {
            addCriterion("isunique <", value, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueLessThanOrEqualTo(Byte value) {
            addCriterion("isunique <=", value, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueIn(List<Byte> values) {
            addCriterion("isunique in", values, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueNotIn(List<Byte> values) {
            addCriterion("isunique not in", values, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueBetween(Byte value1, Byte value2) {
            addCriterion("isunique between", value1, value2, "isunique");
            return (Criteria) this;
        }

        public Criteria andIsuniqueNotBetween(Byte value1, Byte value2) {
            addCriterion("isunique not between", value1, value2, "isunique");
            return (Criteria) this;
        }

        public Criteria andMd52IsNull() {
            addCriterion("md52 is null");
            return (Criteria) this;
        }

        public Criteria andMd52IsNotNull() {
            addCriterion("md52 is not null");
            return (Criteria) this;
        }

        public Criteria andMd52EqualTo(String value) {
            addCriterion("md52 =", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52NotEqualTo(String value) {
            addCriterion("md52 <>", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52GreaterThan(String value) {
            addCriterion("md52 >", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52GreaterThanOrEqualTo(String value) {
            addCriterion("md52 >=", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52LessThan(String value) {
            addCriterion("md52 <", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52LessThanOrEqualTo(String value) {
            addCriterion("md52 <=", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52Like(String value) {
            addCriterion("md52 like", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52NotLike(String value) {
            addCriterion("md52 not like", value, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52In(List<String> values) {
            addCriterion("md52 in", values, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52NotIn(List<String> values) {
            addCriterion("md52 not in", values, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52Between(String value1, String value2) {
            addCriterion("md52 between", value1, value2, "md52");
            return (Criteria) this;
        }

        public Criteria andMd52NotBetween(String value1, String value2) {
            addCriterion("md52 not between", value1, value2, "md52");
            return (Criteria) this;
        }
    }

    /**
     * mapping_questions
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * mapping_questions 2020-11-01
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
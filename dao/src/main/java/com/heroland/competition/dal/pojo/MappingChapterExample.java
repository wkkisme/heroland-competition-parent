package com.heroland.competition.dal.pojo;

import java.util.ArrayList;
import java.util.List;

public class MappingChapterExample {
    /**
     * mapping_chapter
     */
    protected String orderByClause;

    /**
     * mapping_chapter
     */
    protected boolean distinct;

    /**
     * mapping_chapter
     */
    protected List<Criteria> oredCriteria;

    public MappingChapterExample() {
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
     * mapping_chapter 2020-11-01
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

        public Criteria andSubjectidIsNull() {
            addCriterion("subjectId is null");
            return (Criteria) this;
        }

        public Criteria andSubjectidIsNotNull() {
            addCriterion("subjectId is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectidEqualTo(Integer value) {
            addCriterion("subjectId =", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotEqualTo(Integer value) {
            addCriterion("subjectId <>", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThan(Integer value) {
            addCriterion("subjectId >", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidGreaterThanOrEqualTo(Integer value) {
            addCriterion("subjectId >=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThan(Integer value) {
            addCriterion("subjectId <", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidLessThanOrEqualTo(Integer value) {
            addCriterion("subjectId <=", value, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidIn(List<Integer> values) {
            addCriterion("subjectId in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotIn(List<Integer> values) {
            addCriterion("subjectId not in", values, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidBetween(Integer value1, Integer value2) {
            addCriterion("subjectId between", value1, value2, "subjectid");
            return (Criteria) this;
        }

        public Criteria andSubjectidNotBetween(Integer value1, Integer value2) {
            addCriterion("subjectId not between", value1, value2, "subjectid");
            return (Criteria) this;
        }

        public Criteria andPharseidIsNull() {
            addCriterion("pharseId is null");
            return (Criteria) this;
        }

        public Criteria andPharseidIsNotNull() {
            addCriterion("pharseId is not null");
            return (Criteria) this;
        }

        public Criteria andPharseidEqualTo(Integer value) {
            addCriterion("pharseId =", value, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidNotEqualTo(Integer value) {
            addCriterion("pharseId <>", value, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidGreaterThan(Integer value) {
            addCriterion("pharseId >", value, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pharseId >=", value, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidLessThan(Integer value) {
            addCriterion("pharseId <", value, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidLessThanOrEqualTo(Integer value) {
            addCriterion("pharseId <=", value, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidIn(List<Integer> values) {
            addCriterion("pharseId in", values, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidNotIn(List<Integer> values) {
            addCriterion("pharseId not in", values, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidBetween(Integer value1, Integer value2) {
            addCriterion("pharseId between", value1, value2, "pharseid");
            return (Criteria) this;
        }

        public Criteria andPharseidNotBetween(Integer value1, Integer value2) {
            addCriterion("pharseId not between", value1, value2, "pharseid");
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

        public Criteria andEditionidIsNull() {
            addCriterion("editionId is null");
            return (Criteria) this;
        }

        public Criteria andEditionidIsNotNull() {
            addCriterion("editionId is not null");
            return (Criteria) this;
        }

        public Criteria andEditionidEqualTo(Integer value) {
            addCriterion("editionId =", value, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidNotEqualTo(Integer value) {
            addCriterion("editionId <>", value, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidGreaterThan(Integer value) {
            addCriterion("editionId >", value, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidGreaterThanOrEqualTo(Integer value) {
            addCriterion("editionId >=", value, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidLessThan(Integer value) {
            addCriterion("editionId <", value, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidLessThanOrEqualTo(Integer value) {
            addCriterion("editionId <=", value, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidIn(List<Integer> values) {
            addCriterion("editionId in", values, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidNotIn(List<Integer> values) {
            addCriterion("editionId not in", values, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidBetween(Integer value1, Integer value2) {
            addCriterion("editionId between", value1, value2, "editionid");
            return (Criteria) this;
        }

        public Criteria andEditionidNotBetween(Integer value1, Integer value2) {
            addCriterion("editionId not between", value1, value2, "editionid");
            return (Criteria) this;
        }

        public Criteria andChapterIsNull() {
            addCriterion("chapter is null");
            return (Criteria) this;
        }

        public Criteria andChapterIsNotNull() {
            addCriterion("chapter is not null");
            return (Criteria) this;
        }

        public Criteria andChapterEqualTo(String value) {
            addCriterion("chapter =", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterNotEqualTo(String value) {
            addCriterion("chapter <>", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterGreaterThan(String value) {
            addCriterion("chapter >", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterGreaterThanOrEqualTo(String value) {
            addCriterion("chapter >=", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterLessThan(String value) {
            addCriterion("chapter <", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterLessThanOrEqualTo(String value) {
            addCriterion("chapter <=", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterLike(String value) {
            addCriterion("chapter like", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterNotLike(String value) {
            addCriterion("chapter not like", value, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterIn(List<String> values) {
            addCriterion("chapter in", values, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterNotIn(List<String> values) {
            addCriterion("chapter not in", values, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterBetween(String value1, String value2) {
            addCriterion("chapter between", value1, value2, "chapter");
            return (Criteria) this;
        }

        public Criteria andChapterNotBetween(String value1, String value2) {
            addCriterion("chapter not between", value1, value2, "chapter");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andSectionIsNull() {
            addCriterion("section is null");
            return (Criteria) this;
        }

        public Criteria andSectionIsNotNull() {
            addCriterion("section is not null");
            return (Criteria) this;
        }

        public Criteria andSectionEqualTo(String value) {
            addCriterion("section =", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotEqualTo(String value) {
            addCriterion("section <>", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionGreaterThan(String value) {
            addCriterion("section >", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionGreaterThanOrEqualTo(String value) {
            addCriterion("section >=", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionLessThan(String value) {
            addCriterion("section <", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionLessThanOrEqualTo(String value) {
            addCriterion("section <=", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionLike(String value) {
            addCriterion("section like", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotLike(String value) {
            addCriterion("section not like", value, "section");
            return (Criteria) this;
        }

        public Criteria andSectionIn(List<String> values) {
            addCriterion("section in", values, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotIn(List<String> values) {
            addCriterion("section not in", values, "section");
            return (Criteria) this;
        }

        public Criteria andSectionBetween(String value1, String value2) {
            addCriterion("section between", value1, value2, "section");
            return (Criteria) this;
        }

        public Criteria andSectionNotBetween(String value1, String value2) {
            addCriterion("section not between", value1, value2, "section");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidIsNull() {
            addCriterion("knowledgeId is null");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidIsNotNull() {
            addCriterion("knowledgeId is not null");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidEqualTo(Integer value) {
            addCriterion("knowledgeId =", value, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidNotEqualTo(Integer value) {
            addCriterion("knowledgeId <>", value, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidGreaterThan(Integer value) {
            addCriterion("knowledgeId >", value, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidGreaterThanOrEqualTo(Integer value) {
            addCriterion("knowledgeId >=", value, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidLessThan(Integer value) {
            addCriterion("knowledgeId <", value, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidLessThanOrEqualTo(Integer value) {
            addCriterion("knowledgeId <=", value, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidIn(List<Integer> values) {
            addCriterion("knowledgeId in", values, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidNotIn(List<Integer> values) {
            addCriterion("knowledgeId not in", values, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidBetween(Integer value1, Integer value2) {
            addCriterion("knowledgeId between", value1, value2, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andKnowledgeidNotBetween(Integer value1, Integer value2) {
            addCriterion("knowledgeId not between", value1, value2, "knowledgeid");
            return (Criteria) this;
        }

        public Criteria andChapterorderIsNull() {
            addCriterion("chapterOrder is null");
            return (Criteria) this;
        }

        public Criteria andChapterorderIsNotNull() {
            addCriterion("chapterOrder is not null");
            return (Criteria) this;
        }

        public Criteria andChapterorderEqualTo(Integer value) {
            addCriterion("chapterOrder =", value, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderNotEqualTo(Integer value) {
            addCriterion("chapterOrder <>", value, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderGreaterThan(Integer value) {
            addCriterion("chapterOrder >", value, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderGreaterThanOrEqualTo(Integer value) {
            addCriterion("chapterOrder >=", value, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderLessThan(Integer value) {
            addCriterion("chapterOrder <", value, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderLessThanOrEqualTo(Integer value) {
            addCriterion("chapterOrder <=", value, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderIn(List<Integer> values) {
            addCriterion("chapterOrder in", values, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderNotIn(List<Integer> values) {
            addCriterion("chapterOrder not in", values, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderBetween(Integer value1, Integer value2) {
            addCriterion("chapterOrder between", value1, value2, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andChapterorderNotBetween(Integer value1, Integer value2) {
            addCriterion("chapterOrder not between", value1, value2, "chapterorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderIsNull() {
            addCriterion("unitOrder is null");
            return (Criteria) this;
        }

        public Criteria andUnitorderIsNotNull() {
            addCriterion("unitOrder is not null");
            return (Criteria) this;
        }

        public Criteria andUnitorderEqualTo(Integer value) {
            addCriterion("unitOrder =", value, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderNotEqualTo(Integer value) {
            addCriterion("unitOrder <>", value, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderGreaterThan(Integer value) {
            addCriterion("unitOrder >", value, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderGreaterThanOrEqualTo(Integer value) {
            addCriterion("unitOrder >=", value, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderLessThan(Integer value) {
            addCriterion("unitOrder <", value, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderLessThanOrEqualTo(Integer value) {
            addCriterion("unitOrder <=", value, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderIn(List<Integer> values) {
            addCriterion("unitOrder in", values, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderNotIn(List<Integer> values) {
            addCriterion("unitOrder not in", values, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderBetween(Integer value1, Integer value2) {
            addCriterion("unitOrder between", value1, value2, "unitorder");
            return (Criteria) this;
        }

        public Criteria andUnitorderNotBetween(Integer value1, Integer value2) {
            addCriterion("unitOrder not between", value1, value2, "unitorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderIsNull() {
            addCriterion("sectionOrder is null");
            return (Criteria) this;
        }

        public Criteria andSectionorderIsNotNull() {
            addCriterion("sectionOrder is not null");
            return (Criteria) this;
        }

        public Criteria andSectionorderEqualTo(Integer value) {
            addCriterion("sectionOrder =", value, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderNotEqualTo(Integer value) {
            addCriterion("sectionOrder <>", value, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderGreaterThan(Integer value) {
            addCriterion("sectionOrder >", value, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sectionOrder >=", value, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderLessThan(Integer value) {
            addCriterion("sectionOrder <", value, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderLessThanOrEqualTo(Integer value) {
            addCriterion("sectionOrder <=", value, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderIn(List<Integer> values) {
            addCriterion("sectionOrder in", values, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderNotIn(List<Integer> values) {
            addCriterion("sectionOrder not in", values, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderBetween(Integer value1, Integer value2) {
            addCriterion("sectionOrder between", value1, value2, "sectionorder");
            return (Criteria) this;
        }

        public Criteria andSectionorderNotBetween(Integer value1, Integer value2) {
            addCriterion("sectionOrder not between", value1, value2, "sectionorder");
            return (Criteria) this;
        }
    }

    /**
     * mapping_chapter
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * mapping_chapter 2020-11-01
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
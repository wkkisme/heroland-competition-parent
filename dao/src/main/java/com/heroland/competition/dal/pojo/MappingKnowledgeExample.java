package com.heroland.competition.dal.pojo;

import java.util.ArrayList;
import java.util.List;

public class MappingKnowledgeExample {
    /**
     * mapping_knowledge
     */
    protected String orderByClause;

    /**
     * mapping_knowledge
     */
    protected boolean distinct;

    /**
     * mapping_knowledge
     */
    protected List<Criteria> oredCriteria;

    public MappingKnowledgeExample() {
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
     * mapping_knowledge 2020-11-01
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

        public Criteria andKnowledgenameIsNull() {
            addCriterion("knowledgeName is null");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameIsNotNull() {
            addCriterion("knowledgeName is not null");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameEqualTo(String value) {
            addCriterion("knowledgeName =", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameNotEqualTo(String value) {
            addCriterion("knowledgeName <>", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameGreaterThan(String value) {
            addCriterion("knowledgeName >", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameGreaterThanOrEqualTo(String value) {
            addCriterion("knowledgeName >=", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameLessThan(String value) {
            addCriterion("knowledgeName <", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameLessThanOrEqualTo(String value) {
            addCriterion("knowledgeName <=", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameLike(String value) {
            addCriterion("knowledgeName like", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameNotLike(String value) {
            addCriterion("knowledgeName not like", value, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameIn(List<String> values) {
            addCriterion("knowledgeName in", values, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameNotIn(List<String> values) {
            addCriterion("knowledgeName not in", values, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameBetween(String value1, String value2) {
            addCriterion("knowledgeName between", value1, value2, "knowledgename");
            return (Criteria) this;
        }

        public Criteria andKnowledgenameNotBetween(String value1, String value2) {
            addCriterion("knowledgeName not between", value1, value2, "knowledgename");
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
    }

    /**
     * mapping_knowledge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * mapping_knowledge 2020-11-01
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
package com.heroland.competition.dal.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HeroLandCompetitionRecordExample {
    /**
     * heroland_competition_record
     */
    protected String orderByClause;

    /**
     * heroland_competition_record
     */
    protected boolean distinct;

    /**
     * heroland_competition_record
     */
    protected List<Criteria> oredCriteria;

    public HeroLandCompetitionRecordExample() {
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
     * heroland_competition_record 2020-08-02
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNull() {
            addCriterion("topic_id is null");
            return (Criteria) this;
        }

        public Criteria andTopicIdIsNotNull() {
            addCriterion("topic_id is not null");
            return (Criteria) this;
        }

        public Criteria andTopicIdEqualTo(String value) {
            addCriterion("topic_id =", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotEqualTo(String value) {
            addCriterion("topic_id <>", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThan(String value) {
            addCriterion("topic_id >", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdGreaterThanOrEqualTo(String value) {
            addCriterion("topic_id >=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThan(String value) {
            addCriterion("topic_id <", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLessThanOrEqualTo(String value) {
            addCriterion("topic_id <=", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdLike(String value) {
            addCriterion("topic_id like", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotLike(String value) {
            addCriterion("topic_id not like", value, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdIn(List<String> values) {
            addCriterion("topic_id in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotIn(List<String> values) {
            addCriterion("topic_id not in", values, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdBetween(String value1, String value2) {
            addCriterion("topic_id between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicIdNotBetween(String value1, String value2) {
            addCriterion("topic_id not between", value1, value2, "topicId");
            return (Criteria) this;
        }

        public Criteria andTopicNameIsNull() {
            addCriterion("topic_name is null");
            return (Criteria) this;
        }

        public Criteria andTopicNameIsNotNull() {
            addCriterion("topic_name is not null");
            return (Criteria) this;
        }

        public Criteria andTopicNameEqualTo(String value) {
            addCriterion("topic_name =", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameNotEqualTo(String value) {
            addCriterion("topic_name <>", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameGreaterThan(String value) {
            addCriterion("topic_name >", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameGreaterThanOrEqualTo(String value) {
            addCriterion("topic_name >=", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameLessThan(String value) {
            addCriterion("topic_name <", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameLessThanOrEqualTo(String value) {
            addCriterion("topic_name <=", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameLike(String value) {
            addCriterion("topic_name like", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameNotLike(String value) {
            addCriterion("topic_name not like", value, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameIn(List<String> values) {
            addCriterion("topic_name in", values, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameNotIn(List<String> values) {
            addCriterion("topic_name not in", values, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameBetween(String value1, String value2) {
            addCriterion("topic_name between", value1, value2, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicNameNotBetween(String value1, String value2) {
            addCriterion("topic_name not between", value1, value2, "topicName");
            return (Criteria) this;
        }

        public Criteria andTopicTypeIsNull() {
            addCriterion("topic_type is null");
            return (Criteria) this;
        }

        public Criteria andTopicTypeIsNotNull() {
            addCriterion("topic_type is not null");
            return (Criteria) this;
        }

        public Criteria andTopicTypeEqualTo(Integer value) {
            addCriterion("topic_type =", value, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeNotEqualTo(Integer value) {
            addCriterion("topic_type <>", value, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeGreaterThan(Integer value) {
            addCriterion("topic_type >", value, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("topic_type >=", value, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeLessThan(Integer value) {
            addCriterion("topic_type <", value, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeLessThanOrEqualTo(Integer value) {
            addCriterion("topic_type <=", value, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeIn(List<Integer> values) {
            addCriterion("topic_type in", values, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeNotIn(List<Integer> values) {
            addCriterion("topic_type not in", values, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeBetween(Integer value1, Integer value2) {
            addCriterion("topic_type between", value1, value2, "topicType");
            return (Criteria) this;
        }

        public Criteria andTopicTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("topic_type not between", value1, value2, "topicType");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNull() {
            addCriterion("record_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordIdIsNotNull() {
            addCriterion("record_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordIdEqualTo(String value) {
            addCriterion("record_id =", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotEqualTo(String value) {
            addCriterion("record_id <>", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThan(String value) {
            addCriterion("record_id >", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdGreaterThanOrEqualTo(String value) {
            addCriterion("record_id >=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThan(String value) {
            addCriterion("record_id <", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLessThanOrEqualTo(String value) {
            addCriterion("record_id <=", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdLike(String value) {
            addCriterion("record_id like", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotLike(String value) {
            addCriterion("record_id not like", value, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdIn(List<String> values) {
            addCriterion("record_id in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotIn(List<String> values) {
            addCriterion("record_id not in", values, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdBetween(String value1, String value2) {
            addCriterion("record_id between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andRecordIdNotBetween(String value1, String value2) {
            addCriterion("record_id not between", value1, value2, "recordId");
            return (Criteria) this;
        }

        public Criteria andResultIsNull() {
            addCriterion("result is null");
            return (Criteria) this;
        }

        public Criteria andResultIsNotNull() {
            addCriterion("result is not null");
            return (Criteria) this;
        }

        public Criteria andResultEqualTo(Integer value) {
            addCriterion("result =", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotEqualTo(Integer value) {
            addCriterion("result <>", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThan(Integer value) {
            addCriterion("result >", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultGreaterThanOrEqualTo(Integer value) {
            addCriterion("result >=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThan(Integer value) {
            addCriterion("result <", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultLessThanOrEqualTo(Integer value) {
            addCriterion("result <=", value, "result");
            return (Criteria) this;
        }

        public Criteria andResultIn(List<Integer> values) {
            addCriterion("result in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotIn(List<Integer> values) {
            addCriterion("result not in", values, "result");
            return (Criteria) this;
        }

        public Criteria andResultBetween(Integer value1, Integer value2) {
            addCriterion("result between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andResultNotBetween(Integer value1, Integer value2) {
            addCriterion("result not between", value1, value2, "result");
            return (Criteria) this;
        }

        public Criteria andOpponentIdIsNull() {
            addCriterion("opponent_id is null");
            return (Criteria) this;
        }

        public Criteria andOpponentIdIsNotNull() {
            addCriterion("opponent_id is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentIdEqualTo(String value) {
            addCriterion("opponent_id =", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdNotEqualTo(String value) {
            addCriterion("opponent_id <>", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdGreaterThan(String value) {
            addCriterion("opponent_id >", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdGreaterThanOrEqualTo(String value) {
            addCriterion("opponent_id >=", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdLessThan(String value) {
            addCriterion("opponent_id <", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdLessThanOrEqualTo(String value) {
            addCriterion("opponent_id <=", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdLike(String value) {
            addCriterion("opponent_id like", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdNotLike(String value) {
            addCriterion("opponent_id not like", value, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdIn(List<String> values) {
            addCriterion("opponent_id in", values, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdNotIn(List<String> values) {
            addCriterion("opponent_id not in", values, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdBetween(String value1, String value2) {
            addCriterion("opponent_id between", value1, value2, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentIdNotBetween(String value1, String value2) {
            addCriterion("opponent_id not between", value1, value2, "opponentId");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelIsNull() {
            addCriterion("opponent_level is null");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelIsNotNull() {
            addCriterion("opponent_level is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelEqualTo(String value) {
            addCriterion("opponent_level =", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelNotEqualTo(String value) {
            addCriterion("opponent_level <>", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelGreaterThan(String value) {
            addCriterion("opponent_level >", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelGreaterThanOrEqualTo(String value) {
            addCriterion("opponent_level >=", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelLessThan(String value) {
            addCriterion("opponent_level <", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelLessThanOrEqualTo(String value) {
            addCriterion("opponent_level <=", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelLike(String value) {
            addCriterion("opponent_level like", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelNotLike(String value) {
            addCriterion("opponent_level not like", value, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelIn(List<String> values) {
            addCriterion("opponent_level in", values, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelNotIn(List<String> values) {
            addCriterion("opponent_level not in", values, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelBetween(String value1, String value2) {
            addCriterion("opponent_level between", value1, value2, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentLevelNotBetween(String value1, String value2) {
            addCriterion("opponent_level not between", value1, value2, "opponentLevel");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreIsNull() {
            addCriterion("opponent_score is null");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreIsNotNull() {
            addCriterion("opponent_score is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreEqualTo(Integer value) {
            addCriterion("opponent_score =", value, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreNotEqualTo(Integer value) {
            addCriterion("opponent_score <>", value, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreGreaterThan(Integer value) {
            addCriterion("opponent_score >", value, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("opponent_score >=", value, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreLessThan(Integer value) {
            addCriterion("opponent_score <", value, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreLessThanOrEqualTo(Integer value) {
            addCriterion("opponent_score <=", value, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreIn(List<Integer> values) {
            addCriterion("opponent_score in", values, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreNotIn(List<Integer> values) {
            addCriterion("opponent_score not in", values, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreBetween(Integer value1, Integer value2) {
            addCriterion("opponent_score between", value1, value2, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andOpponentScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("opponent_score not between", value1, value2, "opponentScore");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andInviteIdIsNull() {
            addCriterion("invite_id is null");
            return (Criteria) this;
        }

        public Criteria andInviteIdIsNotNull() {
            addCriterion("invite_id is not null");
            return (Criteria) this;
        }

        public Criteria andInviteIdEqualTo(String value) {
            addCriterion("invite_id =", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdNotEqualTo(String value) {
            addCriterion("invite_id <>", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdGreaterThan(String value) {
            addCriterion("invite_id >", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdGreaterThanOrEqualTo(String value) {
            addCriterion("invite_id >=", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdLessThan(String value) {
            addCriterion("invite_id <", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdLessThanOrEqualTo(String value) {
            addCriterion("invite_id <=", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdLike(String value) {
            addCriterion("invite_id like", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdNotLike(String value) {
            addCriterion("invite_id not like", value, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdIn(List<String> values) {
            addCriterion("invite_id in", values, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdNotIn(List<String> values) {
            addCriterion("invite_id not in", values, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdBetween(String value1, String value2) {
            addCriterion("invite_id between", value1, value2, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteIdNotBetween(String value1, String value2) {
            addCriterion("invite_id not between", value1, value2, "inviteId");
            return (Criteria) this;
        }

        public Criteria andInviteScoreIsNull() {
            addCriterion("invite_score is null");
            return (Criteria) this;
        }

        public Criteria andInviteScoreIsNotNull() {
            addCriterion("invite_score is not null");
            return (Criteria) this;
        }

        public Criteria andInviteScoreEqualTo(Integer value) {
            addCriterion("invite_score =", value, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreNotEqualTo(Integer value) {
            addCriterion("invite_score <>", value, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreGreaterThan(Integer value) {
            addCriterion("invite_score >", value, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreGreaterThanOrEqualTo(Integer value) {
            addCriterion("invite_score >=", value, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreLessThan(Integer value) {
            addCriterion("invite_score <", value, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreLessThanOrEqualTo(Integer value) {
            addCriterion("invite_score <=", value, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreIn(List<Integer> values) {
            addCriterion("invite_score in", values, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreNotIn(List<Integer> values) {
            addCriterion("invite_score not in", values, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreBetween(Integer value1, Integer value2) {
            addCriterion("invite_score between", value1, value2, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andInviteScoreNotBetween(Integer value1, Integer value2) {
            addCriterion("invite_score not between", value1, value2, "inviteScore");
            return (Criteria) this;
        }

        public Criteria andModifierIsNull() {
            addCriterion("modifier is null");
            return (Criteria) this;
        }

        public Criteria andModifierIsNotNull() {
            addCriterion("modifier is not null");
            return (Criteria) this;
        }

        public Criteria andModifierEqualTo(String value) {
            addCriterion("modifier =", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotEqualTo(String value) {
            addCriterion("modifier <>", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThan(String value) {
            addCriterion("modifier >", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierGreaterThanOrEqualTo(String value) {
            addCriterion("modifier >=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThan(String value) {
            addCriterion("modifier <", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLessThanOrEqualTo(String value) {
            addCriterion("modifier <=", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierLike(String value) {
            addCriterion("modifier like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotLike(String value) {
            addCriterion("modifier not like", value, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierIn(List<String> values) {
            addCriterion("modifier in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotIn(List<String> values) {
            addCriterion("modifier not in", values, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierBetween(String value1, String value2) {
            addCriterion("modifier between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andModifierNotBetween(String value1, String value2) {
            addCriterion("modifier not between", value1, value2, "modifier");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNull() {
            addCriterion("gmt_create is null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIsNotNull() {
            addCriterion("gmt_create is not null");
            return (Criteria) this;
        }

        public Criteria andGmtCreateEqualTo(Date value) {
            addCriterion("gmt_create =", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotEqualTo(Date value) {
            addCriterion("gmt_create <>", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThan(Date value) {
            addCriterion("gmt_create >", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_create >=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThan(Date value) {
            addCriterion("gmt_create <", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateLessThanOrEqualTo(Date value) {
            addCriterion("gmt_create <=", value, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateIn(List<Date> values) {
            addCriterion("gmt_create in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotIn(List<Date> values) {
            addCriterion("gmt_create not in", values, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateBetween(Date value1, Date value2) {
            addCriterion("gmt_create between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtCreateNotBetween(Date value1, Date value2) {
            addCriterion("gmt_create not between", value1, value2, "gmtCreate");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNull() {
            addCriterion("gmt_modified is null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIsNotNull() {
            addCriterion("gmt_modified is not null");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedEqualTo(Date value) {
            addCriterion("gmt_modified =", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotEqualTo(Date value) {
            addCriterion("gmt_modified <>", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThan(Date value) {
            addCriterion("gmt_modified >", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedGreaterThanOrEqualTo(Date value) {
            addCriterion("gmt_modified >=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThan(Date value) {
            addCriterion("gmt_modified <", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedLessThanOrEqualTo(Date value) {
            addCriterion("gmt_modified <=", value, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedIn(List<Date> values) {
            addCriterion("gmt_modified in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotIn(List<Date> values) {
            addCriterion("gmt_modified not in", values, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedBetween(Date value1, Date value2) {
            addCriterion("gmt_modified between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andGmtModifiedNotBetween(Date value1, Date value2) {
            addCriterion("gmt_modified not between", value1, value2, "gmtModified");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Boolean value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Boolean value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Boolean value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Boolean value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Boolean value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Boolean> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Boolean> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeIsNull() {
            addCriterion("subject_code is null");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeIsNotNull() {
            addCriterion("subject_code is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeEqualTo(String value) {
            addCriterion("subject_code =", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotEqualTo(String value) {
            addCriterion("subject_code <>", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeGreaterThan(String value) {
            addCriterion("subject_code >", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeGreaterThanOrEqualTo(String value) {
            addCriterion("subject_code >=", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeLessThan(String value) {
            addCriterion("subject_code <", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeLessThanOrEqualTo(String value) {
            addCriterion("subject_code <=", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeLike(String value) {
            addCriterion("subject_code like", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotLike(String value) {
            addCriterion("subject_code not like", value, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeIn(List<String> values) {
            addCriterion("subject_code in", values, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotIn(List<String> values) {
            addCriterion("subject_code not in", values, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeBetween(String value1, String value2) {
            addCriterion("subject_code between", value1, value2, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andSubjectCodeNotBetween(String value1, String value2) {
            addCriterion("subject_code not between", value1, value2, "subjectCode");
            return (Criteria) this;
        }

        public Criteria andQuestionIdIsNull() {
            addCriterion("question_id is null");
            return (Criteria) this;
        }

        public Criteria andQuestionIdIsNotNull() {
            addCriterion("question_id is not null");
            return (Criteria) this;
        }

        public Criteria andQuestionIdEqualTo(String value) {
            addCriterion("question_id =", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdNotEqualTo(String value) {
            addCriterion("question_id <>", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdGreaterThan(String value) {
            addCriterion("question_id >", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdGreaterThanOrEqualTo(String value) {
            addCriterion("question_id >=", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdLessThan(String value) {
            addCriterion("question_id <", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdLessThanOrEqualTo(String value) {
            addCriterion("question_id <=", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdLike(String value) {
            addCriterion("question_id like", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdNotLike(String value) {
            addCriterion("question_id not like", value, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdIn(List<String> values) {
            addCriterion("question_id in", values, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdNotIn(List<String> values) {
            addCriterion("question_id not in", values, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdBetween(String value1, String value2) {
            addCriterion("question_id between", value1, value2, "questionId");
            return (Criteria) this;
        }

        public Criteria andQuestionIdNotBetween(String value1, String value2) {
            addCriterion("question_id not between", value1, value2, "questionId");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeIsNull() {
            addCriterion("invite_start_time is null");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeIsNotNull() {
            addCriterion("invite_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeEqualTo(Date value) {
            addCriterion("invite_start_time =", value, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeNotEqualTo(Date value) {
            addCriterion("invite_start_time <>", value, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeGreaterThan(Date value) {
            addCriterion("invite_start_time >", value, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("invite_start_time >=", value, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeLessThan(Date value) {
            addCriterion("invite_start_time <", value, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("invite_start_time <=", value, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeIn(List<Date> values) {
            addCriterion("invite_start_time in", values, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeNotIn(List<Date> values) {
            addCriterion("invite_start_time not in", values, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeBetween(Date value1, Date value2) {
            addCriterion("invite_start_time between", value1, value2, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("invite_start_time not between", value1, value2, "inviteStartTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeIsNull() {
            addCriterion("invite_end_time is null");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeIsNotNull() {
            addCriterion("invite_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeEqualTo(Date value) {
            addCriterion("invite_end_time =", value, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeNotEqualTo(Date value) {
            addCriterion("invite_end_time <>", value, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeGreaterThan(Date value) {
            addCriterion("invite_end_time >", value, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("invite_end_time >=", value, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeLessThan(Date value) {
            addCriterion("invite_end_time <", value, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("invite_end_time <=", value, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeIn(List<Date> values) {
            addCriterion("invite_end_time in", values, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeNotIn(List<Date> values) {
            addCriterion("invite_end_time not in", values, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeBetween(Date value1, Date value2) {
            addCriterion("invite_end_time between", value1, value2, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andInviteEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("invite_end_time not between", value1, value2, "inviteEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeIsNull() {
            addCriterion("opponent_start_time is null");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeIsNotNull() {
            addCriterion("opponent_start_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeEqualTo(Date value) {
            addCriterion("opponent_start_time =", value, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeNotEqualTo(Date value) {
            addCriterion("opponent_start_time <>", value, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeGreaterThan(Date value) {
            addCriterion("opponent_start_time >", value, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("opponent_start_time >=", value, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeLessThan(Date value) {
            addCriterion("opponent_start_time <", value, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("opponent_start_time <=", value, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeIn(List<Date> values) {
            addCriterion("opponent_start_time in", values, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeNotIn(List<Date> values) {
            addCriterion("opponent_start_time not in", values, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeBetween(Date value1, Date value2) {
            addCriterion("opponent_start_time between", value1, value2, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("opponent_start_time not between", value1, value2, "opponentStartTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeIsNull() {
            addCriterion("opponent_end_time is null");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeIsNotNull() {
            addCriterion("opponent_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeEqualTo(Date value) {
            addCriterion("opponent_end_time =", value, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeNotEqualTo(Date value) {
            addCriterion("opponent_end_time <>", value, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeGreaterThan(Date value) {
            addCriterion("opponent_end_time >", value, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("opponent_end_time >=", value, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeLessThan(Date value) {
            addCriterion("opponent_end_time <", value, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("opponent_end_time <=", value, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeIn(List<Date> values) {
            addCriterion("opponent_end_time in", values, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeNotIn(List<Date> values) {
            addCriterion("opponent_end_time not in", values, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeBetween(Date value1, Date value2) {
            addCriterion("opponent_end_time between", value1, value2, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOpponentEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("opponent_end_time not between", value1, value2, "opponentEndTime");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNull() {
            addCriterion("org_code is null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIsNotNull() {
            addCriterion("org_code is not null");
            return (Criteria) this;
        }

        public Criteria andOrgCodeEqualTo(String value) {
            addCriterion("org_code =", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotEqualTo(String value) {
            addCriterion("org_code <>", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThan(String value) {
            addCriterion("org_code >", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeGreaterThanOrEqualTo(String value) {
            addCriterion("org_code >=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThan(String value) {
            addCriterion("org_code <", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLessThanOrEqualTo(String value) {
            addCriterion("org_code <=", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeLike(String value) {
            addCriterion("org_code like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotLike(String value) {
            addCriterion("org_code not like", value, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeIn(List<String> values) {
            addCriterion("org_code in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotIn(List<String> values) {
            addCriterion("org_code not in", values, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeBetween(String value1, String value2) {
            addCriterion("org_code between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andOrgCodeNotBetween(String value1, String value2) {
            addCriterion("org_code not between", value1, value2, "orgCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeIsNull() {
            addCriterion("class_code is null");
            return (Criteria) this;
        }

        public Criteria andClassCodeIsNotNull() {
            addCriterion("class_code is not null");
            return (Criteria) this;
        }

        public Criteria andClassCodeEqualTo(String value) {
            addCriterion("class_code =", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeNotEqualTo(String value) {
            addCriterion("class_code <>", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeGreaterThan(String value) {
            addCriterion("class_code >", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeGreaterThanOrEqualTo(String value) {
            addCriterion("class_code >=", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeLessThan(String value) {
            addCriterion("class_code <", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeLessThanOrEqualTo(String value) {
            addCriterion("class_code <=", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeLike(String value) {
            addCriterion("class_code like", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeNotLike(String value) {
            addCriterion("class_code not like", value, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeIn(List<String> values) {
            addCriterion("class_code in", values, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeNotIn(List<String> values) {
            addCriterion("class_code not in", values, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeBetween(String value1, String value2) {
            addCriterion("class_code between", value1, value2, "classCode");
            return (Criteria) this;
        }

        public Criteria andClassCodeNotBetween(String value1, String value2) {
            addCriterion("class_code not between", value1, value2, "classCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeIsNull() {
            addCriterion("grade_code is null");
            return (Criteria) this;
        }

        public Criteria andGradeCodeIsNotNull() {
            addCriterion("grade_code is not null");
            return (Criteria) this;
        }

        public Criteria andGradeCodeEqualTo(String value) {
            addCriterion("grade_code =", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeNotEqualTo(String value) {
            addCriterion("grade_code <>", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeGreaterThan(String value) {
            addCriterion("grade_code >", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeGreaterThanOrEqualTo(String value) {
            addCriterion("grade_code >=", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeLessThan(String value) {
            addCriterion("grade_code <", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeLessThanOrEqualTo(String value) {
            addCriterion("grade_code <=", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeLike(String value) {
            addCriterion("grade_code like", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeNotLike(String value) {
            addCriterion("grade_code not like", value, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeIn(List<String> values) {
            addCriterion("grade_code in", values, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeNotIn(List<String> values) {
            addCriterion("grade_code not in", values, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeBetween(String value1, String value2) {
            addCriterion("grade_code between", value1, value2, "gradeCode");
            return (Criteria) this;
        }

        public Criteria andGradeCodeNotBetween(String value1, String value2) {
            addCriterion("grade_code not between", value1, value2, "gradeCode");
            return (Criteria) this;
        }
    }

    /**
     * heroland_competition_record
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * heroland_competition_record 2020-08-02
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
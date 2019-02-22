package com.nmefc.hpcmmp.entity.management;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ActionExample() {
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

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Integer value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Integer value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Integer value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Integer> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Integer> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNull() {
            addCriterion("area_name is null");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNotNull() {
            addCriterion("area_name is not null");
            return (Criteria) this;
        }

        public Criteria andAreaNameEqualTo(String value) {
            addCriterion("area_name =", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotEqualTo(String value) {
            addCriterion("area_name <>", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThan(String value) {
            addCriterion("area_name >", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("area_name >=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThan(String value) {
            addCriterion("area_name <", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThanOrEqualTo(String value) {
            addCriterion("area_name <=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLike(String value) {
            addCriterion("area_name like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotLike(String value) {
            addCriterion("area_name not like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameIn(List<String> values) {
            addCriterion("area_name in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotIn(List<String> values) {
            addCriterion("area_name not in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameBetween(String value1, String value2) {
            addCriterion("area_name between", value1, value2, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotBetween(String value1, String value2) {
            addCriterion("area_name not between", value1, value2, "areaName");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNull() {
            addCriterion("method_name is null");
            return (Criteria) this;
        }

        public Criteria andMethodNameIsNotNull() {
            addCriterion("method_name is not null");
            return (Criteria) this;
        }

        public Criteria andMethodNameEqualTo(String value) {
            addCriterion("method_name =", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotEqualTo(String value) {
            addCriterion("method_name <>", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThan(String value) {
            addCriterion("method_name >", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameGreaterThanOrEqualTo(String value) {
            addCriterion("method_name >=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThan(String value) {
            addCriterion("method_name <", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLessThanOrEqualTo(String value) {
            addCriterion("method_name <=", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameLike(String value) {
            addCriterion("method_name like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotLike(String value) {
            addCriterion("method_name not like", value, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameIn(List<String> values) {
            addCriterion("method_name in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotIn(List<String> values) {
            addCriterion("method_name not in", values, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameBetween(String value1, String value2) {
            addCriterion("method_name between", value1, value2, "methodName");
            return (Criteria) this;
        }

        public Criteria andMethodNameNotBetween(String value1, String value2) {
            addCriterion("method_name not between", value1, value2, "methodName");
            return (Criteria) this;
        }

        public Criteria andControllerNameIsNull() {
            addCriterion("controller_name is null");
            return (Criteria) this;
        }

        public Criteria andControllerNameIsNotNull() {
            addCriterion("controller_name is not null");
            return (Criteria) this;
        }

        public Criteria andControllerNameEqualTo(String value) {
            addCriterion("controller_name =", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameNotEqualTo(String value) {
            addCriterion("controller_name <>", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameGreaterThan(String value) {
            addCriterion("controller_name >", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameGreaterThanOrEqualTo(String value) {
            addCriterion("controller_name >=", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameLessThan(String value) {
            addCriterion("controller_name <", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameLessThanOrEqualTo(String value) {
            addCriterion("controller_name <=", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameLike(String value) {
            addCriterion("controller_name like", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameNotLike(String value) {
            addCriterion("controller_name not like", value, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameIn(List<String> values) {
            addCriterion("controller_name in", values, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameNotIn(List<String> values) {
            addCriterion("controller_name not in", values, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameBetween(String value1, String value2) {
            addCriterion("controller_name between", value1, value2, "controllerName");
            return (Criteria) this;
        }

        public Criteria andControllerNameNotBetween(String value1, String value2) {
            addCriterion("controller_name not between", value1, value2, "controllerName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameIsNull() {
            addCriterion("js_function_name is null");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameIsNotNull() {
            addCriterion("js_function_name is not null");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameEqualTo(String value) {
            addCriterion("js_function_name =", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameNotEqualTo(String value) {
            addCriterion("js_function_name <>", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameGreaterThan(String value) {
            addCriterion("js_function_name >", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameGreaterThanOrEqualTo(String value) {
            addCriterion("js_function_name >=", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameLessThan(String value) {
            addCriterion("js_function_name <", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameLessThanOrEqualTo(String value) {
            addCriterion("js_function_name <=", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameLike(String value) {
            addCriterion("js_function_name like", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameNotLike(String value) {
            addCriterion("js_function_name not like", value, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameIn(List<String> values) {
            addCriterion("js_function_name in", values, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameNotIn(List<String> values) {
            addCriterion("js_function_name not in", values, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameBetween(String value1, String value2) {
            addCriterion("js_function_name between", value1, value2, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andJsFunctionNameNotBetween(String value1, String value2) {
            addCriterion("js_function_name not between", value1, value2, "jsFunctionName");
            return (Criteria) this;
        }

        public Criteria andTypeEnumIsNull() {
            addCriterion("type_enum is null");
            return (Criteria) this;
        }

        public Criteria andTypeEnumIsNotNull() {
            addCriterion("type_enum is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEnumEqualTo(Integer value) {
            addCriterion("type_enum =", value, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumNotEqualTo(Integer value) {
            addCriterion("type_enum <>", value, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumGreaterThan(Integer value) {
            addCriterion("type_enum >", value, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_enum >=", value, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumLessThan(Integer value) {
            addCriterion("type_enum <", value, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumLessThanOrEqualTo(Integer value) {
            addCriterion("type_enum <=", value, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumIn(List<Integer> values) {
            addCriterion("type_enum in", values, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumNotIn(List<Integer> values) {
            addCriterion("type_enum not in", values, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumBetween(Integer value1, Integer value2) {
            addCriterion("type_enum between", value1, value2, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andTypeEnumNotBetween(Integer value1, Integer value2) {
            addCriterion("type_enum not between", value1, value2, "typeEnum");
            return (Criteria) this;
        }

        public Criteria andMenuIconIsNull() {
            addCriterion("menu_icon is null");
            return (Criteria) this;
        }

        public Criteria andMenuIconIsNotNull() {
            addCriterion("menu_icon is not null");
            return (Criteria) this;
        }

        public Criteria andMenuIconEqualTo(String value) {
            addCriterion("menu_icon =", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotEqualTo(String value) {
            addCriterion("menu_icon <>", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconGreaterThan(String value) {
            addCriterion("menu_icon >", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconGreaterThanOrEqualTo(String value) {
            addCriterion("menu_icon >=", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLessThan(String value) {
            addCriterion("menu_icon <", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLessThanOrEqualTo(String value) {
            addCriterion("menu_icon <=", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconLike(String value) {
            addCriterion("menu_icon like", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotLike(String value) {
            addCriterion("menu_icon not like", value, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconIn(List<String> values) {
            addCriterion("menu_icon in", values, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotIn(List<String> values) {
            addCriterion("menu_icon not in", values, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconBetween(String value1, String value2) {
            addCriterion("menu_icon between", value1, value2, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andMenuIconNotBetween(String value1, String value2) {
            addCriterion("menu_icon not between", value1, value2, "menuIcon");
            return (Criteria) this;
        }

        public Criteria andIconWidthIsNull() {
            addCriterion("icon_width is null");
            return (Criteria) this;
        }

        public Criteria andIconWidthIsNotNull() {
            addCriterion("icon_width is not null");
            return (Criteria) this;
        }

        public Criteria andIconWidthEqualTo(Integer value) {
            addCriterion("icon_width =", value, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthNotEqualTo(Integer value) {
            addCriterion("icon_width <>", value, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthGreaterThan(Integer value) {
            addCriterion("icon_width >", value, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthGreaterThanOrEqualTo(Integer value) {
            addCriterion("icon_width >=", value, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthLessThan(Integer value) {
            addCriterion("icon_width <", value, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthLessThanOrEqualTo(Integer value) {
            addCriterion("icon_width <=", value, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthIn(List<Integer> values) {
            addCriterion("icon_width in", values, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthNotIn(List<Integer> values) {
            addCriterion("icon_width not in", values, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthBetween(Integer value1, Integer value2) {
            addCriterion("icon_width between", value1, value2, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconWidthNotBetween(Integer value1, Integer value2) {
            addCriterion("icon_width not between", value1, value2, "iconWidth");
            return (Criteria) this;
        }

        public Criteria andIconHeightIsNull() {
            addCriterion("icon_height is null");
            return (Criteria) this;
        }

        public Criteria andIconHeightIsNotNull() {
            addCriterion("icon_height is not null");
            return (Criteria) this;
        }

        public Criteria andIconHeightEqualTo(Integer value) {
            addCriterion("icon_height =", value, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightNotEqualTo(Integer value) {
            addCriterion("icon_height <>", value, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightGreaterThan(Integer value) {
            addCriterion("icon_height >", value, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightGreaterThanOrEqualTo(Integer value) {
            addCriterion("icon_height >=", value, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightLessThan(Integer value) {
            addCriterion("icon_height <", value, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightLessThanOrEqualTo(Integer value) {
            addCriterion("icon_height <=", value, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightIn(List<Integer> values) {
            addCriterion("icon_height in", values, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightNotIn(List<Integer> values) {
            addCriterion("icon_height not in", values, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightBetween(Integer value1, Integer value2) {
            addCriterion("icon_height between", value1, value2, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconHeightNotBetween(Integer value1, Integer value2) {
            addCriterion("icon_height not between", value1, value2, "iconHeight");
            return (Criteria) this;
        }

        public Criteria andIconClsIsNull() {
            addCriterion("icon_cls is null");
            return (Criteria) this;
        }

        public Criteria andIconClsIsNotNull() {
            addCriterion("icon_cls is not null");
            return (Criteria) this;
        }

        public Criteria andIconClsEqualTo(String value) {
            addCriterion("icon_cls =", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsNotEqualTo(String value) {
            addCriterion("icon_cls <>", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsGreaterThan(String value) {
            addCriterion("icon_cls >", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsGreaterThanOrEqualTo(String value) {
            addCriterion("icon_cls >=", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsLessThan(String value) {
            addCriterion("icon_cls <", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsLessThanOrEqualTo(String value) {
            addCriterion("icon_cls <=", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsLike(String value) {
            addCriterion("icon_cls like", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsNotLike(String value) {
            addCriterion("icon_cls not like", value, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsIn(List<String> values) {
            addCriterion("icon_cls in", values, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsNotIn(List<String> values) {
            addCriterion("icon_cls not in", values, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsBetween(String value1, String value2) {
            addCriterion("icon_cls between", value1, value2, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClsNotBetween(String value1, String value2) {
            addCriterion("icon_cls not between", value1, value2, "iconCls");
            return (Criteria) this;
        }

        public Criteria andIconClassNameIsNull() {
            addCriterion("icon_class_name is null");
            return (Criteria) this;
        }

        public Criteria andIconClassNameIsNotNull() {
            addCriterion("icon_class_name is not null");
            return (Criteria) this;
        }

        public Criteria andIconClassNameEqualTo(String value) {
            addCriterion("icon_class_name =", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameNotEqualTo(String value) {
            addCriterion("icon_class_name <>", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameGreaterThan(String value) {
            addCriterion("icon_class_name >", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameGreaterThanOrEqualTo(String value) {
            addCriterion("icon_class_name >=", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameLessThan(String value) {
            addCriterion("icon_class_name <", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameLessThanOrEqualTo(String value) {
            addCriterion("icon_class_name <=", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameLike(String value) {
            addCriterion("icon_class_name like", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameNotLike(String value) {
            addCriterion("icon_class_name not like", value, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameIn(List<String> values) {
            addCriterion("icon_class_name in", values, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameNotIn(List<String> values) {
            addCriterion("icon_class_name not in", values, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameBetween(String value1, String value2) {
            addCriterion("icon_class_name between", value1, value2, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIconClassNameNotBetween(String value1, String value2) {
            addCriterion("icon_class_name not between", value1, value2, "iconClassName");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNull() {
            addCriterion("is_show is null");
            return (Criteria) this;
        }

        public Criteria andIsShowIsNotNull() {
            addCriterion("is_show is not null");
            return (Criteria) this;
        }

        public Criteria andIsShowEqualTo(Boolean value) {
            addCriterion("is_show =", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotEqualTo(Boolean value) {
            addCriterion("is_show <>", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThan(Boolean value) {
            addCriterion("is_show >", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_show >=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThan(Boolean value) {
            addCriterion("is_show <", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowLessThanOrEqualTo(Boolean value) {
            addCriterion("is_show <=", value, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowIn(List<Boolean> values) {
            addCriterion("is_show in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotIn(List<Boolean> values) {
            addCriterion("is_show not in", values, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowBetween(Boolean value1, Boolean value2) {
            addCriterion("is_show between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andIsShowNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_show not between", value1, value2, "isShow");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumIsNull() {
            addCriterion("method_type_enum is null");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumIsNotNull() {
            addCriterion("method_type_enum is not null");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumEqualTo(Integer value) {
            addCriterion("method_type_enum =", value, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumNotEqualTo(Integer value) {
            addCriterion("method_type_enum <>", value, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumGreaterThan(Integer value) {
            addCriterion("method_type_enum >", value, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("method_type_enum >=", value, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumLessThan(Integer value) {
            addCriterion("method_type_enum <", value, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumLessThanOrEqualTo(Integer value) {
            addCriterion("method_type_enum <=", value, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumIn(List<Integer> values) {
            addCriterion("method_type_enum in", values, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumNotIn(List<Integer> values) {
            addCriterion("method_type_enum not in", values, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumBetween(Integer value1, Integer value2) {
            addCriterion("method_type_enum between", value1, value2, "methodTypeEnum");
            return (Criteria) this;
        }

        public Criteria andMethodTypeEnumNotBetween(Integer value1, Integer value2) {
            addCriterion("method_type_enum not between", value1, value2, "methodTypeEnum");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

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
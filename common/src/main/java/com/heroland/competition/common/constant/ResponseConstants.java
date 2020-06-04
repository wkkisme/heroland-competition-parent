package com.heroland.competition.common.constant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;

public enum ResponseConstants {
    /**
     * 全局成功失败
     */
    SUCCESS("200","成功"),


    EMPTY_PARAMS("400","参数为空"),

    /**
     *  100000 通用
     */
    SAVE_OR_UPDATE_ERROR("100000", "更新失败"),
    PARAMS_ERROR("100001", "参数错误"),
    CANCEL_ERROR("100002", "取消失败"),
    SYSTEM_ERROR("100003", "接口调用失败"),
    SETTLEMENT_INFO_NOT_EXSIT("100004", "结算信息不存在"),
    PAY_FAILS("100005", "支付失败"),
    SETTLEMENT_INFO_NOT_SAME("100006", "结算信息不一致"),
    FEE_NOT_SAME("100007", "结算费用不一致"),
    ORDER_INFO_NOT_EXSIT("100007", "订单明细信息不存在"),
    FEE_NOT_SETTEMENT("100007", "存在未结算的费用"),
    ZLF_FEE_SETTEMENT("100007", "诊疗费无法结算"),
    OPT_REGISTER_NOT_EXSIT("100008", "挂号记录不存在"),
    OPT_REGISTER_TOO_MANY("100009", "挂号记录存在多条"),
    ORG_ORDER_NO_NOT_EXSIT("100009", "医疗机构订单号不存在"),
    ORG_ORDER_INFO_NOT_EXSIT("100009", "医疗机构订单不存在"),
    CAL_DATA_ERROR("100010", "计算数值错误"),
    SAVE_CAL_DATA_ERROR("100010", "保存计算数值错误"),
    DATA_SYNC_FAILED("100011", "数据同步失败"),
    /**
     * 20****病人 医生 科室
     */
    MED_DOCTOR_NOT_EXIST("200000", "医生信息不存在"),
    MED_DEPARTMENT_NOT_EXIST("200001", "科室信息不存在"),
    MED_PATINT_NOT_EXIST("200002", "病人信息不存在"),
    MED_PATINT_IS_EXIST("200003", "病人信息已存在"),
    MED_USER_NOT_EXIST("200004", "用户信息不存在"),
    MED_USER_CAN_NOT_DELETE("200003", "不能删除本人"),
    MED_DEPARTMENT_TEMP_NOT_EXIST("200004", "模板不存在"),
    MED_DEPARTMENT_TEMP_CAN_NOT_DELETE("200005", "科室模板不能删除"),
    MED_DEPARTMENT_TEMP_TOO_MANY("200006", "个人模板最多五条"),

    /**
     * 31**** 为医疗机构
     */
    MED_ORG_NOT_EXIST("310000", "医疗机构不存在"),
    MED_ORG_MSG_NOT_FULL("310001", "医疗机构信息不完整: appAuthToken,MedorgName,AuthAppId,MedorgAddress,HospitalCode,请补全!"),
    MED_ORG_CODE_IS_NULL("310002", "当前机构无医疗机构编码, 请先修改"),
    MED_ORG_BRANCH_NOT_EXIST("310003", "院区不存在"),

    /**
     * 32****为医疗机构部门
     */
    MED_ORG_DEPARTMENT_NOT_EXIST("320000", "医疗机构部门不存在,请核对该部门是否存在"),




    /**
     * 34****系统资源
     */
    SYSTEM_RESOURCE_NOT_JSON("340000", "配置文件不是标准json格式,请核对"),
    SYSTEM_RESOURCE_EXIST("340001", "ResourceCode&&AppCode联合唯一索引已存在,请修改"),


    /**
     * 4***** 为用户相关操作
     */
    USER_NOT_EXIST("400000", "账号或密码错误"),
    USER_FREEZE("400001", "用户已冻结,无法登录"),
    CAPTCHA_ID_IS_NOT_NULL("400002", "CAPTCHA为空,请求异常"),
    CAPTCHA_ERR("400003", "验证码错误,请输入正确的验证码"),
    CAPTCHA_TIMEOUT("400004", "验证码过期,请刷新验证码重新输入"),
    NOT_PERMISSION("400005", "警告!你没有操作权限!"),
    PARAMS_ILLEGAL("400006", "警告!参数非法!请勿再次尝试"),
    USERNAME_EXIST("400007", "用户名已存在,请更换"),
    USER_LOGIN_EXPIRE("400008", "登录信息过期,请重新登录!"),
    USER_REMOVED("400009", "登录信息已不存在!"),
    USER_EXSIT("400010", "用户已存在"),
    USER_NOT_EXSIT("400010", "用户不存在"),
    RECORD_NOT_EXSIT("400011", "记录不存在"),
    ROLE_NOT_EXSIT("400011", "角色信息不存在"),

    NO_PRESCRIPTION("400050", "无法查询到相关处方"),
    NO_PRESCRIPTION_ITEMS("400051", "不存在处方药品明细信息！"),

    /**
     * server 服务器内部报错
     */
    SERVER_ERR("500000", "服务器内部错误");




    private final String retCode;
    private final String msg;


    ResponseConstants(String retCode, String msg) {
        this.retCode = retCode;
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public String getMsg() {
        return msg;
    }

    private static SerializeConfig config = new SerializeConfig();

    @Override
    public String toString() {
        config.configEnumAsJavaBean(ResponseConstants.class);
        return JSON.toJSONString(this,config);
    }
}

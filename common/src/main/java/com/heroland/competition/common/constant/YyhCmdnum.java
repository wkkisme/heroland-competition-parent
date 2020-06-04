package com.heroland.competition.common.constant;

public enum YyhCmdnum {
    RUN_YEAR_TASK("runYearTask","执行批量任务(上传历史数据,某一年)"),
    RUN_MONTH_TASK("runMonthTask","执行批量任务(上传历史数据，某一个月)"),
    RUN_TASK("runTask","执行任务"),
    RUN_CRON_TASK("cronTask","执行定时任务"),
    TEST_SQL("testSql","测试sql语句");


    private String code;
    private String desc;

    private YyhCmdnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

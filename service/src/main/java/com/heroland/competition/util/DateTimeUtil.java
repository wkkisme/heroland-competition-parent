package com.heroland.competition.util;

import com.heroland.competition.competition.common.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
public class DateTimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    private static int FIRST_DATE_OF_WEEK = Calendar.SUNDAY;
    public static final int BEFORE_START_DATE = -2;// 一个日期早于一个日期区间
    public static final int EQUAL_START_DATE = -1;// 一个日期等于一个日期区间的开始日期
    public static final int BETWEEN_TOW_DATE = 0;// 一个日期在一个日期区间之内
    public static final int EQUAL_END_DATE = 1;// 一个日期等于一个日期区间的结束日期
    public static final int AFTER_END_DATE = 2;// 一个日期晚于一个日期区间
    public static final int TREE_DATE_EQUAL = 3;// 三个日期相等

    // 日期格式
    public static final String ACCOUNT_DATETIME_PATTERN = "yy-MM-dd HH:mm";
    public static final String NORMAL_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String NORMAL_DATETIME_PATTERN_EXT = "MM-dd HH:mm";
    public static final String NORMAL_TIME_PATTERN = "HH:mm:ss";
    public static final String NORMAL_TIME_MINUTE = "HH:mm";
    public static final String NORMAL_DATETIME_MILLI_PATTERN = "yyyyMMddHHmmssSSS";
    public static final String LL_DATETIME_PATTERN = "yyyyMMddHHmmss";
    public static final String NORMAL_DATE_PATTERN = "yyyy-MM-dd";
    public static final String YEAR_MONTH_PATTERN = "yyyy-MM";
    public static final String MONTH_PATTERN = "MM";
    public static final String DAILY_DATE_PATTERN = "yyyyMMdd";
    public static final String CHINESE_DATE_PATTERN = "MM月dd日";
    public static final String CHINESE_YEAR_DATE_PATTERN = "yyyy年MM月dd日";
    public static final String CHINESE_YEAR_DATE_PATTERN_MINUTE = "yyyy年MM月dd日 HH:mm";
    public static final String NORMAL_YEAR_YY = "yyyy";


    public static String getStrFormatday(int dayparams){
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            int nowDay = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DATE, nowDay + dayparams);

            SimpleDateFormat myFormat = new SimpleDateFormat(NORMAL_DATE_PATTERN);

            return myFormat.format(cal.getTime());
        }catch (Exception e){
            logger.error("日期格式化错误:{}",e.getMessage());
        }
        return null;
    }

    /**
     * 不是闰年
     *
     * @param year
     * @return
     */
    public static boolean isNotLeapyear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)    //闰年
        {
            return false;
        } else    //不是闰年
        {
            return true;
        }
    }

    /**
     * 年
     *
     * @param calendar
     * @return
     */
    public static int getYear(Calendar calendar) {
        return calendar.get(calendar.YEAR);
    }

    /**
     * 月
     *
     * @param calendar
     * @return
     */
    public static int getMonth(Calendar calendar) {
        return calendar.get(calendar.MONTH) + 1;
    }

    /**
     * 日
     *
     * @param calendar
     * @return
     */
    public static int getDay(Calendar calendar) {
        return calendar.get(calendar.DATE);
    }

    /**
     * 验证日期格式
     *
     * @param dateStr
     * @return
     */
    public static boolean verifyDateStrFail(String dateStr) {
        try {
            parse(dateStr, NORMAL_DATE_PATTERN);
        } catch (Exception e) {
            logger.error("日期转换有误");
            return true;
        }
        return false;
    }



    /**
     * 获取一周开始时间
     *
     * @param calendar
     * @return
     */
    public static String getMonday(Calendar calendar) {
        calendar.set(calendar.DAY_OF_WEEK, calendar.MONDAY);
        return format(calendar.getTime(), NORMAL_DATE_PATTERN);
    }

    /**
     * 获取上一周开始时间
     *
     * @param calendar
     * @return
     */
    public static String getLastMonday(Calendar calendar) {
        calendar.set(calendar.DAY_OF_WEEK, calendar.MONDAY);
        calendar.set(Calendar.DATE, calendar.get(calendar.DATE) - 7);
        return format(calendar.getTime(), NORMAL_DATE_PATTERN);
    }

    /**
     * 获取一周结束时间
     *
     * @param calendar
     * @return
     */
    public static String getSunday(Calendar calendar) {
        calendar.set(Calendar.DATE, calendar.get(calendar.DATE) + 6);
        return format(calendar.getTime(), NORMAL_DATE_PATTERN);
    }

    /**
     * 获取下一周结束时间
     *
     * @param calendar
     * @return
     */
    public static String getNextSunday(Calendar calendar) {
        calendar.set(Calendar.DATE, calendar.get(calendar.DATE) + 20);
        return format(calendar.getTime(), NORMAL_DATE_PATTERN);
    }


    /**
     * 获取当前月的第一天
     *
     * @return
     */
    public static String getMonthFirstDay(Date date) {
        // 当前月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//		calendar.add(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return format(calendar.getTime(), NORMAL_DATE_PATTERN);
    }

    /**
     * 获取当前月的最后天
     *
     * @return
     */
    public static String getMonthLastDay(Date date) {
        // 下个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        return format(calendar.getTime(), NORMAL_DATE_PATTERN);
    }

    /**
     * 增加月份
     *
     * @param date
     * @param num
     * @return
     */
    public static Date addMonth(Date date, int num) {
        // 下个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return calendar.getTime();
    }

    /**
     * 增加年份
     *
     * @param date
     * @param num
     * @return
     */
    public static Date addYear(Date date, int num) {
        // 下一年
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, num);
        return calendar.getTime();
    }

    /**
     * date to string
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        try {
            if(date==null){
                return null;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }catch (Exception e){
            logger.info("错误日志：{}-{}",e.getMessage(),date);
            return null;
        }
    }

    /**
     * string to date
     *
     * @param date
     * @param pattern
     * @return
     */
    public static Date parse(String date, String pattern) {
        if(StringUtils.isBlank(date)){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(date);
            return d;
        } catch (ParseException e) {
            //throw new RuntimeException("日期转换错误", e);
            logger.error("日期转换错误:{}",date);
            return null;
        }

    }

    /**
     * 起止日期间的天数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int differentDays(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);
        int startDay = startCal.get(Calendar.DAY_OF_YEAR);
        int endDay = endCal.get(Calendar.DAY_OF_YEAR);

        int startYear = startCal.get(Calendar.YEAR);
        int endYear = endCal.get(Calendar.YEAR);
        if (startYear != endYear)   //不同年
        {
            int timeDistance = 0;
            for (int i = startYear; i < endYear; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (endDay - startDay);
        } else    //同一年
        {
            return endDay - startDay;
        }
    }

    /**
     * 年份比较
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int compareYears(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        int startYear = startCal.get(Calendar.YEAR);
        int endYear = endCal.get(Calendar.YEAR);

        return endYear - startYear;
    }

    /**
     * 获取该日期前后几天的日期
     *
     * @param date
     * @param beforeDays 负数表示前几天，正数表示后几天
     * @return
     */
    public static Date getBeforeDayDate(Date date, int beforeDays) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + beforeDays);
        calendar.add(Calendar.DATE, beforeDays);
        return calendar.getTime();
    }

    /**
     * 获取该日期前后几秒的日期
     *
     * @param date
     * @param beforeSeconds 负数表示前几秒，正数表示后几秒
     * @return
     */
    public static Date getBeforeSecondDate(Date date, int beforeSeconds) {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);

        calendar.add(Calendar.SECOND, beforeSeconds);

        return calendar.getTime();
    }

    /**
     * 日期的天数差
     *
     * @param beginDate
     * @param endDate
     * @return
     */
    public static int differentDaysByMillisecond(Date beginDate, Date endDate) {
        int days = (int) ((endDate.getTime() - beginDate.getTime()) / (1000 * 3600 * 24));
        return days;
    }

    /**
     * add(Calendar.DAY_OF_MONTH, -5)
     *
     * @param date
     * @param calendorField
     * @param amount
     * @return
     */
    public static Date add(Date date, int calendorField, int amount) {
        try {
            Calendar cal = Calendar.getInstance();

            cal.setTime(date);

            cal.add(calendorField, amount);

            return cal.getTime();
        }catch (Exception e){
            return null;
        }
    }

    /**
     * @return Calendar.SUNDAY <br/>
     * Calendar.MONDAY <br/>
     * Calendar.TUESDAY <br/>
     * Calendar.WEDNESDAY <br/>
     * Calendar.THURSDAY <br/>
     * Calendar.FRIDAY <br/>
     * Calendar.SATURDAY <br/>
     */
    public static int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return 7;
            case 2:
                return 1;
            case 3:
                return 2;
            case 4:
                return 3;
            case 5:
                return 4;
            case 6:
                return 5;
            default:
                return 6;
        }
    }

    /**
     * @return Calendar.SUNDAY <br/>
     * Calendar.MONDAY <br/>
     * Calendar.TUESDAY <br/>
     * Calendar.WEDNESDAY <br/>
     * Calendar.THURSDAY <br/>
     * Calendar.FRIDAY <br/>
     * Calendar.SATURDAY <br/>
     */
    public static int getDayOfMouth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算两个日期间相差的天数
     *
     * @param date
     * @param compareDate
     * @return
     * @throws ParseException
     */
    public static long compareTo(Date date, Date compareDate) {
        // 去掉时分秒
        date = parse(format(date, DAILY_DATE_PATTERN), DAILY_DATE_PATTERN);
        compareDate = parse(format(compareDate, DAILY_DATE_PATTERN), DAILY_DATE_PATTERN);

        long a = (date.getTime() - compareDate.getTime())
                / (1000 * 60 * 60 * 24);
        return a;
    }

    /**
     * 判断是否为一周的最后一天(目前配置的是周日为一周的第一天)
     *
     * @param date
     * @return
     */
    public static boolean isEndOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK);
        if (weekDay == FIRST_DATE_OF_WEEK) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否为月末
     *
     * @param nowDate 日期（需要验证的日期）
     * @return boolean true 表示是月末 false 表示不为月末
     */
    public static boolean isMonthEnd(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否为季末
     *
     * @param nowDate 日期（需要验证的日期）
     * @return boolean true 表示是季末 false 表示不是季末
     */
    public static boolean isQuarterEnd(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1
                && (month == Calendar.MARCH || month == Calendar.JUNE
                || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER)) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否为季出
     *
     * @param nowDate 日期（需要验证的日期）
     * @return boolean true 表示是季初 false 表示不是季初
     */
    public static boolean isQuarterBegin(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1
                && (month == Calendar.JANUARY || month == Calendar.APRIL
                || month == Calendar.JULY || month == Calendar.OCTOBER)) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否为半年末
     *
     * @param nowDate 日期（需要验证的日期）
     * @return boolean true 表示是半年末 false 表示不是半年末
     */
    public static boolean isHalfYearEnd(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        cal.add(Calendar.DAY_OF_MONTH, 1);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1 && (month == Calendar.JUNE || month == Calendar.DECEMBER)) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否为半年出
     *
     * @param nowDate 日期（需要验证的日期）
     * @return boolean true 表示是半年初 false 表示不是半年初
     */
    public static boolean isHalfYearBegin(Date nowDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        if (day == 1 && (month == Calendar.JANUARY || month == Calendar.JULY)) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否为年末
     *
     * @param nowDate 日期（需要验证的日期）
     * @return boolean true 表示是年末 false 表示不为年末
     */
    public static boolean isYearEnd(Date nowDate) {
        if ("1231".equals(format(nowDate, "MMdd"))) {
            return true;
        }
        return false;
    }

    /**
     * 判断时间是否为年初
     *
     * @param nowDate 日期（需要验证的日期）
     * @return boolean true 表示是年初 false 表示不为年初
     */
    public static boolean isYearBegin(Date nowDate) {
        if ("0101".equals(format(nowDate, "MMdd"))) {
            return true;
        }
        return false;
    }

    /**
     * 获取日期的年月日
     *
     * @return
     */
    public static Calendar getYMD(Date date) {
        String dateStr = format(date, "yyyyMMdd");
        date = parse(dateStr, "yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 判断是否为结算日期
     *
     * @param stlCycle ,stlCycleDay,tranDate
     * @return boolean
     */
    public static boolean chkStlTime(String stlCycle, String stlCycleDay,
                                     Date tranDate) {
        boolean b = false;
        switch (stlCycle.toCharArray()[0]) {
            case '1':
                // 日结
                b = true;
                break;
            case '2':
                // 周结
                String nowDate = String.valueOf(DateTimeUtil.getDayOfWeek(tranDate));
                if (nowDate.equals(stlCycleDay)) {
                    b = true;
                }
                break;
            case '3':
                // 月结
                if (stlCycleDay.equals("0")) {
                    // 月末结
                    boolean result = DateTimeUtil.isMonthEnd(tranDate);
                    if (result) {
                        b = true;
                    }
                } else {
                    // 非月末结
                    String nowDate1 = String.valueOf(DateTimeUtil
                            .getDayOfMouth(tranDate));
                    if (nowDate1.equals(stlCycleDay)) {
                        b = true;
                    }
                }
                break;
            case '4':
                // 季结
                if ("1".equals(stlCycleDay)) {
                    // 季初
                    if (DateTimeUtil.isQuarterBegin(tranDate)) {
                        b = true;
                    }
                } else if ("0".equals(stlCycleDay)) {
                    // 季末
                    if (DateTimeUtil.isQuarterEnd(tranDate)) {
                        b = true;
                    }
                }
                break;
            case '5':
                // 半年结
                if ("1".equals(stlCycleDay)) {
                    // 半年初
                    if (DateTimeUtil.isHalfYearBegin(tranDate)) {
                        b = true;
                    }
                } else if ("0".equals(stlCycleDay)) {
                    // 半年末
                    if (DateTimeUtil.isHalfYearEnd(tranDate)) {
                        b = true;
                    }
                }
                break;
            case '6':
                // 年结
                if ("1".equals(stlCycleDay)) {
                    // 年初
                    if (DateTimeUtil.isYearBegin(tranDate)) {
                        b = true;
                    }
                } else if ("0".equals(stlCycleDay)) {
                    // 年末
                    if (DateTimeUtil.isYearEnd(tranDate)) {
                        b = true;
                    }
                }
                break;
            default:
                break;
        }
        return b;
    }

    // 比较频繁交易前后两笔的时间间隔与指定的某个时间对比，在这个时间段内，是频繁交易
    public static boolean monFreCompare(Date startTime, Date endTime,
                                        int interTime) {
        boolean flag = false;
        long a = (endTime.getTime() - startTime.getTime());
        // 两笔交易的时间间隔<=interTime,是频繁交易
        long interval = a / 1000;
        if (interval <= interTime && interval > 0) {
            flag = true;
        }

        return flag;
    }

    private DateTimeUtil() {
    }

    /**
     * 描述: 判断<firstDate>时间点是否在<secondDate>时间点之前 如果此 firstDate
     * 的时间在参数<secondDate>表示的时间之前，则返回小于 0 的值
     *
     * @param firstDate
     * @param secondDate
     * @return
     * @author chenlc
     */
    public static boolean isBefore(Date firstDate, Date secondDate) {
        return compare(firstDate, secondDate) < 0 ? true : false;
    }

    /**
     * 描述: 判断<firstDate>时间点是否在<secondDate>时间点之后 如果此 firstDate
     * 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
     *
     * @param firstDate
     * @param secondDate
     * @return
     * @author chenlc
     */
    public static boolean isAfter(Date firstDate, Date secondDate) {
        return compare(firstDate, secondDate) > 0 ? true : false;
    }

    /**
     * 描述: 比较两个时间点是否相等
     *
     * @param firstDate
     * @param secondDate
     * @return
     * @author chenlc
     */
    public static boolean isEqual(Date firstDate, Date secondDate) {
        return compare(firstDate, secondDate) == 0 ? true : false;
    }

    /**
     * 描述: 比较两个时间点 如果secondDate表示的时间等于此 firstDate 表示的时间，则返回 0 值； 如果此 firstDate
     * 的时间在参数<secondDate>表示的时间之前，则返回小于 0 的值； 如果此 firstDate
     * 的时间在参数<secondDate>表示的时间之后，则返回大于 0 的值
     *
     * @param firstDate
     * @param secondDate
     * @return
     * @author chenlc
     */
    public static int compare(Date firstDate, Date secondDate) {
        Calendar firstCalendar = null;
        if (firstDate != null) {
            firstCalendar = Calendar.getInstance();
            firstCalendar.setTime(firstDate);
        }
        Calendar secondCalendar = null;
        if (firstDate != null) {
            secondCalendar = Calendar.getInstance();
            secondCalendar.setTime(secondDate);
        }
        try {
            return firstCalendar.compareTo(secondCalendar);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(e);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 描述: 时间区间判断
     *
     * @param startDate
     * @param endDate
     * @param inDate
     * @return
     * @author chenlc
     */
    public static int betweenTowDate(Date startDate, Date endDate, Date inDate) {
        if (isBefore(endDate, startDate)) {
            throw new IllegalArgumentException(
                    "endDate can not before startDate!");
        }
        int sflag = compare(inDate, startDate);
        int eflag = compare(inDate, endDate);
        int flag = 0;
        if (sflag < 0) {
            flag = DateTimeUtil.BEFORE_START_DATE;
        } else if (sflag == 0) {
            if (eflag == 0) {
                flag = DateTimeUtil.TREE_DATE_EQUAL;
            } else {
                flag = DateTimeUtil.EQUAL_START_DATE;
            }
        } else if (sflag > 0 && eflag < 0) {
            flag = DateTimeUtil.BETWEEN_TOW_DATE;
        } else if (eflag == 0) {
            flag = DateTimeUtil.EQUAL_END_DATE;
        } else if (eflag > 0) {
            flag = DateTimeUtil.AFTER_END_DATE;
        }
        return flag;
    }

    /**
     * 描述: 分别判断当前日期是与一个日期区间的六种情况比较 （1） 一个日期早于一个日期区间 （2）三个日期相等
     * （3）一个日期等于一个日期区间的开始日期 （4）一个日期在一个日期区间之内 （5）一个日期等于一个日期区间的结束日期
     * （6）一个日期晚于一个日期区间
     *
     * @param startDate
     * @param endDate
     * @return
     * @author chenlc
     */
    public static int betweenTowDate(Date startDate, Date endDate) {
        return betweenTowDate(startDate, endDate, new Date());
    }

    /**
     * @param startDate 日期
     * @param x         小时
     * @return
     */
    public static String addDate(String startDate, int x) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");//24小时制
        Date date = null;

        try {
            date = format.parse(startDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (date == null) {
            return "";
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, x);//24小时制
        date = cal.getTime();
        cal = null;
        return format.format(date);
    }



    public static String getyyyyMMdd(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CHINESE_YEAR_DATE_PATTERN);//CHINESE_YEAR_DATE_PATTERN "yyyy年MM月dd日"
        return sdf.format(date);
    }

    public static String getHHss(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_TIME_MINUTE);//CHINESE_YEAR_DATE_PATTERN "yyyy年MM月dd日"
        return sdf.format(date);
    }

    public static String getToType(Date date,String type) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(type);//CHINESE_YEAR_DATE_PATTERN "yyyy年MM月dd日"
        return sdf.format(date);
    }


    public static Date parseToDate(Date date,String type) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(type);//CHINESE_YEAR_DATE_PATTERN "yyyy年MM月dd日"
        try {
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static int DateCompare(String source, String type){
        int ret=0;
        try {
            System.out.println(source);
            String nowTime = new SimpleDateFormat("HH:MM").format(new Date());
            SimpleDateFormat format = new SimpleDateFormat(type);
            Date sourcedate = format.parse(source);
            Date tragetdate = format.parse(nowTime);
            ret = sourcedate.compareTo(tragetdate);
        }catch (Exception e){

        }

        return ret;
    }

    //	实现给定某日期，判断是星期几
    public static int dayForWeek(String pTime) {
        if(StringUtils.isBlank(pTime)){
            return 0;
        }
        int dayForWeek = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(pTime));

            if(c.get(Calendar.DAY_OF_WEEK) == 1){
                dayForWeek = 7;
            }else{
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        }catch (Exception e){
            logger.info("日期格式化星期失败{}",pTime);
        }
        return dayForWeek;
    }

    /*
     * @Description: 获取指定某年某月份的最后一天
     * @Date 2019/6/3 10:52
     * @param: yearMonth yyyy-MM格式
     * @return: java.lang.String
     */
    public static String getLastDayOfMonth(String yearMonth) {
        int year = Integer.parseInt(yearMonth.split("-")[0]);  //年
        int month = Integer.parseInt(yearMonth.split("-")[1]); //月
        Calendar cal = Calendar.getInstance();
        // 设置年份
        cal.set(Calendar.YEAR, year);
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        // 获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        // 设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }

    public static void main(String[] args) throws Exception {

       // Date date = parse("2019-02", "yyyyMM");
        System.out.println(getMonthS("2019"));
        // System.out.println(getMonth(new Date(),-1));
    }


    public static String getRightDate(String optRegisterDate) {
        if(StringUtils.isEmpty(optRegisterDate)) {
            return null;
        }
        /* 当前请求日期是否当天 */
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        Date reqOptRigisterDate = null;
        try {
            reqOptRigisterDate = DateUtils.parseDate(optRegisterDate, DateTimeUtil.NORMAL_DATE_PATTERN);
        } catch (ParseException e) {
            logger.error("格式化日期错误, optRegisterDate:{}, foramt:{}", optRegisterDate, DateTimeUtil.NORMAL_DATE_PATTERN, e);
            return null;
        }

        if(!DateUtils.isSameDay(nowDate, reqOptRigisterDate)) {
            return optRegisterDate;
        }

        /* 现在是否过了16：30 */
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 30);
        Date today1630 = calendar.getTime();

        if(nowDate.before(today1630)){
            return optRegisterDate;
        }
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return DateFormatUtils.format(calendar.getTime(), DateTimeUtil.NORMAL_DATE_PATTERN);
    }

    /**
     * 4点半前返回当天 4点半后返回明天
     * @return
     */
    public static boolean beforeToday1630() {

        //DateTime todayDateTime = new DateTime();

        /* 当前请求日期是否当天 */
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        /* 现在是否过了16：30 */
        calendar.set(Calendar.HOUR_OF_DAY, 16);
        calendar.set(Calendar.MINUTE, 30);
        Date today1630 = calendar.getTime();
        if(nowDate.before(today1630)){
            return true;
        }
        return false;
    }

    public static boolean afterHour1130() {
        /* 当前请求日期是否当天 */
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();

        /* 现在是否过了11：30 */
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 30);
        Date today1130 = calendar.getTime();

        if(today1130.before(nowDate)){
            return true;
        }
        return false;
    }

    public static String getDayOfWeekStr(int i) {
        switch (i) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
            default:
                return null;
        }
    }

    /**
     * 年份
     *
     * @param date
     * @param num
     * @return
     */
    public static String getYear(Date date, int num) {
        // 下一年
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, num);
        return DateFormatUtils.format(calendar.getTime(), DateTimeUtil.NORMAL_YEAR_YY);
    }

    /**
     * 年份
     */
    public static String getYear(String year, int num) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(NORMAL_YEAR_YY);
            Date yearDate = format.parse(year);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(yearDate);
            calendar.add(Calendar.YEAR, num);
            return DateFormatUtils.format(calendar.getTime(), DateTimeUtil.NORMAL_YEAR_YY);
        }catch (Exception e){
            logger.info("年份{}",e.getMessage());
        }
        return null;
    }
    /**
     * 月份
     *
     * @param date
     * @param num
     * @return
     */
    public static String getMonth(Date date, int num) {
        // 下个月
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, num);
        return DateFormatUtils.format(calendar.getTime(), DateTimeUtil.MONTH_PATTERN);
    }

    /**
     * 月份
     *
     * @param year
     * @return
     */
    public static List<String> getMonthS(String year) {
        if(StringUtil.isBlank(year)){
            return new ArrayList<>();
        }
        String nowYear = getYear(new Date(),0);
        String nowMonth ="NULL";
        // 下个月
        if(year.equals(nowYear)){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, 0);
            nowMonth = DateFormatUtils.format(calendar.getTime(), DateTimeUtil.MONTH_PATTERN);
        }

        String[] months={"01","02","03","04","05","06","07","08","09","10","11","12"};
        List<String> retMonths=new ArrayList<>();
        for(String month : months){
            retMonths.add(year+"-"+month);
            if(nowMonth.equals(month)){
                break;
            }
        }
        return retMonths;
    }

    public static String getLastMonth(String month) {
        switch (month) {
            case "01":
                return "12";
            case "02":
                return "01";
            case "03":
                return "02";
            case "04":
                return "03";
            case "05":
                return "04";
            case "06":
                return "05";
            case "07":
                return "06";
            case "08":
                return "07";
            case "09":
                return "08";
            case "10":
                return "09";
            case "11":
                return "10";
            case "12":
                return "11";
            default:
                return null;
        }
    }
}

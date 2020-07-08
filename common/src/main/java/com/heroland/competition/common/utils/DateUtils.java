package com.heroland.competition.common.utils;

import com.heroland.competition.common.contants.TimeIntervalUnit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Slf4j
public class DateUtils {

    public static final String PATTERN_STANDARD = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_DATE = "yyyy-MM-dd";

    public static final String PATTERN_MONTH = "yyyy-MM";

    public static final String PATTERN_YYYY_MM_DD_HH_MM = "yyyyMMddHHmm";

    public static final String PATTERN_YYYY_MM_DD_HH_MM_2 = "yyyy-MM-dd HH:mm";

    public static final long MILLIS_PER_DAY = 86400000L;

    public static String timestamp2String(Timestamp timestamp, String pattern) {
        if (timestamp == null) {
            throw new IllegalArgumentException("timestamp null illegal");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = PATTERN_STANDARD;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date(timestamp.getTime()));
    }

    public static Date formatDate(Date date, String pattern) {
        String dateStr = date2String(date, pattern);
        return string2Date(dateStr, pattern);
    }


    public static String date2String(Date date, String pattern) {
        if (date == null) {
            throw new IllegalArgumentException("timestamp null illegal");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = PATTERN_STANDARD;
            ;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static Timestamp currentTimestamp() {
        return new Timestamp(new Date().getTime());
    }

    public static int now() {
        return (int) (System.currentTimeMillis() / 1000L);
    }

    public static String currentTimestamp2String(String pattern) {
        return timestamp2String(currentTimestamp(), pattern);
    }

    public static Timestamp string2Timestamp(String strDateTime, String pattern) {
        if (strDateTime == null || strDateTime.equals("")) {
            throw new IllegalArgumentException("Date Time Null Illegal");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = PATTERN_STANDARD;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = sdf.parse(strDateTime);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return new Timestamp(date.getTime());
    }

    public static Date string2Date(String strDate, String pattern) {
        if (strDate == null || strDate.equals("")) {
            throw new RuntimeException("str date null");
        }
        if (pattern == null || pattern.equals("")) {
            pattern = DateUtils.PATTERN_DATE;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date = null;

        try {
            date = sdf.parse(strDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    public static String stringToYear(String strDest) {
        if (strDest == null || strDest.equals("")) {
            throw new IllegalArgumentException("str dest null");
        }

        Date date = string2Date(strDest, DateUtils.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return String.valueOf(c.get(Calendar.YEAR));
    }

    public static String stringToMonth(String strDest) {
        if (strDest == null || strDest.equals("")) {
            throw new IllegalArgumentException("str dest null");
        }

        Date date = string2Date(strDest, DateUtils.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // return String.valueOf(c.get(Calendar.MONTH));
        int month = c.get(Calendar.MONTH);
        month = month + 1;
        if (month < 10) {
            return "0" + month;
        }
        return String.valueOf(month);
    }

    public static String stringToDay(String strDest) {
        if (strDest == null || strDest.equals("")) {
            throw new IllegalArgumentException("str dest null");
        }

        Date date = string2Date(strDest, DateUtils.PATTERN_DATE);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // return String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        }
        return "" + day;
    }

    public static Date getFirstDayOfMonth(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = 1;
        c.set(year, month, day, 0, 0, 0);
        return c.getTime();
    }

    public static Date getLastDayOfMonth(Calendar c) {
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = 1;
        if (month > 11) {
            month = 0;
            year = year + 1;
        }
        c.set(year, month, day - 1, 0, 0, 0);
        return c.getTime();
    }

    /**
     * 获取当年的最后一天 yyyy-MM-dd HH:mm:ss
     */
    public static Date getCurrYearLast(){
        Calendar currCal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.YEAR, currCal.get(Calendar.YEAR));
        calendar.roll(Calendar.DAY_OF_YEAR, -1);
        return getEndTimeOfDate(calendar.getTime());
    }


    public static String date2GregorianCalendarString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("Date is null");
        }
        long tmp = date.getTime();
        GregorianCalendar ca = new GregorianCalendar();
        ca.setTimeInMillis(tmp);
        try {
            XMLGregorianCalendar t_XMLGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(ca);
            return t_XMLGregorianCalendar.normalize().toString();
        } catch (DatatypeConfigurationException e) {
            throw new IllegalArgumentException(e);
        }

    }

    public static boolean compareDate(Date firstDate, Date secondDate) {
        if (firstDate == null || secondDate == null) {
            throw new RuntimeException();
        }

        String strFirstDate = date2String(firstDate, "yyyy-MM-dd");
        String strSecondDate = date2String(secondDate, "yyyy-MM-dd");
        if (strFirstDate.equals(strSecondDate)) {
            return true;
        }
        return false;
    }

    public static Date getStartTimeOfDate(Date currentDate) {
        Assert.notNull(currentDate);
        String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 00:00:00";
        return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
    }

    public static Date getEndTimeOfDate(Date currentDate) {
        Assert.notNull(currentDate);
        String strDateTime = date2String(currentDate, "yyyy-MM-dd") + " 23:59:59";
        return string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
    }

    public static boolean isValidDate(String str) {
        boolean convertRes = true;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setLenient(false);
        try {
            formatter.parse(str);
        } catch (ParseException e) {
            convertRes = false;
        }
        return convertRes;
    }

    /**
     * 获取num后的时间
     *
     * @param date         原始时间
     * @param num          增减数量
     * @param calendarType 增加类型：如 Calendar.DATE
     * @return
     */
    public static Date getDate(Date date, int num, int calendarType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarType, num);
        return calendar.getTime();
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smallDate 较小的时间
     * @param bigDate   较大的时间
     * @return 相差天数
     */
    public static int daysBetween(Date smallDate, Date bigDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smallDate = sdf.parse(sdf.format(smallDate));
            bigDate = sdf.parse(sdf.format(bigDate));
        } catch (ParseException e) {
            log.error("parse date fail", e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smallDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bigDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    public static void main(String[] args) {
        System.out.println("#######################");
        int i = Integer.parseInt(String.valueOf(10));

        System.out.println(i);
        System.out.println(Math.abs(i));
    }
;
    public static Date plusMill(Date now, long plus) {
        long time = now.getTime();
        Date date = new Date(time + plus);
        return date;
    }


    /**
     * 计算 startTime & endTime 是否超过days天
     * <p>
     * 超过返回 true 否 返回 false
     */
    public static boolean judgeDate(String startTime, String endTime, int days) {

        Date start = string2Date(startTime, PATTERN_STANDARD);
        Date end = string2Date(endTime, PATTERN_STANDARD);

        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.DAY_OF_MONTH, days);
        if (c.getTime().before(end)) {
            return true;
        } else {
            return false;
        }


    }


    /**
     * 计算 startTime & endTime 是否超过days天
     * <p>
     * 超过返回 true 否 返回 false
     */
    public static boolean judgeDate(String startTime, String endTime, int days, String patten) {

        Date start = string2Date(startTime, patten);
        Date end = string2Date(endTime, patten);

        Calendar c = Calendar.getInstance();
        c.setTime(start);
        c.add(Calendar.DAY_OF_MONTH, days);
        if (c.getTime().before(end)) {
            return true;
        } else {
            return false;
        }


    }


    /**
     * 获取{interval}间隔之前/之后的时间
     * 间隔之前:interval < 0
     * 间隔之后:interval > 0
     *
     * @param date     时间
     * @param interval 间隔
     * @param unit     间隔单位<br>
     *                 DAY:天
     *                 MONTH:月
     *                 QUARTER:季度
     *                 YEAR:年
     * @return 日期 时间
     */
    public static Date plusDate(Date date, int interval, TimeIntervalUnit unit) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        switch (unit) {
            case DAY:
                calendar.add(Calendar.DATE, interval);
                break;
            case MONTH:
                calendar.add(Calendar.MONTH, interval);
                break;
            case QUARTER:
                calendar.add(Calendar.MONTH, interval * 3);
                break;
            case YEAR:
                calendar.add(Calendar.YEAR, interval);
                break;
            default:
                calendar.add(Calendar.DATE, interval);
                break;
        }
        return calendar.getTime();
    }

    /**
     * 计算两个时间之间的小时数
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 小时数
     */
    public static int betweenHours(Date begin, Date end) {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(begin);
        c2.setTime(end);

        long hours = ((c2.getTime().getTime() / 1000) - (c1.getTime().getTime() / 1000)) / 60 / 60;
        return (int) hours;
    }

    /**
     * 是否是有效时间
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return boolean
     */
    public static boolean isValidTime(Date begin, Date end) {
        Date now = new Date();
        return now.before(end) && now.after(begin);
    }

    /**
     * 计算出两个日期之间相差的天数
     * end 大于 begin 这样计算的值为正数
     *
     * @return date1 - date2
     */
    public static int getDateSpace(Date begin, Date end) {

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();

        calst.setTime(begin);
        caled.setTime(end);

        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        long days = ((caled.getTime().getTime() / 1000) - (calst.getTime().getTime() / 1000)) / 3600 / 24;

        return (int) days;
    }

    /**
     * 计算一年中的总天数，365/366
     *
     * @return
     */
    public static int getDaysOfYear() {
        Calendar cal = Calendar.getInstance();
        int yr = cal.get(Calendar.YEAR);
        boolean isLeap = yr % 400 == 0 || (yr % 100 != 0 && yr % 4 == 0);
        if (isLeap) {
            return 366;
        }
        return 365;
    }

    public static boolean isSameInstantWithin1M(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        long secondsDiffer = Math.abs(date1.getTime() / 1000 - date2.getTime() / 1000);

        return date1.getTime() == date2.getTime() || secondsDiffer < 60L;
    }


    public static void main2(String[] args) throws ParseException {
        String str1 = "2011-01-01";
        String str2 = "1988-09-09";
        Date date1 = DateUtils.string2Date(str1, "yyyy-MM-dd");
        Date date2 = DateUtils.string2Date(str2, "yyyy-MM-dd");
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        c2.add(Calendar.YEAR, 4);
        if (c2.before(c1)) {
            System.out.println("illegal");
        } else {
            System.out.println("ok");
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sdateString = "2016-09-12 23:59:00";
        String bdateString = "2016-09-12 23:59:46";
        try {
            Date sdate = formatter.parse(sdateString);
            Date bdate = formatter.parse(bdateString);
            System.out.println(daysBetween(sdate, bdate));
            System.out.println(Math.round((10 * (-100 / 3))));
        } catch (ParseException e) {
            log.error("parse data fail", e);
        }

        Date day = plusDate(new Date(), -12, TimeIntervalUnit.DAY);
        System.out.println(DateUtils.date2String(day, "yyyy-MM-dd HH:mm:ss"));

        System.out.println(DateUtils.getDate(new Date(), 12, Calendar.DATE));


        Date month = plusDate(new Date(), 12, TimeIntervalUnit.MONTH);
        System.out.println(DateUtils.date2String(month, "yyyy-MM-dd HH:mm:ss"));

        Date quarter = plusDate(new Date(), 12, TimeIntervalUnit.QUARTER);
        System.out.println(DateUtils.date2String(quarter, "yyyy-MM-dd HH:mm:ss"));

        Date year = plusDate(new Date(), 12, TimeIntervalUnit.YEAR);
        System.out.println(DateUtils.date2String(year, "yyyy-MM-dd HH:mm:ss"));

        System.out.println("------------------------");
        Date s1 = formatter.parse("2017-09-11 19:00:00");
        Date s2 = formatter.parse("2017-09-11 22:59:00");
        Date s3 = formatter.parse("2017-09-12 22:21:00");
        int hours = DateUtils.betweenHours(s1, s2);
        System.out.println(hours);

        boolean isValid = isValidTime(s1, s2);
        System.out.println(isValid);

        boolean isValid2 = isValidTime(s2, s3);
        System.out.println(isValid2);

        Date date3 = new Date();
        Date date4 = plusMill(date3, 1000);//加1秒
        System.out.println(date3 + "----" + date4);
    }

    public static Date parseDate(String date){
        if (StringUtils.isBlank(date)){
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, PATTERN_STANDARD,PATTERN_DATE);
        }catch (Exception e){
            log.warn("error parseDate[{}]",date);
        }
        return null;
    }



    public static Date parseDate(String date,String... parsePatterns){
        if (StringUtils.isBlank(date)){
            return null;
        }
        try {
            return org.apache.commons.lang3.time.DateUtils.parseDate(date, parsePatterns);
        }catch (Exception e){
            log.warn("error parseDate[{}]",date);
        }
        return null;
    }

    /**
     * 判断指定时间是否是否在 明天凌晨之前
     *
     * @param date
     * @return
     */
    public static boolean isBeforeTomorrow(Date date) {
        String strDateTime = date2String(new Date(), "yyyy-MM-dd") + " 59:59:59";
        Date currentDayMaxTime = string2Date(strDateTime, "yyyy-MM-dd hh:mm:ss");
        return date.before(currentDayMaxTime);
    }
}
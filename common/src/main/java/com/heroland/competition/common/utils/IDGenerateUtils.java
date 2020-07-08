package com.heroland.competition.common.utils;

import com.heroland.competition.common.contants.AdminFieldEnum;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Random;

/**
 * @author smjyouzan
 * @date 2020/6/23
 */
@Component
public class IDGenerateUtils {

    public static final Integer SYSTEM_CODE = 6;

    public static Random random = new Random();


    @AllArgsConstructor
    public enum ModelEnum {
        DEFAULT("0", "默认"),

        ADMIN("1", "后台数据管理"),

        ORDER("6", "订单"),

        PAY("3", "支付单"),

        ;

        private String code;

        private String desc;
    }

    /**
     * 通过随机数生成ID
     * 系统标识 1位 + 模型标识 2位 + 时间戳 10位(s) + 序列号 5位
     *
     * @param modeEnum
     * @return
     */
    public static long getIdByRandom(ModelEnum modeEnum) {

        StringBuilder sb = new StringBuilder();

        sb.append(SYSTEM_CODE);

        sb.append(modeEnum.code);

        long currentTimeStampInSec = System.currentTimeMillis() / 1000L;

        String timeStampInSecStr = StringUtils.leftPad(String.valueOf(currentTimeStampInSec), 10, "0");

        sb.append(timeStampInSecStr);

        Long randomNum = random.nextLong();

        Long tailNum = randomNum % 1000;

        String marchStr = StringUtils.leftPad(tailNum.toString(), 3, "0");

        sb.append(marchStr);

        return NumberUtils.parseLong(sb);
    }

    /**
     * 获取后台管理中的值编号
     * @param subjectEnum
     * @return
     */
    public static String getKey(AdminFieldEnum subjectEnum){
        if (Objects.isNull(subjectEnum)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(subjectEnum.getCode()).append("-").append(getIdByRandom(ModelEnum.ADMIN));
        return sb.toString();
    }

    /**
     * 获取订单业务单号
     * @return
     */
    public static String getOrderBizNo(){
        return getIdByRandom(ModelEnum.ORDER)+"";
    }
}

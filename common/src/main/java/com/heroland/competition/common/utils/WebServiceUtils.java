package com.heroland.competition.common.utils;

/**
 * @author: peng.zhup
 * @date: 2019/1/4
 * @instructions: 发送webservice操作工具类
 */
public class WebServiceUtils {


    /**
     * 电子健康卡查重,注册 webservice xml 拼接字符串
   */
    public static final String START = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://webservices.vcard.health.yjcloud.com/\">\n" +
            "   <soapenv:Header/>\n" +
            "   <soapenv:Body>\n" +
            "      <ser:doService>\n" +
            "         <!--Optional:-->\n" +
            "         <HeaderInParm>\n" +
            "<![CDATA[";

    public static final String MIDDLE = "]]>\n" +
            "</HeaderInParm>\n" +
            "         <!--Optional:-->\n" +
            "         <BodyInParm>\n" +
            "       <![CDATA[";

    public static final String END = "]]>\n" +
            "         </BodyInParm>\n" +
            "      </ser:doService>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";


}

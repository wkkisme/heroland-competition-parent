package com.heroland.competition.common.utils;

import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
 
public class SecretUtilTest {
	/**
	 * 密钥头部参数相关
	 */
	//应用id
	public static String APPID  = "f0a2adb9f1ea89f8a792";
	//密钥id
	public static String ACCESSKEYID = "1e5373c58663d902cd44";
	//密钥
	public static String ACCESSKEY = "f96eff4ee42d2610db041b7d0673b683";
	//请求地址
	public static String HANDER_URL = "http://192.168.3.79:8484/choice/api/customer/reserviceinfo";
	//电脑系统
	public static String HANDER_DEVICE = "Win7";
	//电脑名称
	public static String HANDER_PRGNAME = "Assistant_Dev";
	//内容是否加密
	public static String HANDER_ENCRYPT = "false";
	//机构编号
	public static String HANDER_JIGOUBH = "0";
	//个人编号
	public static String HANDER_GERENBH = "1";
	//登陆方式
	public static String HANDER_LOGID = "login";
	//应用类型
	public static String HANDER_YINGYONGLX = "yuntongdao_w";
	//加密方式
	public static String MAC_NAME = "HmacSHA1";
	//编码方式
	public static String ENCODING = "UTF-8";
	//时间戳，GMT+8格式
	public static String FORMAT_PATTERN_TIME_GMT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";
	
	/**
	 * 签名算法
	 * 
	 * @param content
	 * @param key
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeyException
	 */
	@SuppressWarnings("restriction")
	public static String signature(String content, String key)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		byte[] data = key.getBytes(ENCODING);
		// 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
		SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
		// 生成一个指定 Mac 算法 的 Mac 对象
		Mac mac = Mac.getInstance(MAC_NAME);
		// 用给定密钥初始化 Mac 对象
		mac.init(secretKey);

		byte[] text = content.getBytes(ENCODING);
		// 完成 Mac 操作
		byte[] res = mac.doFinal(text);
		BASE64Encoder base64Encoder = new BASE64Encoder();
		String testss = base64Encoder.encode(res);
		return testss;
	}

	/**
	 * 获取 签名内容
	 *
	 * @param url
	 * @param headers
	 * @return
	 */
	private static String getSignatureContent(String url, Map<String, String> headers) {
		// 加密
		String VERB = "POST";
		String CONTENT_MD5 = "";
		String CONTENT_TYPE = "";
		StringBuffer content=new StringBuffer();
		content.append(VERB).append("\n");
		content.append(CONTENT_MD5).append("\n");
		content.append(CONTENT_TYPE).append("\n");
		content.append(headers.get("Date")).append("\n");
		content.append("x-weimai-appid:" + headers.get("x-weimai-appid")).append("\n");
		content.append("x-weimai-device:"+ headers.get("x-weimai-device")).append("\n");
		content.append("x-weimai-encrypt:"+HANDER_ENCRYPT).append("\n");
		content.append("x-weimai-jgbh:" + headers.get("x-weimai-jgbh")).append("\n");
		content.append("x-weimai-grbh:" + HANDER_GERENBH).append("\n");
		content.append("x-weimai-logid:" + HANDER_LOGID).append("\n");
		content.append("x-weimai-prgname:"+ headers.get("x-weimai-prgname")).append("\n");
		content.append("x-weimai-yylx:" + HANDER_YINGYONGLX).append("\n");
		content.append(url); 
//		System.out.println(content);
		return content.toString();
	}

	/**
	 * 组装headers头部内容 用于post调取
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 */
	@SuppressWarnings("restriction")
	public static void authorizAtion() throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException{
		String eventId=UUID.randomUUID().toString();
		BASE64Encoder base64Encoder = new BASE64Encoder();
		byte[] bbs1 = HANDER_DEVICE.getBytes("utf-8");
		byte[] bbs2 = HANDER_PRGNAME.getBytes("utf-8");
		String device = base64Encoder.encode(bbs1);
		String prgname = base64Encoder.encode(bbs2);
		String date = parseGMT();
		// 加载头部信息
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent", "Mozilla/5.0");
		headers.put("Accept", "application/json");
		headers.put("Content-Type", "application/json");
		headers.put("Accept-Encoding", "gzip");
		headers.put("Date", date);
		headers.put("x-weimai-appid", APPID);
		headers.put("x-weimai-device", device);
		headers.put("x-weimai-encrypt", HANDER_ENCRYPT);
		headers.put("x-weimai-jgbh", HANDER_JIGOUBH);
		headers.put("x-weimai-grbh", HANDER_GERENBH);
		headers.put("x-weimai-logid", HANDER_LOGID);
		headers.put("x-weimai-prgname", prgname);
		headers.put("x-weimai-yylx", HANDER_YINGYONGLX);
		headers.put("x-zsvh-log", HANDER_ENCRYPT);
		headers.put("x-weimai-eventid", eventId);
		String signatureContent = getSignatureContent(HANDER_URL.substring(HANDER_URL.lastIndexOf("/") + 1)+"?", headers);
		String signatureValue = signature(signatureContent, ACCESSKEYID);
		headers.put("Authorization", "WEIMAI " + ACCESSKEY + ":" + signatureValue);
		for (String key:headers.keySet()) {
			System.out.println(key+":"+headers.get(key));
		}
	}

	
	 /**
     * GMT时间
     *
     * @return
     */
    public static String parseGMT() {
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN_TIME_GMT, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        String str = sdf.format(cd.getTime());
        return str;
    }
    

}

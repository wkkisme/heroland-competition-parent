package com.heroland.competition.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * HBase表行键生成
 * @author chenli
 *
 */
public class HRowKeyUtil {

	/**
	 * 生成主索引日志表行键--[机构编码][逆向时间戳][操作人] 
	 * @param og--机构编码
	 * @param tm--时间
	 * @param op--操作人
	 * @return
	 */
	public static String indexLogKeyCreate(String og,Date tm,String op) {
		if(StringUtils.isBlank(og)||StringUtils.isBlank(op)||null == tm) {
			return null;
		}
		int t = op.hashCode();
		long d =Long.MAX_VALUE-tm.getTime();
		int i = og.hashCode();
		String logRowKey = String.valueOf(i)+String.valueOf(d)+String.valueOf(t);
		return logRowKey;
	}
}

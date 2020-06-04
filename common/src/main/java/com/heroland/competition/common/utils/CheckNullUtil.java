package com.heroland.competition.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数非空验证
 * @author chenli
 *
 */
public class CheckNullUtil {

	/**
	 * 传入要验证的非空参数，全部非空返回true，否则返回false
	 * @param params
	 * @return
	 */
	public static boolean checkNullParams(Object... params) {
		boolean flag = true;
		for (Object param : params) {
			if(null==param || StringUtils.isBlank(param.toString())) {
				flag = false;
				break;
			}
		}
		return flag;
	}
}

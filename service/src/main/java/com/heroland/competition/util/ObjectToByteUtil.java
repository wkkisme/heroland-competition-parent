package com.heroland.competition.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * 数据类型转换
 * @author chenli
 *
 */
public class ObjectToByteUtil {

	private byte[] data;

	public ObjectToByteUtil(Object targetData) {
		if(targetData instanceof Integer) {
			this.data = String.valueOf((int) targetData).getBytes();
		}
		if(targetData instanceof BigInteger) {
			BigInteger b = (BigInteger) targetData;
			this.data = String.valueOf(b.intValue()).getBytes();
		}
		if(targetData instanceof BigDecimal) {
			BigDecimal b = (BigDecimal) targetData;
			this.data = String.valueOf(b.doubleValue()).getBytes();
		}
		if(targetData instanceof Long) {
			this.data = String.valueOf((long) targetData).getBytes();
		}
		if(targetData instanceof Float) {
			this.data = String.valueOf((float) targetData).getBytes();
		}
		if(targetData instanceof Double) {
			this.data = String.valueOf((double) targetData).getBytes();
		}
		if(targetData instanceof Short) {
			this.data = String.valueOf((short) targetData).getBytes();
		}
		if(targetData instanceof Boolean) {
			this.data = String.valueOf((boolean) targetData).getBytes();
		}
		if(targetData instanceof String) {
			this.data = ((String) targetData).getBytes();
		}
		if(targetData instanceof byte[]) {
			this.data = (byte[]) targetData;
		}
		if(targetData instanceof Date) {
			this.data = String.valueOf(((Date) targetData).getTime()).getBytes();
		}
	}
	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}

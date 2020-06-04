package com.heroland.competition.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author: peng.zhup
 * @date: 2019/1/7
 * @instructions: Base64加密工具类
 */
public class Base64EncryptUtils {

    /**
     * base64加密
     *
     * @param content
     * @return
     */
    public static String encrypt(String content){
        if(content == null){
            throw new NullPointerException("加密内容不能为null");
        }
        Base64 base64 = new Base64();
        byte[] textByte = new byte[0];
        try {
            textByte = content.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return base64.encodeToString(textByte);
    }


    /**
     * base64解密
     *
     * @param content
     * @return
     */
    public static String decrypt(String content) {
        if(content == null){
            throw new NullPointerException("解密内容不能为null");
        }
        Base64 base64 = new Base64();
        try {
            return new String(base64.decode(content), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}

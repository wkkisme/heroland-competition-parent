package com.heroland.competition.common.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

/**
 * @Author: wk
 * @Project: bq-order-proxy
 * @DateTime: 2019/1/18 15:23
 * @Description: MAC地址工具类
 */
public class MacAddrUtils {




    public static String getMACAddress(InetAddress inetAddress)   {
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        byte[] mac = new byte[0];
        try {
            mac = NetworkInterface.getByInetAddress(inetAddress).getHardwareAddress();
        } catch (SocketException e) {
            return inetAddress.getHostAddress();
        }
        if(mac == null) {
            return inetAddress.getHostAddress();
        }
        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append(":");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

}

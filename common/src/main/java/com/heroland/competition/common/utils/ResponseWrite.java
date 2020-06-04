package com.heroland.competition.common.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wangkai
 */
public class ResponseWrite {


    /**
     * 首页专享
     * @param response
     */

    public static  void write(HttpServletResponse response,String html) {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter p = null;
        try {
            p = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
       if (p != null) {
           p.println(html);
           p.close();
       }
    }
}

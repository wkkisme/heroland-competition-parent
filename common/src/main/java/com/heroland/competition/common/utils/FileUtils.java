package com.heroland.competition.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author wangkai
 */
public class FileUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtils.class);

    public static String getString(String path) {
        InputStream stream = null;
        BufferedReader br = null;
        try {

            //FileInputStream in = new FileInputStream(file);
            StringBuilder stringBuffer = new StringBuilder();
            stream = FileUtils.class.getClassLoader().getResourceAsStream(path);

            br = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            String line;

            while ((line = br.readLine()) != null) {
                stringBuffer.append(line);
            }
            String content = stringBuffer.toString();
            LOGGER.info("初始化index.html文件成功, 内容:{}", content);
            return stringBuffer.toString();

        } catch (Exception e) {
            LOGGER.error("初始化index.html文件失败", e);
            return null;


        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


    }
}

package com.heroland.competition.util;


import com.alijk.bqhealth.cloud.vo.BaseVO;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * bqhealth-cloud
 *
 * @author wangkai
 * @date 2019/12/28
 */

public class ScanningFileUtil {
    private Class<?> superStrategy = BaseVO.class;//接口类class 用于过滤 可以不要

    private List<Class<? extends BaseVO>> eleStrategyList = new ArrayList<Class<? extends BaseVO>>();

    private ClassLoader classLoader = ScanningFileUtil.class.getClassLoader();//默认使用的类加载器

    private static final String STARATEGY_PATH = "com.alijk.bqhealth.cloud.vo.data";//需要扫描的策略包名

    public static void main(String[] args) {
        ScanningFileUtil s = new ScanningFileUtil();
        s.addClass();
        System.out.println("完毕");
    }

    /**
     * 获取包下所有实现了superStrategy的类并加入list
     */
    private void addClass(){
        URL url = classLoader.getResource(STARATEGY_PATH.replace(".", "/"));
        String protocol = url.getProtocol();
        if ("file".equals(protocol)) {
            // 本地自己可见的代码
            findClassLocal(STARATEGY_PATH);
        } else if ("jar".equals(protocol)) {
            // 引用jar包的代码
            findClassJar(STARATEGY_PATH);
        }
    }

    /**
     * 本地查找
     * @param packName
     */
    private void findClassLocal(final String packName){
        URI url = null ;
        try {
            url = classLoader.getResource(packName.replace(".", "/")).toURI();
        } catch (URISyntaxException e1) {
            throw new RuntimeException("未找到策略资源");
        }

        File file = new File(url);
        file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File chiFile) {
                if(chiFile.isDirectory()){
                    findClassLocal(packName+"."+chiFile.getName());
                }
                if(chiFile.getName().endsWith(".class")){
                    Class<?> clazz = null;
                    try {
                        clazz = classLoader.loadClass(packName + "." + chiFile.getName().replace(".class", ""));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    System.out.println(chiFile);
                    assert clazz != null;
                    if(superStrategy.isAssignableFrom(clazz)){
                        eleStrategyList.add((Class<? extends BaseVO>) clazz);
                    }
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * jar包查找
     * @param packName
     */
    private void findClassJar(final String packName){
        String pathName = packName.replace(".", "/");
        JarFile jarFile  = null;
        try {
            URL url = classLoader.getResource(pathName);
            assert url != null;
            JarURLConnection jarURLConnection  = (JarURLConnection )url.openConnection();
            jarFile = jarURLConnection.getJarFile();
        } catch (IOException e) {
            throw new RuntimeException("未找到策略资源");
        }

        Enumeration<JarEntry> jarEntries = jarFile.entries();
        while (jarEntries.hasMoreElements()) {
            JarEntry jarEntry = jarEntries.nextElement();
            String jarEntryName = jarEntry.getName();

            if(jarEntryName.contains(pathName) && !jarEntryName.equals(pathName+"/")){
                //递归遍历子目录
                if(jarEntry.isDirectory()){
                    String clazzName = jarEntry.getName().replace("/", ".");
                    int endIndex = clazzName.lastIndexOf(".");
                    String prefix = null;
                    if (endIndex > 0) {
                        prefix = clazzName.substring(0, endIndex);
                    }
                    assert prefix != null;
                    findClassJar(prefix);
                }
                if(jarEntry.getName().endsWith(".class")){
                    Class<?> clazz = null;
                    try {
                        clazz = classLoader.loadClass(jarEntry.getName().replace("/", ".").replace(".class", ""));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    assert clazz != null;
                    if(superStrategy.isAssignableFrom(clazz)){
                        eleStrategyList.add((Class<? extends BaseVO>) clazz);
                    }
                }
            }

        }

    }

}

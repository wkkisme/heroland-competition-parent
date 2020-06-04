package com.heroland.competition.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: peng.zhup
 * @Project: bq-order-proxy
 * @DateTime: 2019/1/18 14:59
 * @Description: 提供xml文件转换对象,用于电子健康卡调用返回结果封装成对象
 */
public class XmlConverBeanUtils {


    /**
     * xml文件转换对象
     *
     * @param clazz 指定类
     * @param xml   转换的xml字符串
     * @param <T>
     * @return
     */
    public static <T> T conver(Class<T> clazz, String xml){
        Map map = parse(xml);
        return JSONObject.parseObject(JSON.toJSONString(map),clazz);
    }



    private static Map parse(String xml){
        if(StringUtils.isBlank(xml)){
            throw new NullPointerException("xml不能为空！");
        }
        return parse(xml,new HashMap<>());
    }


    private static Map parse(String xml, Map<String,Object> map){
        //报文转成doc对象
        Document doc = null;
        try {
            doc = DocumentHelper.parseText(xml);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //获取根元素，准备递归解析这个XML树
        Element root = doc.getRootElement();
        getCode(root, map);
        return map;
    }

    private static void getCode(Element root, Map<String,Object> map){
        if(root.elements()!=null){
            //如果当前跟节点有子节点，找到子节点
            List<Element> list = root.elements();
            //遍历每个节点
            for(Element e:list){
                if(e.elements().size()>0){
                    //当前节点不为空的话，递归遍历子节点；
                    getCode(e, map);
                }
                //如果为叶子节点，那么直接把名字和值放入map
                if(e.elements().size()==0){
                    map.put(e.getName(), e.getTextTrim());
                }
            }
        }
    }

}

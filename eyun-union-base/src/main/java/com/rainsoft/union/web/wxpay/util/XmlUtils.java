package com.rainsoft.union.web.wxpay.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * XmlUtils
 * Created by qianna on 2016/10/9
 */
public class XmlUtils {
    private static Logger logger = LoggerFactory.getLogger(XmlUtils.class);
    /**
     * 通过实体获取xml字符串
     * @param entity
     * @return String
     */
    public static String getXmlStr4Bean(Object entity , Class tClass){
        //解决XStream对出现双下划线的bug
        XStream xStream = new XStream(new XppDriver(new NoNameCoder()) {

            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out) {
                    // 对所有xml节点的转换都增加CDATA标记
                    boolean cdata = true;

                    @Override
                    @SuppressWarnings("rawtypes")
                    public void startNode(String name, Class clazz) {
                        super.startNode(name, clazz);
                    }

                    /**
                     * 去掉对特殊字符的转换
                     * Encode the node name into the name of the target format.
                     * @param name the original name
                     * @return the name in the target format
                     */
                    @Override
                    public String encodeNode(String name) {
                        return name;
                    }

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (cdata) {
                            writer.write("<![CDATA[");
                            writer.write(text);
                            writer.write("]]>");
                        } else {
                            writer.write(text);
                        }
                    }
                };
            }
        });
        xStream.autodetectAnnotations(true);
        xStream.alias("xml" , tClass);
        String xml = xStream.toXML(entity);
        return xml;
    }


    /**
     * 从xml字符串，获取参数map
     * @param xmlStr
     * @return map
     */
    public static Map<String , Object> getMapFromXml(String xmlStr){
        Map<String , Object> map = new HashMap<>();
        if(!StringUtils.isEmpty(xmlStr)){
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(xmlStr);
            } catch (DocumentException e) {
                logger.error("xml parser dom failure");
                e.printStackTrace();
            }
            String key = "";
            logger.info("start to read data from document");
            if(doc != null){
                Element root = doc.getRootElement();
                for(Iterator i = root.elementIterator(); i.hasNext();){
                    Element element = (Element)i.next();
                    key = element.getName();
                    String value = element.getText();
                    logger.info("key:"+key+",parameter:"+value);
                    map.put(key,value);
                }
            }
        }
        return map;
    }
    
    /**
     * 从xml字符串，获取参数map
     * @param xmlStr
     * @return map
     */
    public static Map<String , String> getMapStringFromXml(String xmlStr){
        Map<String , String> map = new HashMap<>();
        if(!StringUtils.isEmpty(xmlStr)){
            Document doc = null;
            try {
                doc = DocumentHelper.parseText(xmlStr);
            } catch (DocumentException e) {
                logger.error("xml parser dom failure");
                e.printStackTrace();
            }
            String key = "";
            logger.info("start to read data from document");
            if(doc != null){
                Element root = doc.getRootElement();
                for(Iterator i = root.elementIterator(); i.hasNext();){
                    Element element = (Element)i.next();
                    key = element.getName();
                    String value = element.getText();
                    logger.info("key:"+key+",parameter:"+value);
                    map.put(key,value);
                }
            }
        }
        return map;
    }

    public static Object getObjectFromXML(String xml, Class tClass) {
        Object obj = null;
        if(!StringUtils.isEmpty(xml)){
            //将从API返回的XML数据映射到Java对象
            XStream xStreamForResponseData = new XStream();
            xStreamForResponseData.alias("xml", tClass);
            xStreamForResponseData.ignoreUnknownElements();//暂时忽略掉一些新增的字段
            obj = xStreamForResponseData.fromXML(xml);
        }
        return obj;
    }

}

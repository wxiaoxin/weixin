package com.wy.weixin.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.wy.weixin.model.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by wxiao on 2016.11.8.
 *
 * XML解析
 */

public class XMLUtil {

    /**
     * xml文件的输入流转map对象
     *
     * @param is XML文件输入流
     * @return 转换后的map对象
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlISToMap(InputStream is) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>();

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(is);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        for (Element e : elements) {
            map.put(e.getName(), e.getText());
        }
        is.close();
        return map;
    }

    /**
     * 对象转xml
     *
     * @param textMsg
     * @return
     */
    public static String textMsgToXml(TextMessage textMsg) {
        XStream xstream = new XStream();
        xstream.alias("xml", textMsg.getClass());
        return xstream.toXML(textMsg);
    }


    /**
     * map对象转xml字符串
     *
     * @param map
     * @return
     */
    public static String mapToXml(SortedMap map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if ("sign".equalsIgnoreCase(k)) {
                continue;
            }
            if ("detail".equalsIgnoreCase(k)) {
                sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
            } else {
                sb.append("<" + k + ">" + v + "</" + k + ">");
            }
        }
        sb.append("<sign>" + map.get("sign") + "</sign>");
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * map对象转xml字符串
     *
     * @param map
     * @return
     */
    public static String mapToXml2(TreeMap map) {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("xml", TreeMap.class);
        xStream.registerConverter(new MapEntryConverter());
        return xStream.toXML(map);
    }


    public static class MapEntryConverter implements Converter {

        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if (null != val) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }
        }

        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
            Map<String, String> map = new HashMap<>();
            while (reader.hasMoreChildren()) {
                reader.moveDown();
                String key = reader.getNodeName();
                String value = reader.getValue();
                map.put(key, value);
                reader.moveUp();
            }
            return map;
        }

    }

}

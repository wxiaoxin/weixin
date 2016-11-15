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
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * map对象转xml字符串
     *
     * @param map
     * @return
     */
    public static String mapToXml(Map map) {
        XStream xStream = new XStream(new DomDriver());
        xStream.alias("xml", Map.class);
        xStream.registerConverter(new MapEntryConverter());
        return xStream.toXML(map);
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

            Map<String, String> map = new HashMap<String, String>();

            while (reader.hasMoreChildren()) {
                reader.moveDown();

                String key = reader.getNodeName(); // nodeName aka element's name
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }

}

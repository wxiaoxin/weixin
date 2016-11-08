package com.wy.weixin.util;

import com.thoughtworks.xstream.XStream;
import com.wy.weixin.model.TextMessage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
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
     * @param is    XML文件输入流
     * @return      转换后的map对象
     * @throws IOException
     * @throws DocumentException
     */
    public static Map<String, String> xmlISToMap(InputStream is) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>();

        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(is);
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        for(Element e: elements) {
            map.put(e.getName(), e.getText());
        }
        is.close();
        return map;
    }


    /**
     * 对象转xml
     * @param textMsg
     * @return
     */
    public static String textMsgToXml(TextMessage textMsg) {
        XStream xstream = new XStream();
        xstream.alias("xml",textMsg.getClass());
        return xstream.toXML(textMsg);
    }


}

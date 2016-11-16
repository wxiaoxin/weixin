package com.wy.weixin.util;

import com.wy.weixin.constants.WeixinConfigConstant;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;

/**
 * Created by wxiao on 2016/11/16.
 *
 * 微信相关工具类
 */

public class WeixinUtils {

    private static Logger logger = Logger.getLogger(WeixinUtils.class);

    public static String sign(SortedMap<String, String> map) {
        StringBuffer sb = new StringBuffer();
        Iterator it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            String v = (String) entry.getValue();
            if(null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + WeixinConfigConstant.PAY_KEY);
        logger.debug(sb.toString());
        return MD5Util.MD5Encode(sb.toString()).toUpperCase();
    }

}

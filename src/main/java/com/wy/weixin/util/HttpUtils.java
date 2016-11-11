package com.wy.weixin.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;

/**
 * Created by wxiao on 2016.11.8.
 *
 * 请求封装工具类
 */

public class HttpUtils {


    public static String get(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);

        CloseableHttpResponse response = client.execute(httpGet);
        HttpEntity respEntity = response.getEntity();

        response.close();
        client.close();

        return EntityUtils.toString(respEntity);
    }

    public static String post(String url, String param) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        StringEntity entity = new StringEntity(param, "utf-8");
        entity.setContentType("application/json");
        httpPost.setEntity(entity);

        CloseableHttpResponse response = client.execute(httpPost);
        HttpEntity respEntity = response.getEntity();
        String result = EntityUtils.toString(respEntity);

        response.close();
        client.close();

        return result;
    }


}

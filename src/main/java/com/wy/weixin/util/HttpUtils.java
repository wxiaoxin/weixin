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
        InputStream is = respEntity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String s = "";
        while ((s = reader.readLine()) != null) {
            sbf.append(s);
        }
        reader.close();
        is.close();
        response.close();
        client.close();

        return sbf.toString();
    }

    public static String post(String url, String param) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);

        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");
        String encodeParam = URLEncoder.encode(param, "UTF-8");
        StringEntity entity = new StringEntity(encodeParam);
        entity.setContentType("text/json");
        entity.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
        httpPost.setEntity(entity);

        CloseableHttpResponse resp = client.execute(httpPost);
        HttpEntity respEntity = resp.getEntity();
        InputStream is = respEntity.getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuffer sbf = new StringBuffer();
        String s = "";
        while ((s = reader.readLine()) != null) {
            sbf.append(s);
        }
        reader.close();
        is.close();
        resp.close();
        client.close();

        return sbf.toString();
    }


}

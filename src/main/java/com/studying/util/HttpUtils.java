package com.studying.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;

public class HttpUtils {

    private static final String USER_AGENT = "com.studying";
    private static final int CONNECTION_TIMEOUT = 1000000 * 1000;
    private static final int SO_TIMEOUT = 3000000 * 1000;
    private static final int MAX_PER_ROUTE = 2;
    private static final int MAX_CON_TOTAL = 200;

    private static HttpClientBuilder httpClientBuilder = HttpClients.custom().setDefaultRequestConfig(
            RequestConfig.custom()
                    .setConnectTimeout(CONNECTION_TIMEOUT)
                    .setConnectionRequestTimeout(SO_TIMEOUT)
                    .build())
            .setMaxConnPerRoute(MAX_PER_ROUTE)
            .setMaxConnTotal(MAX_CON_TOTAL)
            .setUserAgent(USER_AGENT);

    private static HttpClient httpClient = httpClientBuilder.build();

    public static String post(String url, List<BasicNameValuePair> params) {
        HttpPost post = new HttpPost(url);
        try {
            HttpEntity urlEncodeEntity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(urlEncodeEntity);
            HttpResponse response = httpClient.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LoggerUtil.logger.error("post error, url : {}", post.getURI(), e);
        }
        return null;
    }

    public static String get(HttpGet get) {
        try {
            HttpResponse response = httpClient.execute(get);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LoggerUtil.logger.error("get error, url : {}", get.getURI(), e);
        }
        return null;
    }

    public static String post(HttpPost post) {
        try {
            HttpResponse response = httpClient.execute(post);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LoggerUtil.logger.error("post error, url : {}", post.getURI(), e);
        }
        return null;
    }

    public static String put(HttpPut put) {
        try {
            HttpResponse response = httpClient.execute(put);
            return EntityUtils.toString(response.getEntity());
        } catch (IOException e) {
            LoggerUtil.logger.error("put error", e);
        }
        return null;
    }

    public static byte[] getBytes(String url) {
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = httpClient.execute(get);
            return EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            LoggerUtil.logger.error("getBytes error, url : {}", url, e);
        }
        return null;
    }

}

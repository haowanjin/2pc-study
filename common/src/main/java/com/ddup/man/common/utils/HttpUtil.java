package com.ddup.man.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpUtil {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    static {
        REST_TEMPLATE.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
    }

    /**
     * 发送POST JSON请求
     *
     * @param url  请求地址
     * @param data 请求数据
     */
    public static JSONObject post(String url, Object data) {
        return post(url, data, JSONObject.class);
    }

    /**
     * 发送POST JSON请求
     *
     * @param url         请求地址
     * @param data        请求数据
     * @param returnClass 返回类型
     */
    public static <T> T post(String url, Object data, Class<T> returnClass) {
        String resultStr = REST_TEMPLATE.postForObject(url, data, String.class);
        return JSON.parseObject(resultStr, returnClass);
    }

    /**
     * 发送GET请求
     */
    public static <T> T get(String url, Class<T> returnClass) {
        String resultStr = REST_TEMPLATE.getForObject(URI.create(url), String.class);
        return JSON.parseObject(resultStr, returnClass);
    }


    /**
     * 发送post表单请求
     * <p>
     * 上传文件示例 {@code data.put("file", new FileSystemResource()));}
     */
    public static <T> T postFormData(String url, MultiValueMap<String, Object> data, Class<T> returnClass) {
        String resultStr = REST_TEMPLATE.postForObject(url, data, String.class);
        return JSON.parseObject(resultStr, returnClass);
    }


}

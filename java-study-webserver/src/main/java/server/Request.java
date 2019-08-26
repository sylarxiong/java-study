package main.java.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装请求协议
 */
public class Request {
    private String requestInfo;
    private String method;
    private String url;
    private Map<String, String> paramsMap;
    private String params;

    public Request(InputStream inputStream) {
        paramsMap = new HashMap<>();
        byte[] datas = new byte[1024 * 1024 * 1024];
        int len;
        try {
            len = inputStream.read(datas);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        requestInfo = new String(datas, 0, len);
        //分解
        parseRequest();

    }

    public Request() {

    }

    private void parseRequest() {
        System.out.println(requestInfo);
        System.out.println("1、获取请求方式：开头到第一个/");
        this.method = this.requestInfo.substring(0, this.requestInfo.indexOf("/")).trim();
        System.out.println("method:" + this.method);

        System.out.println("2、获取请求url：第一个/到HTTP/");
        int start = this.requestInfo.indexOf("/");
        int end = this.requestInfo.indexOf("HTTP/");
        String url = this.requestInfo.substring(start, end);
        int urlIndex = url.indexOf("?");
        //String params = "";
        if (urlIndex > 0) {
            String[] strs = url.split("\\?");
            this.url = url = strs[0];
            params = decode(strs[1], "UTF-8");
        }
        System.out.println("url:" + url);
        System.out.println("3、获取请求参数");
        if ("POST".equals(this.method)) {
            String p = this.requestInfo.substring(this.requestInfo.lastIndexOf("\r\n")).trim();
            System.out.println("Post参数:\n" + p);
        }
        System.out.println("params:" + params);
        if (params != null)
            convertParam();
    }

    public void convertParam() {
        String[] params = this.params.split("&");
        for (String param : params) {
            String[] kv = param.trim().split("=");
            kv = Arrays.copyOf(kv, 2);
            paramsMap.put(kv[0], kv[1]);
        }
    }

    public String getParameter(String key) {
        return paramsMap.get(key);
    }

    public String getUrl() {
        return this.url;
    }

    /**
     * 处理中文
     *
     * @return
     */
    private String decode(String value, String enc) {
        try {
            return java.net.URLDecoder.decode(value, enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}

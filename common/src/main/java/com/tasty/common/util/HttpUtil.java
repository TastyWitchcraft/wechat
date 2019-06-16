package com.tasty.common.util;

import com.tasty.common.constant.Constants;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @Auther: zhu.zexin
 * @Date: 2019/6/15
 */
@Slf4j
public class HttpUtil {

    /**
     * 发送GET请求
     * @param url
     * @param param 请求参数
     * @return 响应结果
     */
    public static String sendGet(String url, Map<String, String> param) throws IOException {
        log.info("发送http POST请求，请求参数url：{},请求参数：{}", url, param);
        StringBuilder result = new StringBuilder();
        InputStream in = null;
        try {
            if (!Utils.isEmpty(param)) {
                if(url.indexOf("?") == -1){
                    url += "?";
                } else if (url.endsWith("&")){
                    url += "&";
                }
                for (String key : param.keySet()) {
                    url += key + "=" + param.get(key) + "&";
                }
            }
            URL realUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("Accept-Charset", Constants.CHARSET_NAME);
            connection.setRequestProperty("contentType", Constants.CHARSET_NAME);
            connection.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            Map<String, List<String>> map = connection.getHeaderFields();
            for (String key : map.keySet()) {
                log.info("{} ----> {}", key, map.get(key));
            }
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                in = connection.getInputStream();
            } else {
                in = connection.getErrorStream();
            }
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                result.append(new String(buf, 0, len, Constants.CHARSET_NAME));
            }
        } catch (Exception e) {
            log.error("发送GET请求出现异常！" + e);
            throw e;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                log.error("get请求关闭异常，异常信息", e2);
            }
        }
        log.info("请求URL:{},返回值：{}", url, result.toString());
        return result.toString();
    }

    /**
     * 发送POST请求
     * @param url
     * @param param json格式
     * @return 响应结果
     */
    public static String sendPost(String url, String param) throws IOException {
        log.info("发送http POST请求，请求参数url：{},请求参数：{}", url, param);
        OutputStream out = null;
        InputStream in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Accept-Charset", Constants.CHARSET_NAME);
            conn.setRequestProperty("contentType", Constants.CHARSET_NAME);
            conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=" + Constants.CHARSET_NAME);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = conn.getOutputStream();
            out.write(param.getBytes());
            out.flush();
            int code = conn.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                in = conn.getInputStream();
            } else {
                in = conn.getErrorStream();
            }
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) != -1) {
                result.append(new String(buf, 0, len, Constants.CHARSET_NAME));
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            throw e;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        log.info("请求URL:{},返回值：{}", url, result.toString());
        return result.toString();
    }

}

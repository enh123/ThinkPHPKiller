package com.example.thinkphpgui.utils;

import com.example.thinkphpgui.ConfigManager;
import java.net.*;
import java.util.Map;



public class HttpRequestUtil {
    private static final ConfigManager configManager=ConfigManager.getInstance();

    public static HttpURLConnection get(String url) throws Exception {
        return request(url, "GET");
    }

    public static HttpURLConnection post(String url) throws Exception {
        return request(url, "POST");
    }

    private static HttpURLConnection request(String url, String method) throws Exception {
        if(configManager.isRunning!=null&& !configManager.isRunning){
            return null;
        }
        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setRequestMethod(method);
        httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setReadTimeout(60000);


        if (ConfigManager.headersMap != null
                && ConfigManager.enableHeaders!=null&&ConfigManager.enableHeaders
                && !ConfigManager.headersMap.isEmpty()) {
            for (Map.Entry<String, String> entry : ConfigManager.headersMap.entrySet()) {
                if (entry.getKey() != null && entry.getValue() != null) {
                    httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
        }

        return httpURLConnection;
    }
}
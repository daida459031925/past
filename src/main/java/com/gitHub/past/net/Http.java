package com.gitHub.past.net;

import cn.hutool.http.HttpRequest;
import cn.hutool.log.Log;
import org.apache.commons.lang.StringUtils;

import java.net.InetSocketAddress;
import java.net.Proxy;

public class Http {
    private static int socketTimeout = 1000;
    private static String proxyHost = "192.168.1.1";
    private static String proxyPort = "8080";

    // url:链接地址，params：填充在url中的参数， useProxy：是否使用代理
    // proxyHost：代理地址， proxyPort：代理端口号
    public static String httpGet(String url, String params, String useProxy) {
        String requestUrl = url;
        if (StringUtils.isNotBlank(params)) {
            requestUrl = url + "?" + params;
        }
        String respData = null;
        Log.get().info("httpGet req is 【{}】", params);
        HttpRequest httpRequest = HttpRequest.get(requestUrl).timeout(socketTimeout).header("token",
                "application/json");
        if ("Y".equalsIgnoreCase(useProxy)) {
            Log.get().info(String.format("使用代理"));
            httpRequest.setProxy(new Proxy(Proxy.Type.HTTP,
                    new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort))));
        }
        respData = httpRequest.execute().body();
        Log.get().info(String.format("HttpsUtil:httpGet | 请求信息：%s | 响应信息: %s", httpRequest.getUrl(), respData));
        return respData;
    }


    // url:链接地址，params：填充在url中的参数， sendBodyData：body， useProxy：是否使用代理
    // proxyHost：代理地址， proxyPort：代理端口号
    public static String httpPost(String url, String params, String sendBodyData, String useProxy) {
        String requestUrl = url;
        if (StringUtils.isNotBlank(params)) {
            requestUrl = url + "?" + params;
        }
        String respData = null;
        Log.get().info("httpPost req is 【{}】", sendBodyData);
        HttpRequest httpRequest = HttpRequest.post(requestUrl).timeout(socketTimeout).header("Content-Type", "application/json");
        if ("Y".equalsIgnoreCase(useProxy)) {
            Log.get().info(String.format("使用代理"));
            httpRequest.setProxy(new Proxy(Proxy.Type.HTTP,
                    new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort))));
        }
        if (StringUtils.isNotBlank(sendBodyData)) {
            httpRequest.body(sendBodyData);
        }
        respData = httpRequest.execute().body();
        Log.get().info(String.format("HttpsUtil:httpPost | 请求信息：%s | 响应信息: %s", httpRequest.getUrl(), respData));
        return respData;
    }
}

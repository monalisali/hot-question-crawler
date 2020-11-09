package utils;

import com.alibaba.fastjson.JSON;
import dto.ConnectDto;
import org.apache.commons.codec.Charsets;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class NetworkConnect {
    private static Properties properties = Helper.GetAppProperties();

    public static HttpsURLConnection sendHttpGet(String reqeustUrl) {
        HttpsURLConnection conn = null;
        boolean isUsingProx = Boolean.parseBoolean(properties.getProperty("isConnectedByProxy"));
        try {
            URL url = new URL(reqeustUrl);
            if (isUsingProx) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 58591));
                conn = (HttpsURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpsURLConnection) url.openConnection();
            }
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);
            conn.setRequestProperty("User-Agent", properties.getProperty("userAgent"));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static HttpsURLConnection createHttpConnection(ConnectDto connectDto) {
        HttpsURLConnection conn = null;
        try {
            URL url = new URL(connectDto.getRequestUrl());
            if (connectDto.isConnectedByProxy()) {
                Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 58591));
                conn = (HttpsURLConnection) url.openConnection(proxy);
            } else {
                conn = (HttpsURLConnection) url.openConnection();
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(connectDto.getMethod());
            conn.setRequestProperty("Content-Type", connectDto.getContentType());
            conn.setRequestProperty("Accept", connectDto.getAccept());
            conn.setRequestProperty("User-Agent", connectDto.getUserAgent());

            if (connectDto.getSource().equals(ConstantsHelper.NetworkConnectConstant.CONNTSOURCE_ZHIHU)) {
                conn.setRequestProperty("x-zse-83", connectDto.getxZse83());
                conn.setRequestProperty("x-zse-86", connectDto.getxZse86());
                conn.setRequestProperty("referer", connectDto.getRefer());
                conn.setRequestProperty("cookie", connectDto.getCookie());
            }

            if(connectDto.getSource().equals(ConstantsHelper.NetworkConnectConstant.CONNTSOURCE_JD_SearchProduct)){ conn.setRequestProperty("origin", connectDto.getOrigin());
                conn.setRequestProperty("cookie",connectDto.getCookie());
                conn.setRequestProperty("origin",connectDto.getOrigin());
                String jsonParms = JSON.toJSONString(connectDto.getJdSearchRequestDto());
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParms.getBytes(String.valueOf(Charsets.UTF_8));
                    os.write(input, 0, input.length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return conn;
    }


    public static String getHttpResponseLocation(String requestUrl) {
        String location = "";
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(requestUrl).openConnection();
            conn.setInstanceFollowRedirects(false);
            location = conn.getHeaderField("Location");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return location;
    }
}

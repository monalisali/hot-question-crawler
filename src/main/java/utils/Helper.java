package utils;

import dto.ConnectDto;
import modules.zhihu.ZhihuCrawler;
import sun.misc.BASE64Decoder;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Helper {
    private static Properties properties = Helper.GetAppProperties();

    public static Properties GetAppProperties() {
        Properties pro = null;
        try {
            pro = new Properties();
            FileInputStream in = new FileInputStream("./src/main/resources/app.properties");
            pro.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public static Properties getAppPropertiesByName(String propertyFileName) {
        Properties pro = null;
        try {
            pro = new Properties();
            FileInputStream in = new FileInputStream("./src/main/resources/" + propertyFileName);
            pro.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public static String getHttpsURLConnectionResponse(HttpsURLConnection connection) {
        StringBuilder sbResp = new StringBuilder();

        if (connection != null) {
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = rd.readLine()) != null) {
                    sbResp.append(line);
                }
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        }

        return sbResp.toString();
    }

    // 对字节数组字符串进行Base64解码并生成图片
    public static boolean SaveBase64ToImage(String imgStr, String imgFilePath) {
        if (imgStr.isEmpty()) // 图像数据为空
            return false;

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static String getProjectRootPath(){
        String projectPath = ZhihuCrawler.class.getResource("/").getPath();
        return projectPath.substring(0, projectPath.indexOf("target")).substring(1);
    }

    public static String getProjectOutputPath(){
        Properties properties = GetAppProperties();
        return getProjectRootPath() + properties.getProperty("questionOutputPath");
    }

    public static String setFileNameDateFormat(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss");
        return dateTime.format(formatter);
    }

    public static boolean checkNetworkConnection(){
        boolean cnnTest = false;
        String testUrl = "";
        //不用翻墙也可以访问：https://readhub.cn/topic/5bMmlAm75lD
        //翻墙才可以访问： https://www.google.com
        try {
            boolean isCnnByProxy = Boolean.parseBoolean(properties.getProperty("isConnectedByProxy"));
            testUrl = isCnnByProxy ? "https://www.google.com" : "https://readhub.cn/topic/5bMmlAm75lD";
            ConnectDto connectDto = new ConnectDto(testUrl, "GET"
                    , properties.getProperty("accept1")
                    , properties.getProperty("contentType1"), "", "", ""
            );
            HttpsURLConnection resp = NetworkConnect.createHttpConnection(connectDto);
            System.out.println("是否使用代理: " + isCnnByProxy);
            System.out.println("请求返回代码： " + resp.getResponseCode());
            if (resp.getResponseCode() == 200) {
                cnnTest = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cnnTest;
    }
}

package modules;

import dto.ConnectDto;
import dto.QuestionResultDto;
import dto.XZSE86Dto;
import org.apache.commons.codec.Charsets;
import utils.FileHelper;
import utils.Helper;
import utils.NetworkConnect;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Crawler {
    private static Properties properties = Helper.GetAppProperties();

    public static void main(String[] args) {
        System.out.println("网络测试开始");
        System.out.println("为了避免被百度屏蔽IP，需要使用代理，请确认做了已下操作：");
        System.out.println("1. 翻墙软件设置为了「全局模式」");
        System.out.println("2. 程序的app.properties文件中 isConnectedByProxy = true");
        System.out.println("3. createHttpConnection() 中的代理协议，IP，端口号与翻墙软件一致。而且代码中，必须使用HTTP模式进行设置");
        System.out.println("\r\n");

        boolean cnnTest = false;
        //不用翻墙也可以访问：https://readhub.cn/topic/5bMmlAm75lD"
        //翻墙才可以访问： https://www.google.com
        try {
            Properties p = Helper.GetAppProperties();
            if (p != null) {
                boolean isCnnByProxy = Boolean.parseBoolean(p.getProperty("isConnectedByProxy"));
                ConnectDto connectDto = new ConnectDto("https://readhub.cn/topic/5bMmlAm75lD"
                        , "GET"
                        , properties.getProperty("accept1")
                        , properties.getProperty("contentType1"),"","",""
                );
                HttpsURLConnection resp = NetworkConnect.createHttpConnection(connectDto);
                System.out.println("是否使用代理: " + isCnnByProxy);
                System.out.println("请求返回代码： " + resp.getResponseCode());
                if (resp.getResponseCode() == 200) {
                    cnnTest = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!cnnTest) {
            System.out.println("网络测试不通过");
            return;
        }

        System.out.println("网络测试通过，开始爬数据");
        System.out.println("**********************************开始***************************************");
        List<String> hotWords = FileHelper.ReadHotWords();
//        List<String> hotWords = new ArrayList<>();
//        hotWords.add("保温饭盒");
        System.out.println("一共有" + hotWords.size() + "个热词:");
        System.out.println(hotWords.toString());

        System.out.println("**************************通过知乎，爬取知乎问题********************************");
        List<XZSE86Dto> zhihuHotwords = FileHelper.ReadZhiHuHotWords();
        QuestionFromZhihu zhihu = new QuestionFromZhihu(zhihuHotwords);
        List<QuestionResultDto> zhihuQuestions = zhihu.getQuestion();
        StringBuilder zhihuStringBuilder = new StringBuilder();
        zhihuQuestions.forEach(x->zhihuStringBuilder.append(x.getLink() + "\r\n"));

        System.out.println("知乎，爬取问题链接，共有" + zhihuQuestions.size() + "个：");
        System.out.println(zhihuStringBuilder.toString());
        System.out.println();
        System.out.println("**************************通过知乎，爬取知乎问题********************************");


        System.out.println("**************************通过百度，爬取知乎问题********************************");
        QuestionFromBaidu baidu = new QuestionFromBaidu(hotWords, true);
        List<QuestionResultDto> allQuestion = baidu.getQuestion();
        StringBuilder printStringBuilder = new StringBuilder();
        allQuestion.forEach(x -> printStringBuilder.append(x.getLink() + "\r\n"));
        System.out.println("百度，爬取问题链接，共有" + allQuestion.size() + "个：");
        System.out.println(printStringBuilder.toString());
        System.out.println("**************************通过百度，爬取知乎问题********************************");



        System.out.println("**********************************结束***************************************");

    }
}

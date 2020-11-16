package modules.zhihu;

import dto.ConnectDto;
import dto.QuestionParseDto;
import dto.QuestionResultDto;
import dto.XZSE86Dto;
import org.apache.commons.codec.Charsets;
import utils.ConstantsHelper;
import utils.FileHelper;
import utils.Helper;
import utils.NetworkConnect;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ZhihuCrawler {
    private static Properties properties = Helper.GetAppProperties();

    public static void main(String[] args) {
        System.out.println("网络测试开始");
        System.out.println("为了避免被百度屏蔽IP，需要使用代理，请确认做了已下操作：");
        System.out.println("1. 翻墙软件设置为了「全局模式」");
        System.out.println("2. 程序的app.properties文件中 isConnectedByProxy = true");
        System.out.println("3. createHttpConnection() 中的代理协议，IP，端口号与翻墙软件一致。而且代码中，必须使用HTTP模式进行设置");
        System.out.println("\r\n");

        if (!Helper.checkNetworkConnection()) {
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

        System.out.println("\r\n");
        System.out.println("**************************通过知乎，爬取知乎问题  开始********************************");
        List<XZSE86Dto> zhihuHotwords = FileHelper.ReadZhiHuHotWords();
        QuestionFromZhihu zhihu = new QuestionFromZhihu(zhihuHotwords);
        List<QuestionResultDto> zhihuQuestions = zhihu.getQuestion();
        StringBuilder zhihuStringBuilder = new StringBuilder();
        zhihuQuestions.forEach(x -> zhihuStringBuilder.append(x.getLink() + "\r\n"));

        System.out.println("知乎，爬取问题链接，共有" + zhihuQuestions.size() + "个：");
        System.out.println(zhihuStringBuilder.toString());
        System.out.println();
        System.out.println("**************************通过知乎，爬取知乎问题  完成********************************");
        System.out.println("\r\n");


        System.out.println("**************************通过百度，爬取知乎问题  开始********************************");
        QuestionFromBaidu baidu = new QuestionFromBaidu(hotWords, true);
        List<QuestionResultDto> baiduQuestion = baidu.getQuestion();
        StringBuilder printStringBuilder = new StringBuilder();
        baiduQuestion.forEach(x -> printStringBuilder.append(x.getLink() + "\r\n"));
        System.out.println("百度，爬取问题链接，共有" + baiduQuestion.size() + "个：");
        System.out.println(printStringBuilder.toString());
        System.out.println("**************************通过百度，爬取知乎问题  完成********************************");
        System.out.println("\r\n");


        System.out.println("**************************解析所有的知乎问题，开始********************************");
        QuestionParse parse = createQuestionParseObj(baiduQuestion, zhihuQuestions);
        System.out.println("去重后，有待解析问题：" + parse.getQuestions().size() + "个");
        List<QuestionParseDto> questionContents = parse.getQuestionContent();
        System.out.println("一共完成：" + questionContents.size() + "个");

        System.out.println("**************************解析所有的知乎问题，结束********************************");
        String filePath = parse.saveQuestionResultToExcel(ConstantsHelper.CAETGORYNAME, questionContents);
        System.out.println("本次处理结果被保存到: " + filePath);

        System.out.println("**************************解析所有的知乎问题，开始********************************");
        System.out.println("**********************************结束***************************************");

    }

    private static QuestionParse createQuestionParseObj(List<QuestionResultDto> baiduQuestion, List<QuestionResultDto> zhihuQuestions) {
        QuestionParse parse = new QuestionParse();
        parse.setBiaduQuestion(baiduQuestion);
        parse.setZhihuQuestion(zhihuQuestions);
        List<QuestionResultDto> combines = new ArrayList<>();
        combines.addAll(baiduQuestion);
        combines.addAll(zhihuQuestions);
        List<QuestionResultDto> dist = combines.stream().distinct().collect(Collectors.toList());
        parse.setQuestions(dist);
        return parse;
    }
}

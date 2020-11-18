package modules.zhihu;

import dao.Dao;
import dto.ConnectDto;
import dto.QuestionParseDto;
import dto.QuestionResultDto;
import dto.XZSE86Dto;
import entity.HotWord;
import entity.TopCategory;
import org.apache.commons.codec.Charsets;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;
import utils.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class ZhihuCrawler {
    private static Properties properties = Helper.GetAppProperties();
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());
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
        List<TopCategory> topCategories = dao.selectAllActiveTopCategories();
        for (TopCategory top: topCategories
             ) {
            List<HotWord> crtHotWords = dao.selectHotWordsByTopCategoryId(top.getId());
            List<String> hotWords = crtHotWords.stream().map(x->x.getName()).collect(Collectors.toList());
            System.out.println("当前操作的品类名称：" + top.getName() + "   共有热词：" + hotWords.size());
            System.out.println(hotWords.toString());

            System.out.println("**************************通过百度，爬取知乎问题  开始********************************");
            QuestionFromBaidu baidu = new QuestionFromBaidu(hotWords, true);
            List<QuestionResultDto> baiduQuestion = baidu.getQuestion();
            StringBuilder printStringBuilder = new StringBuilder();
            baiduQuestion.forEach(x -> printStringBuilder.append(x.getLink() + "\r\n"));
            System.out.println("百度，爬取问题链接，共有" + baiduQuestion.size() + "个：");
            System.out.println(printStringBuilder.toString());
            System.out.println("**************************通过百度，爬取知乎问题  完成********************************");
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


            System.out.println("**************************解析所有的知乎问题，开始********************************");
            QuestionParse parse = createQuestionParseObj(baiduQuestion, zhihuQuestions);
            System.out.println("去重后，有待解析问题：" + parse.getQuestions().size() + "个");
            List<QuestionParseDto> questionContents = parse.getQuestionContent();
            System.out.println("一共完成：" + questionContents.size() + "个");

            System.out.println("**************************解析所有的知乎问题，结束********************************");
            String filePath = parse.saveQuestionResultToExcel(top.getName(), questionContents);
            System.out.println("本次处理结果被保存到: " + filePath);

            System.out.println("**************************解析所有的知乎问题，开始********************************");
            System.out.println("**********************************结束***************************************");
        }
    }

    private static QuestionParse createQuestionParseObj(List<QuestionResultDto> baiduQuestion, List<QuestionResultDto> zhihuQuestions) {
        QuestionParse parse = new QuestionParse();
        parse.setBiaduQuestion(baiduQuestion);
        parse.setZhihuQuestion(zhihuQuestions);
        List<QuestionResultDto> combines = new ArrayList<>();
        combines.addAll(baiduQuestion);
        combines.addAll(zhihuQuestions);
        List<QuestionResultDto> dist = combines.stream().distinct().filter(x->x.getLink().indexOf("https") >= 0).collect(Collectors.toList());
        parse.setQuestions(dist);
        return parse;
    }
}

package modules.zhihu;

import dao.Dao;
import entity.HotWord;
import entity.TopCategory;
import org.apache.log4j.Logger;
import utils.*;
import java.util.*;
import java.util.stream.Collectors;

public class ZhihuCrawler {
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());
    private static Logger logger = Logger.getLogger(ZhihuCrawler.class);
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
        List<TopCategory> topCategories = dao.selectAllActiveTopCategories();
        for (TopCategory top : topCategories
        ) {
            List<HotWord> crtHotWords = dao.selectHotWordsByTopCategoryId(top.getId());
            List<String> hotWords = crtHotWords.stream().map(HotWord::getName).collect(Collectors.toList());
            System.out.println("当前操作的品类名称：" + top.getName() + "   共有热词：" + hotWords.size());
            System.out.println(hotWords.toString());

            System.out.println("**************************通过知乎，爬取知乎问题  开始********************************");
            QuestionFromZhihu zhihu = new QuestionFromZhihu(top);
            zhihu.getQuestion();
            System.out.println("**************************通过知乎，爬取知乎问题  完成********************************");


            System.out.println("**************************通过百度，爬取知乎问题  开始********************************");
            QuestionFromBaidu baidu = new QuestionFromBaidu(hotWords, true);
            baidu.getQuestion();
            System.out.println("**************************通过百度，爬取知乎问题  完成********************************");


            System.out.println("**************************解析所有的知乎问题，开始********************************");
            QuestionParse parse = new QuestionParse(top);
            parse.ParseAndCalcuateQuestion();
            System.out.println("**************************解析所有的知乎问题，开始********************************");


            System.out.println("**********************************结束***************************************");
        }
    }
}

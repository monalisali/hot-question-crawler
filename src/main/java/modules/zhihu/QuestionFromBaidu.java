package modules.zhihu;

import dao.Dao;
import dto.ConnectDto;
import dto.QuestionResultDto;
import entity.HotWord;
import entity.Question;
import org.apache.commons.codec.Charsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.ConstantsHelper;
import utils.DatabaseHelp;
import utils.Helper;
import utils.NetworkConnect;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;


public class QuestionFromBaidu implements IQuestion {
    private static Properties properties = Helper.GetAppProperties();
    private static String _baiduUrlPrefix = properties.getProperty("baiduUrlPrefix");
    private static String _zhihuSpecificSite = properties.getProperty("zhihuSpecificSite");
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());

    //搜索时，是否加上site:www.zhihu.com
    private Boolean isSearchFromZhihuOnly;
    private List<String> hotWordList;

    public QuestionFromBaidu(List<String> hotWords, Boolean isSearchFromZhihuOnly) {
        this.hotWordList = hotWords;
        this.isSearchFromZhihuOnly = isSearchFromZhihuOnly;
    }

    public List<String> getHotWordList() {
        return hotWordList;
    }

    public void setHotWordList(List<String> hotWordList) {
        this.hotWordList = hotWordList;
    }

    public Boolean getSearchFromZhihuOnly() {
        return isSearchFromZhihuOnly;
    }


    @Override
    public List<QuestionResultDto> getQuestion() {
        List<QuestionResultDto> pagedHtmlList = new ArrayList<>();
        List<QuestionResultDto> result = new ArrayList<>();
        List<QuestionResultDto> zhiHuQuestions = new ArrayList<>();
        Properties pro = Helper.GetAppProperties();
        if (pro != null) {
            int count = 1;
            for (String q : this.getHotWordList()
            ) {
                HotWord crtHotWord = dao.selectHotWordByName(q);
                //isDone = 0 时才获取Question
                if(crtHotWord.getDone() == null || !crtHotWord.getDone()){
                    List<QuestionResultDto> validResults = new ArrayList<>();
                    List<QuestionResultDto> crtQuestions = getQuestion(sendHttpGetRequest(q));
                    List<Question> existedQuestions = dao.selectQuestionsByHotWordId(crtHotWord.getId());
                    for (QuestionResultDto c:crtQuestions
                         ) {
                        Optional<Question> chk = existedQuestions.stream().filter(x->x.getUrl().equals(c.getLink())).findFirst();
                        if(!chk.isPresent()){
                            result.add(c);
                            validResults.add(c);
                        }
                    }
                    dbProcessAfterGetQuestion(crtHotWord,validResults);
                    existedQuestions.forEach(x->result.add(Helper.convertQuestionToQuestionResultDto(x)));
                    System.out.println("第" + (count++) + "个热词完成：" + q);
                    try {
                        Thread.currentThread().sleep(20000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    List<Question> getQuestions = dao.selectQuestionsByHotWordId(crtHotWord.getId());
                    getQuestions.forEach(x->result.add(Helper.convertQuestionToQuestionResultDto(x)));
                    System.out.println("Question已经获取过了，第" + (count++) + "个热词完成：" + q);
                }
            }
        }
        return result;
    }

    private List<QuestionResultDto> sendHttpGetRequest(String keyword) {
        List<QuestionResultDto> results = new ArrayList<>();
        try {
            for (int i = ConstantsHelper.PageHelper.STARTINDEX; i <= ConstantsHelper.PageHelper.MAXPAGENUM; i++) {
                QuestionResultDto pagedResult = new QuestionResultDto();
                int pn = i * ConstantsHelper.PageHelper.PAGESIZE;
                keyword = isSearchFromZhihuOnly ? keyword + " " + _zhihuSpecificSite : keyword;
                String keyEncode = Helper.replacePlusFromUrlEncode(URLEncoder.encode(keyword, String.valueOf(Charsets.UTF_8)));
                StringBuilder sb = new StringBuilder(_baiduUrlPrefix);
                sb.append("&wd=");
                sb.append(keyEncode);
                sb.append("&pn=");
                sb.append(pn);
                sb.append("&bs=");
                sb.append(keyEncode);

                ConnectDto connectDto = new ConnectDto(sb.toString()
                        , "GET"
                        , properties.getProperty("accept1")
                        , properties.getProperty("contentType1")
                        , "","","");
                HttpsURLConnection conn = NetworkConnect.createHttpConnection(connectDto);
                if (conn != null) {
                    String sbResp = Helper.getHttpsURLConnectionResponse(conn);
                    pagedResult.setPageIndex(i + 1);
                    pagedResult.setPagedHtmlResponse(sbResp);
                    results.add(pagedResult);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    private List<QuestionResultDto> parsePagedHtml(List<QuestionResultDto> pagedHmls) {
        List<QuestionResultDto> result = new ArrayList<>();
        for (int i = 0; i < pagedHmls.size(); i++) {
            QuestionResultDto html = pagedHmls.get(i);
            Document document = Jsoup.parse(html.getPagedHtmlResponse());
            Elements elements = document.getElementsByTag("h3");
            for (Element el : elements
            ) {
                Element link = el.select("a").first();
                String href = link.attr("href");
                if (!href.isEmpty()) {
                    QuestionResultDto temp = new QuestionResultDto();
                    temp.setLinkIndex(i + 1);
                    temp.setDeCodeLink(href);
                    result.add(temp);
                }
            }
        }
        return result;
    }

    private void cleanLink(List<QuestionResultDto> links) {
        for (QuestionResultDto q : links
        ) {
            if (q.getLink().contains("/answer")) {
                q.setLink(q.getLink().substring(0, q.getLink().indexOf("/answer")));
            }
        }
    }

    private List<QuestionResultDto> getQuestion(List<QuestionResultDto> list){
        List<QuestionResultDto> links = parsePagedHtml(list);
        //解析百度加密过的知乎链接，并赋值给属性
        links.forEach(x -> x.setLink(NetworkConnect.getHttpResponseLocation(x.getDeCodeLink())));
        //只保留链接中有question的链接
        List<QuestionResultDto> zhiHuQuestions = links.stream()
                .filter(x -> x.getLink() != null && x.getLink().contains("/question/")).collect(Collectors.toList());
        cleanLink(zhiHuQuestions);
        zhiHuQuestions.forEach(x -> x.getLink().trim());
        return zhiHuQuestions.stream().distinct().collect(Collectors.toList());
    }

    //此时只能拿到question url，没有名称。名称在最后合并zhihu、baidu所有问题后，再去解析时获取
    private Question createQuestionObj(HotWord hotWord, QuestionResultDto questionResult){
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setHotWordId(hotWord.getId());
        question.setUrl(questionResult.getLink());
        question.setSource(ConstantsHelper.Question.QuestionSource_Baidu);
        question.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return question;
    }

    private void dbProcessAfterGetQuestion(HotWord crtHotWord,List<QuestionResultDto> crtQuestions){
        List<Question> questions = new ArrayList<>();
        crtHotWord.setDone(true);
        dao.updateHotWord(crtHotWord);

        if(crtQuestions.size() > 0){
            crtQuestions.stream().forEach(x-> questions.add(createQuestionObj(crtHotWord,x)));
            dao.batchInsertQuestions(questions);
        }

    }
}



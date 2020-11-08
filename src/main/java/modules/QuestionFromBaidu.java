package modules;

import dto.ConnectDto;
import dto.QuestionResultDto;
import org.apache.commons.codec.Charsets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.ConstantsHelper;
import utils.Helper;
import utils.NetworkConnect;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class QuestionFromBaidu implements IQuestion {
    private static Properties properties = Helper.GetAppProperties();
    private static String _baiduUrlPrefix = properties.getProperty("baiduUrlPrefix");
    private static String _zhihuSpecificSite = properties.getProperty("zhihuSpecificSite");

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
            for (String q : this.getHotWordList()
            ) {
                pagedHtmlList.addAll(sendHttpGetRequest(q));
                try {
                    Thread.currentThread().sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<QuestionResultDto> links = parsePagedHtml(pagedHtmlList);
            //解析百度加密过的知乎链接，并赋值给属性
            links.forEach(x -> x.setLink(NetworkConnect.getHttpResponseLocation(x.getDeCodeLink())));
            //只保留链接中有question的链接
            zhiHuQuestions = links.stream().filter(x -> x.getLink().contains("/question/")).collect(Collectors.toList());
            cleanLink(zhiHuQuestions);
            zhiHuQuestions.forEach(x -> x.getLink().trim());
            result = zhiHuQuestions.stream().distinct().collect(Collectors.toList());
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
                String keyEncode = URLEncoder.encode(keyword, String.valueOf(Charsets.UTF_8));
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
}



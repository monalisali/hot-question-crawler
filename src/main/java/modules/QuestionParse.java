package modules;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import dto.QuestionParseDto;
import dto.QuestionResultDto;
import dto.XZSE86Dto;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.Helper;
import utils.NetworkConnect;

import javax.net.ssl.HttpsURLConnection;
import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionParse {
    private List<QuestionResultDto> biaduQuestion;
    private List<QuestionResultDto> zhihuQuestion;
    private List<QuestionResultDto> questions;


    public List<QuestionParseDto> getQuestionContent() {
        List<QuestionParseDto> results = new ArrayList<>();
        List<String> allQuestionsUrl = this.getQuestions().stream().map(x -> x.getLink()).collect(Collectors.toList());
        for (String url : allQuestionsUrl
        ) {
            HttpsURLConnection conn = NetworkConnect.sendHttpGet(url);
            String response = Helper.getHttpsURLConnectionResponse(conn);
            if (!response.isEmpty()) {
                Document document = Jsoup.parse(response);
                QuestionParseDto parseDto = parseHtml(document);
                parseDto.setQuestionUrl(url);
                results.add(parseDto);
                System.out.println("解析完成：" + parseDto.getQuestionUrl());
            }
        }
        return results;
    }


    private QuestionParseDto parseHtml(Document document) {
        QuestionParseDto resultDto = new QuestionParseDto();
        Elements bodys = document.getElementsByTag("body");
        if (bodys != null && bodys.size() > 0) {
            Element body = bodys.get(0);
            Element header = body.getElementsByClass("QuestionHeader-side").get(0);
            Elements numberBoders = header.getElementsByClass("NumberBoard-itemInner");
            if (numberBoders != null && numberBoders.size() > 0) {
                String followerNumTxt = numberBoders.first().child(1).text();
                String browserNumerTxt = numberBoders.last().child(1).text();
                if (!followerNumTxt.isEmpty()) {
                    resultDto.setFollowCount(Integer.parseInt(followerNumTxt.replace(",", "")));
                }

                if (!browserNumerTxt.isEmpty()) {
                    resultDto.setBrowseCount(Integer.parseInt(browserNumerTxt.replace(",", "")));
                }
            }
        }

        return resultDto;
    }

    public List<QuestionResultDto> getBiaduQuestion() {
        return biaduQuestion;
    }

    public void setBiaduQuestion(List<QuestionResultDto> biaduQuestion) {
        this.biaduQuestion = biaduQuestion;
    }

    public List<QuestionResultDto> getZhihuQuestion() {
        return zhihuQuestion;
    }

    public void setZhihuQuestion(List<QuestionResultDto> zhihuQuestion) {
        this.zhihuQuestion = zhihuQuestion;
    }

    public List<QuestionResultDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionResultDto> questions) {
        this.questions = questions;
    }


}

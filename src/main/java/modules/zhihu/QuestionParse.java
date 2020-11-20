package modules.zhihu;


import dao.Dao;
import dto.QuestionParseDto;
import dto.QuestionResultDto;
import entity.CombinedQuestion;
import entity.Question;
import entity.TopCategory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.DatabaseHelp;
import utils.Helper;
import utils.NetworkConnect;
import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class QuestionParse {
    private static Properties properties = Helper.GetAppProperties();
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());
    private List<QuestionResultDto> biaduQuestion;
    private List<QuestionResultDto> zhihuQuestion;
    private List<QuestionResultDto> questions;
    private TopCategory topCategory;

    public QuestionParse(TopCategory topCategory){
        this.topCategory = topCategory;
    }

    public List<QuestionParseDto> getQuestionContent() {
        insertCombinedQuestion();
        List<QuestionParseDto> results = new ArrayList<>();
        List<String> allQuestionsUrl = this.getQuestions().stream().map(x -> x.getLink()).collect(Collectors.toList());
        int count = 1;
        for (String url : allQuestionsUrl
        ) {
            HttpsURLConnection conn = NetworkConnect.sendHttpGet(url);
            String response = Helper.getHttpsURLConnectionResponse(conn);
            if (!response.isEmpty()) {
                Document document = Jsoup.parse(response);
                QuestionParseDto parseDto = parseHtml(document);
                parseDto.setQuestionUrl(url);
                results.add(parseDto);
                System.out.println("第"+ (count++) +"个解析完成：" + parseDto.getQuestionUrl());
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }


    public String saveQuestionResultToExcel(String saveFileCategoryName, List<QuestionParseDto> questions) {
        //String projectPath = ZhihuCrawler.class.getResource("/").getPath();
        //String projectRoot = projectPath.substring(0, projectPath.indexOf("target")).substring(1);
        //String parentFolder = projectRoot + properties.getProperty("questionOutputPath") + saveFileCategoryName;
        String parentFolder =  properties.getProperty("questionOutputPath") + saveFileCategoryName;
        String fileFullPath = setSaveFileFullPath(saveFileCategoryName, parentFolder);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("知乎问题解析内容");
        //设置表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("问题名称");
        cell = row.createCell(1);
        cell.setCellValue("问题链接");
        cell = row.createCell(2);
        cell.setCellValue("关注人数");
        cell = row.createCell(3);
        cell.setCellValue("浏览次数");
        sheet.setColumnWidth(0, 50 * 256);
        sheet.setColumnWidth(1, 50 * 256);

        for (QuestionParseDto q : questions
        ) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(q.getTitle());
            dataRow.createCell(1).setCellValue(q.getQuestionUrl());
            dataRow.createCell(2).setCellValue(q.getFollowCount());
            dataRow.createCell(3).setCellValue(q.getBrowseCount());
        }

        File excelParentFolder = new File(parentFolder);
        if (!excelParentFolder.exists()) {
            excelParentFolder.mkdir();
        }
        File xlsFile = new File(fileFullPath);

        try {
            // 或者以流的形式写入文件 workbook.write(new FileOutputStream(xlsFile));
            workbook.write(xlsFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileFullPath;
    }

    private static String setSaveFileFullPath(String saveFileCategoryName, String parentFolder) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss");
        return parentFolder + "/" + saveFileCategoryName + "_" + dateTime.format(formatter) + ".xls";
    }

    private QuestionParseDto parseHtml(Document document) {
        QuestionParseDto resultDto = new QuestionParseDto();
        Elements bodys = document.getElementsByTag("body");
        if (bodys != null && bodys.size() > 0) {
            Element body = bodys.get(0);
            Element header = body.getElementsByClass("QuestionHeader-side").get(0);
            Elements numberBoders = header.getElementsByClass("NumberBoard-itemInner");
            Elements titles = body.getElementsByClass("QuestionHeader-title");

            if (titles != null && titles.size() > 0) {
                Element title = titles.first();
                resultDto.setTitle(title.text());
            }

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

    private void insertCombinedQuestion(){
        List<CombinedQuestion> existedCombinedQuestions = dao.selectCombinedQuestion(this.getTopCategory().getId());
        List<Question> currectQuestions = dao.selectQuestionByTopCategory(this.getTopCategory().getId());
        for (Question q: currectQuestions
             ) {
            Optional<CombinedQuestion> chk = existedCombinedQuestions.stream()
                    .filter(x->x.getHotWordId().equals(q.getHotWordId()) && x.getUrl().equals(q.getUrl()))
                    .findFirst();
            if(!chk.isPresent()){
                CombinedQuestion cq = new CombinedQuestion();
                cq.setId(UUID.randomUUID().toString());
                cq.setTopCategoryID(this.getTopCategory().getId());
                cq.setHotWordId(q.getHotWordId());
                cq.setUrl(q.getUrl());
                cq.setCreateTime(new Timestamp(System.currentTimeMillis()));
                dao.insertCombinedQuestion(cq);
            }
        }
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

    public TopCategory getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(TopCategory topCategory) {
        this.topCategory = topCategory;
    }
}

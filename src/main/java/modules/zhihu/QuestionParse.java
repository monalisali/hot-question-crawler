package modules.zhihu;


import dao.Dao;
import dto.ParseQuestionExcelDto;
import dto.QuestionContentDto;
import dto.QuestionParseDto;
import entity.CombinedQuestion;
import entity.Question;
import entity.QuestionContent;
import entity.TopCategory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class QuestionParse {
    private static Properties properties = Helper.GetAppProperties();
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());
    private List<CombinedQuestion> questions;
    private TopCategory topCategory;

    public QuestionParse(TopCategory topCategory) {
        this.topCategory = topCategory;
    }

    public void ParseAndCalcuateQuestion() {
        getQuestionContent();
        List<QuestionContentDto> compareResults = compareQuestionContent();
        List<ParseQuestionExcelDto> calculateResults = calculateQuestionResult(compareResults);
        saveQuestionResultToExcel(calculateResults);
    }

    private List<QuestionParseDto> getQuestionContent() {
        prepareBeforeParse();
        List<QuestionParseDto> results = new ArrayList<>();
        int count = 1;
        System.out.println(this.getTopCategory().getName() + ", 去重后有待解析问题：" + this.getQuestions().size() + "个");
        for (CombinedQuestion cq : this.getQuestions()
        ) {
            HttpsURLConnection conn = NetworkConnect.sendHttpGet(cq.getUrl());
            String response = Helper.getHttpsURLConnectionResponse(conn);
            if (!response.isEmpty()) {
                Document document = Jsoup.parse(response);
                QuestionParseDto parseDto = parseHtml(document);
                parseDto.setQuestionUrl(cq.getUrl());
                cq.setName(parseDto.getTitle());
                results.add(parseDto);
                dao.insertQuestionContentSingle(createQuestionContentObj(cq,parseDto));
                //主要用来更新CombinedQuestion中Name字段（只有解析后，才能知道问题的名称）
                dao.updateCombinedQuestion(cq);
                System.out.println("第" + (count++) + "个解析完成：" + parseDto.getQuestionUrl());
                if (count <= this.getQuestions().size()) {
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println(this.getTopCategory().getName() + "， 一共解析问题：" + results.size() + "个");
        return results;
    }


    private String saveQuestionResultToExcel(String saveFileCategoryName, List<QuestionParseDto> questions) {
        //String projectPath = ZhihuCrawler.class.getResource("/").getPath();
        //String projectRoot = projectPath.substring(0, projectPath.indexOf("target")).substring(1);
        //String parentFolder = projectRoot + properties.getProperty("questionOutputPath") + saveFileCategoryName;
        String parentFolder = properties.getProperty("questionOutputPath") + saveFileCategoryName;
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

    private String saveQuestionResultToExcel(List<ParseQuestionExcelDto> list) {
        String parentFolder = properties.getProperty("questionOutputPath") + this.getTopCategory().getName();
        String fileFullPath = setSaveFileFullPath(this.getTopCategory().getName(), parentFolder);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("知乎问题解析内容");
        //设置表头
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("品类");
        cell = row.createCell(1);
        cell.setCellValue("问题名称");
        cell = row.createCell(2);
        cell.setCellValue("问题链接");
        cell = row.createCell(3);
        cell.setCellValue("浏览数增量");
        cell = row.createCell(4);
        cell.setCellValue("关注人增量");
        cell = row.createCell(5);
        cell.setCellValue("时间间隔(天)");
        cell = row.createCell(6);
        cell.setCellValue("老流量数");
        cell = row.createCell(7);
        cell.setCellValue("老关注人数");
        cell = row.createCell(8);
        cell.setCellValue("老创建时间");
        cell = row.createCell(9);
        cell.setCellValue("新流量数");
        cell = row.createCell(10);
        cell.setCellValue("新关注人数");
        cell = row.createCell(11);
        cell.setCellValue("新创建时间");

        for (int i = 0; i < row.getLastCellNum(); i++) {
            sheet.setColumnWidth(i, 15 * 256);
        }

        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 50 * 256);
        sheet.setColumnWidth(8, 25 * 256);
        sheet.setColumnWidth(11, 25 * 256);

        for (ParseQuestionExcelDto q : list
        ) {
            XSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(q.getTopCategoryName());
            dataRow.createCell(1).setCellValue(q.getQuestionName());
            dataRow.createCell(2).setCellValue(q.getQuestionUrl());
            dataRow.createCell(3).setCellValue(q.getDiffBrowserCount().intValue());
            dataRow.createCell(4).setCellValue(q.getDiffFollowerCount().intValue());
            dataRow.createCell(5).setCellValue(q.getDiffCreateTime());
            dataRow.createCell(6).setCellValue(q.getOldBrowserCount().intValue());
            dataRow.createCell(7).setCellValue(q.getOldFollowerCount().intValue());
            dataRow.createCell(8).setCellValue(q.getOldCreateTime().toString());
            dataRow.createCell(9).setCellValue(q.getNewBrowserCount().intValue());
            dataRow.createCell(10).setCellValue(q.getNewFollowerCount().intValue());
            dataRow.createCell(11).setCellValue(q.getNewCreateTime().toString());
        }

        File excelParentFolder = new File(parentFolder);
        if (!excelParentFolder.exists()) {
            excelParentFolder.mkdir();
        }
        File excelFile = new File(fileFullPath);
        try {
            workbook.write(new FileOutputStream(excelFile));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println(this.getTopCategory().getName() + "比较结果被保存到：" + fileFullPath);
        return fileFullPath;
    }

    //找到每个问题中待比较的两个数据
    private List<QuestionContentDto> compareQuestionContent() {
        List<QuestionContentDto> qcMinMaxList = new ArrayList<>();
        List<QuestionContentDto> questionContents = dao.selectQuestionContents(this.getTopCategory().getId());
        ;
        Map<String, List<QuestionContentDto>> groupCombinedQuestions = questionContents.stream().collect(Collectors.groupingBy(QuestionContentDto::getCombinedQuestionId));
        for (String key : groupCombinedQuestions.keySet()) {
            QuestionContentDto dto = new QuestionContentDto();
            List<QuestionContentDto> crtList = groupCombinedQuestions.get(key);
            Optional<QuestionContentDto> maxContent = crtList.stream().max(Comparator.comparing(QuestionContentDto::getCreateTime));
            Optional<QuestionContentDto> minContent = crtList.stream().min(Comparator.comparing(QuestionContentDto::getCreateTime));
            if (maxContent.isPresent() && minContent.isPresent()) {
                dto.setCombinedQuestionId(key);
                dto.setComparsionMinContent(getNearestByDate(crtList, maxContent.get(), minContent.get(), EnumMinMax.Min));
                dto.setComparsionMaxContent(getNearestByDate(crtList, maxContent.get(), minContent.get(), EnumMinMax.Max));
                qcMinMaxList.add(dto);
            }
        }

        return qcMinMaxList;
    }

    private List<ParseQuestionExcelDto> calculateQuestionResult(List<QuestionContentDto> list) {
        List<ParseQuestionExcelDto> results = new ArrayList<>();
        for (QuestionContentDto q : list
        ) {
            ParseQuestionExcelDto dto = new ParseQuestionExcelDto();
            dto.setId(UUID.randomUUID().toString());
            dto.setTopCategoryName(q.getComparsionMinContent().getTopCateogryName());
            dto.setQuestionName(q.getComparsionMinContent().getQuestionName());
            dto.setQuestionUrl(q.getComparsionMinContent().getQuestionUrl());
            dto.setOldBrowserCount(q.getComparsionMinContent().getBrowserCount());
            dto.setOldFollowerCount(q.getComparsionMinContent().getFollowerCount());
            dto.setOldCreateTime(q.getComparsionMinContent().getCreateTime());
            dto.setNewBrowserCount(q.getComparsionMaxContent().getBrowserCount());
            dto.setNewFollowerCount(q.getComparsionMaxContent().getFollowerCount());
            dto.setNewCreateTime(q.getComparsionMaxContent().getCreateTime());
            dto.setDiffBrowserCount(q.getComparsionMaxContent().getBrowserCount().subtract(q.getComparsionMinContent().getBrowserCount()));
            dto.setDiffFollowerCount(q.getComparsionMaxContent().getFollowerCount().subtract(q.getComparsionMinContent().getFollowerCount()));
            long diff = q.getComparsionMaxContent().getCreateTime().getTime() - q.getComparsionMinContent().getCreateTime().getTime();
            dto.setDiffCreateTime((int) (diff / (1000 * 60 * 60 * 24)));
            results.add(dto);
        }

        return results;
    }


    private String setSaveFileFullPath(String saveFileCategoryName, String parentFolder) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        return parentFolder + "/" + saveFileCategoryName + "_" + dateTime.format(formatter) + ".xlsx";
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

    //比较dbo.Question 与 dbo.CombinedQuestion中的记录，把新增的Question添加到dbo.CombinedQuestion
    private void insertCombinedQuestion() {
        List<CombinedQuestion> existedCombinedQuestions = dao.selectCombinedQuestion(this.getTopCategory().getId());
        List<Question> currectQuestions = dao.selectQuestionByTopCategory(this.getTopCategory().getId());
        for (Question q : currectQuestions
        ) {
            Optional<CombinedQuestion> chk = existedCombinedQuestions.stream()
                    .filter(x -> x.getHotWordId().equals(q.getHotWordId()) && x.getUrl().equals(q.getUrl()))
                    .findFirst();
            if (!chk.isPresent()) {
                CombinedQuestion cq = new CombinedQuestion();
                cq.setId(UUID.randomUUID().toString());
                cq.setTopCategoryId(this.getTopCategory().getId());
                cq.setHotWordId(q.getHotWordId());
                cq.setUrl(q.getUrl());
                cq.setCreateTime(new Timestamp(System.currentTimeMillis()));
                dao.insertCombinedQuestion(cq);
            }
        }
    }

    private void prepareBeforeParse() {
        insertCombinedQuestion();
        this.setQuestions(dao.selectCombinedQuestion(this.getTopCategory().getId()));
    }

    private QuestionContent createQuestionContentObj(CombinedQuestion cq, QuestionParseDto dto) {
        QuestionContent questionContent = new QuestionContent();
        questionContent.setId(UUID.randomUUID().toString());
        questionContent.setCombinedQuestionId(cq.getId());
        questionContent.setBrowserCount(BigInteger.valueOf(dto.getBrowseCount()));
        questionContent.setFollowerCount(BigInteger.valueOf(dto.getFollowCount()));
        questionContent.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return questionContent;
    }

    //获取离指定日期最近的那个离这个日期最近的QuestionContent
    //minOrMax = EnumMinMax.Min 获取两者比较时，日期在前的内容
    //minOrMax = EnumMinMax.Max 获取两者比较时，日期在后的内容
    private QuestionContentDto getNearestByDate(List<QuestionContentDto> list, QuestionContentDto crtMaxContent
            , QuestionContentDto crtMinContent, EnumMinMax minOrMax) {
        QuestionContentDto result = new QuestionContentDto();
        //日期在前的计算开始日期：startDate, 如：startDate = 2020-11-21，那么计算开始时间为：2020-11-21 00:00:00
        //日期在后的计算开始日期：startDate + compareOffset 00:00:00, 如：startDate = 2020-11-21, compareOffset = 1, 那么计算开始日期为：2020-11-22 00:00:00
        LocalDateTime localDateStartDate = minOrMax.equals(EnumMinMax.Min) ? LocalDate.parse(properties.getProperty("startDate")).atTime(LocalTime.MIDNIGHT)
                : (LocalDate.parse(properties.getProperty("startDate")).plusDays(Integer.parseInt(properties.getProperty("compareOffset")))).atTime(LocalTime.MIDNIGHT);
        Timestamp startDate = Timestamp.valueOf(localDateStartDate);


        if (startDate.compareTo(crtMinContent.getCreateTime()) <= 0
                || startDate.compareTo(crtMaxContent.getCreateTime()) >= 0) { //startDate在minDate,maxData 之外
            result = minOrMax.equals(EnumMinMax.Min) ? crtMinContent : crtMaxContent;
        } else { //startDate在minDate,maxData之间
            list.sort(Comparator.comparing(QuestionContentDto::getCreateTime));
            for (QuestionContentDto q : list
            ) {
                if (q.getCreateTime().compareTo(startDate) >= 0) {
                    result = q;
                    break;
                }
            }
        }

        return result;
    }

    public List<CombinedQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<CombinedQuestion> questions) {
        this.questions = questions;
    }

    public TopCategory getTopCategory() {
        return topCategory;
    }

    public void setTopCategory(TopCategory topCategory) {
        this.topCategory = topCategory;
    }
}

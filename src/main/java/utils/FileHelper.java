package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import dto.QuestionParseDto;
import dto.XZSE86Dto;
import modules.Crawler;
import org.apache.commons.codec.Charsets;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class FileHelper {
    //文件中只放入需要查询回答的热词，历史记录不要放
    private static Properties properties = Helper.GetAppProperties();
    private static String _hotWordsFilePath = properties.getProperty("hotWordsFilePath");
    private static String _zhiHuHotwordsObjPath = properties.getProperty("zhiHuHotwordsObjPath");

    public static List<String> ReadHotWords(){
        List<String> hotWords = new ArrayList<>();
        Path path = Paths.get(_hotWordsFilePath);
        try {
            hotWords =  Files.readAllLines(path, Charsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hotWords;
    }

    public static List<XZSE86Dto> ReadZhiHuHotWords(){
        List<XZSE86Dto> xZSE86ValueList = new ArrayList<>();
        Path path = Paths.get(_zhiHuHotwordsObjPath);
        try {
            List<String> hotWords =  Files.readAllLines(path, Charsets.UTF_8);
            String hotWordLine = hotWords.get(0);
            String formatLine = hotWordLine.substring(1,hotWordLine.length() - 1).replace("\\\"","\"");
            xZSE86ValueList = JSON.parseObject(formatLine,new TypeReference<List<XZSE86Dto>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return xZSE86ValueList;
    }

    public static String saveQuestionResultToExcel(String saveFileCategoryName, List<QuestionParseDto> questions){
        String projectPath  = Crawler.class.getResource("/").getPath();
        String projectRoot = projectPath.substring(0,projectPath.indexOf("target")).substring(1);
        String parentFolder = projectRoot + properties.getProperty("questionOutputPath") + saveFileCategoryName;
        String fileFullPath = setSaveFileFullPath(saveFileCategoryName,parentFolder);

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("知乎问题解析内容");
        //设置表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("问题链接");
        cell = row.createCell(1);
        cell.setCellValue("关注人数");
        cell = row.createCell(2);
        cell.setCellValue("浏览次数");
        sheet.setColumnWidth(0,50*256);

        for (QuestionParseDto q: questions
             ) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(q.getQuestionUrl());
            dataRow.createCell(1).setCellValue(q.getFollowCount());
            dataRow.createCell(2).setCellValue(q.getBrowseCount());
        }

        File excelParentFolder = new File(parentFolder);
        if(!excelParentFolder.exists()){
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

    private static String setSaveFileFullPath(String saveFileCategoryName,String parentFolder){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss");
        return parentFolder + "/" +  saveFileCategoryName + "_" + dateTime.format(formatter) + ".xlsx";
    }
}

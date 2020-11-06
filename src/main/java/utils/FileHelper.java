package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import dto.XZSE86Dto;
import org.apache.commons.codec.Charsets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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

}

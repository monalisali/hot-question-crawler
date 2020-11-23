package utils;

import dao.Dao;
import dto.ConnectDto;
import dto.QuestionContentDto;
import dto.QuestionResultDto;
import entity.HotWord;
import entity.Question;
import entity.QuestionContent;
import modules.zhihu.ZhihuCrawler;
import sun.misc.BASE64Decoder;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class Helper {
    private static Properties properties = Helper.GetAppProperties();
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());

    public static Properties GetAppProperties() {
        Properties pro = null;
        try {
            pro = new Properties();
            String filePath = "./src/main/resources/app.properties";
            File file = new File(filePath);
            if (!file.exists()) {
                filePath = "./classes/app.properties";
            }
            FileInputStream in = new FileInputStream(filePath);
            pro.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public static Properties getAppPropertiesByName(String propertyFileName) {
        Properties pro = null;
        try {
            pro = new Properties();
            String filePath = "./src/main/resources/" + propertyFileName;
            File file = new File(filePath);
            if (!file.exists()) {
                filePath = "./classes/" + propertyFileName;
            }

            FileInputStream in = new FileInputStream(filePath);
            pro.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pro;
    }

    public static String getHttpsURLConnectionResponse(HttpsURLConnection connection) {
        StringBuilder sbResp = new StringBuilder();

        if (connection != null) {
            try {
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String line;
                while ((line = rd.readLine()) != null) {
                    sbResp.append(line);
                }
                rd.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                connection.disconnect();
            }
        }

        return sbResp.toString();
    }

    // 对字节数组字符串进行Base64解码并生成图片
    public static boolean SaveBase64ToImage(String imgStr, String imgFilePath) {
        if (imgStr.isEmpty()) // 图像数据为空
            return false;

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }

            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();

            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public static String getProjectRootPath() {
        String projectPath = ZhihuCrawler.class.getResource("/").getPath();
        return projectPath.substring(0, projectPath.indexOf("target")).substring(1);
    }

    public static String getProjectOutputPath() {
        Properties properties = GetAppProperties();
        return getProjectRootPath() + properties.getProperty("questionOutputPath");
    }

    public static String setFileNameDateFormat() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss");
        return dateTime.format(formatter);
    }

    public static boolean checkNetworkConnection() {
        boolean cnnTest = false;
        String testUrl = "";
        //不用翻墙也可以访问：https://readhub.cn/topic/5bMmlAm75lD
        //翻墙才可以访问： https://www.google.com
        try {
            boolean isCnnByProxy = Boolean.parseBoolean(properties.getProperty("isConnectedByProxy"));
            testUrl = isCnnByProxy ? "https://www.google.com" : "https://readhub.cn/topic/5bMmlAm75lD";
            ConnectDto connectDto = new ConnectDto(testUrl, "GET"
                    , properties.getProperty("accept1")
                    , properties.getProperty("contentType1"), "", "", ""
            );
            HttpsURLConnection resp = NetworkConnect.createHttpConnection(connectDto);
            System.out.println("是否使用代理: " + isCnnByProxy);
            System.out.println("请求返回代码： " + resp.getResponseCode());
            if (resp.getResponseCode() == 200) {
                cnnTest = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cnnTest;
    }

    public static String replacePlusFromUrlEncode(String str) {
        return str.replace("+", "%20");
    }

    public static QuestionResultDto convertQuestionToQuestionResultDto(Question question){
        QuestionResultDto resultDto = new QuestionResultDto();
        resultDto.setLink(question.getUrl());
        return resultDto;
    }

    //1.更新HotWord
    //2.insert Question 到 dbo.Question表
    public static void dbProcessAfterGetQuestion(HotWord crtHotWord,List<QuestionResultDto> crtQuestions, String source) {
        List<Question> questions = new ArrayList<>();
        if(source.equals(ConstantsHelper.Question.QuestionSource_Baidu)){
            crtHotWord.setDoneBaidu(true);
        }else {
            crtHotWord.setDoneZhihu(true);
        }
        dao.updateHotWord(crtHotWord);

        if(crtQuestions.size() > 0){
            crtQuestions.stream().forEach(x-> questions.add(createQuestionObj(crtHotWord,x,source)));
            dao.batchInsertQuestions(questions);
        }
    }

    //此时只能拿到question url，没有名称。名称在最后合并zhihu、baidu所有问题后，再去解析时获取
    public static Question createQuestionObj(HotWord hotWord, QuestionResultDto questionResult, String source){
        Question question = new Question();
        question.setId(UUID.randomUUID().toString());
        question.setHotWordId(hotWord.getId());
        question.setUrl(questionResult.getLink());
        question.setSource(source);
        question.setCreateTime(new Timestamp(System.currentTimeMillis()));
        return question;
    }

    public static LocalDate convertTimestampToLocalDate(Timestamp timestamp){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return LocalDate.parse(dateFormat.format(timestamp));
    }


    public static QuestionContentDto convertEntityToQuestionContentDto(QuestionContent entity){
        QuestionContentDto dto = new QuestionContentDto();
        dto.setId(entity.getId());
        dto.setBrowserCount(entity.getBrowserCount());
        dto.setCombinedQuestionId(entity.getCombinedQuestionId());
        dto.setCreateTime(entity.getCreateTime());
        dto.setCreateTimeLocalDate(convertTimestampToLocalDate(entity.getCreateTime()));
        dto.setFollowerCount(entity.getFollowerCount());
        return dto;
    }
}

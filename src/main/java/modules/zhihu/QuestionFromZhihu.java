package modules.zhihu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import dao.Dao;
import dto.*;
import entity.HotWord;
import entity.TopCategory;
import entity.XZSE86;
import org.apache.commons.codec.Charsets;
import org.tinylog.Logger;
import utils.ConstantsHelper;
import utils.DatabaseHelp;
import utils.Helper;
import utils.NetworkConnect;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class QuestionFromZhihu implements IQuestion {
    private Properties properties = Helper.GetAppProperties();
    private Properties changeProperties = Helper.getAppPropertiesByName("change.properties");
    private static Dao dao = new Dao(DatabaseHelp.getSqlSessionFactory());
    private ZhihuLoginDto zhihuLoginDto = new ZhihuLoginDto();
    private List<XZSE86Dto> hotWordList;

    public QuestionFromZhihu(List<XZSE86Dto> hotWords) {
        this.hotWordList = hotWords;
    }

    public List<XZSE86Dto> getHotWordList() {
        return hotWordList;
    }

    public void setHotWordList(List<XZSE86Dto> hotWordList) {
        this.hotWordList = hotWordList;
    }


    //目前只能获取第一页的数据，翻页的话又加密参数（猜测是：search_hash_id）还未破解
    @Override
    public List<QuestionResultDto> getQuestion() {
        List<QuestionResultDto> results = new ArrayList<>();
        ConnectDto connectDto = new ConnectDto();
        connectDto.setSource(ConstantsHelper.NetworkConnectConstant.CONNTSOURCE_ZHIHU);
        connectDto.setUserAgent(properties.getProperty("userAgent"));
        connectDto.setMethod("GET");
        connectDto.setAccept("*/*");
        connectDto.setContentType("");
        connectDto.setxZse83(properties.getProperty("xZse83"));
        connectDto.setConnectedByProxy(Boolean.parseBoolean(properties.getProperty("isConnectedByProxy")));
        int count = 1;
        for (XZSE86Dto h : this.getHotWordList()
        ) {
            connectDto.setxZse86(h.getxZse86Val());
            QuestionResultDto resp = sendQuestionRequest(h.getHotword(),connectDto);
            Logger.info("知乎获取问题，发送请求完成：" + h.getHotword());
            Logger.info("知乎获取问题，关键字：" + h.getHotword() + "返回内容：\r\n" + resp.getPagedHtmlResponse());
            ZhihuResponseDto responseDto = convertQuestionResponseToDto(resp.getPagedHtmlResponse());
            if(responseDto != null){
                List<ZhihuResponseQuestionDto> questionDtos =  getQuestionResult(responseDto);
                questionDtos.forEach(x->results.add(formatResponseDtoToQuestion(x)));
                System.out.println("第" + (count++) + "个热词完成：" + h.getHotword());
            }else {
                Logger.error("知乎获取问题，关键字" + h.getHotword() + "在 convertQuestionResponseToDto()中返回null");
            }

            try {
                Thread.currentThread().sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return results.stream().distinct().collect(Collectors.toList());
    }

    private QuestionResultDto sendQuestionRequest(String hotWord,ConnectDto connectDto){
        QuestionResultDto result = new QuestionResultDto();
        connectDto.setRequestUrl(setQuestionUrl(hotWord));
        connectDto.setRefer(setQuestionRef(hotWord));
        connectDto.setCookie(changeProperties.getProperty("zhiHuCookie"));
        HttpsURLConnection conn = NetworkConnect.createHttpConnection(connectDto);
        if (conn != null) {
            String sbResp = Helper.getHttpsURLConnectionResponse(conn);
            result.setPagedHtmlResponse(sbResp);
        }
        return result;
    }

    private String setQuestionUrl(String hotword) {
        StringBuilder urlStringBuilder = new StringBuilder(properties.getProperty("zhihuSearchQuestionUrl"));
        try {
            urlStringBuilder.append("&q=");
            urlStringBuilder.append(Helper.replacePlusFromUrlEncode(URLEncoder.encode(hotword, String.valueOf(Charsets.UTF_8))));
            urlStringBuilder.append("&correction=1&offset=0&limit=20&lc_idx=0&show_all_topics=0");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return urlStringBuilder.toString();
    }

    private String setQuestionRef(String hotword) {
        StringBuilder stringBuilder = new StringBuilder(properties.getProperty("zhihuSearchReferUr"));
        try {
            stringBuilder.append("&q=");
            stringBuilder.append( Helper.replacePlusFromUrlEncode(URLEncoder.encode(hotword, String.valueOf(Charsets.UTF_8))));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private List<ZhihuResponseQuestionDto> getQuestionResult(ZhihuResponseDto resp){
        List<ZhihuResponseQuestionDto> result = new ArrayList<>();
        List<ZhihuResponseDataDto> datas = resp.getData();
         if(datas != null){
             for (ZhihuResponseDataDto d : datas
                  ) {
                 ZhihuResponseObjDto obj = d.getObject();
                 if(obj != null && obj.getQuestion() != null){
                     result.add(obj.getQuestion());
                 }
             }
         }
         return result;
    }

    private ZhihuResponseDto convertQuestionResponseToDto(String resp){
        try{
            ZhihuResponseDto responseDto = JSON.parseObject(resp, ZhihuResponseDto.class);
            return responseDto;
        }catch (Exception e){
            Logger.error(e);
            return null;
        }
    }

    private QuestionResultDto formatResponseDtoToQuestion(ZhihuResponseQuestionDto questionDto){
        QuestionResultDto resultDto = new QuestionResultDto();
        resultDto.setLink( properties.getProperty("zhiHuQuestionUrl") + questionDto.getId());
        return resultDto;
    }

    private ZhihuResponseDto mockSendQuestionRequestion(){
        ZhihuResponseDto responseDto = new ZhihuResponseDto();
        Path path = Paths.get("./src/main/resources/responseExample/知乎关键字查询返回结果.json");
        try {
            List<String> responseList = Files.readAllLines(path, Charsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            responseList.forEach(x->sb.append(x));
            responseDto = JSON.parseObject(sb.toString(), ZhihuResponseDto.class);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseDto;
    }

    public List<XZSE86Dto> getXzse86HotWordList(TopCategory topCategory){
        List<XZSE86Dto> result = new ArrayList<>();
        XZSE86 xzse86 = dao.selectXzse86ByTopCategoryId(topCategory.getId());
        if(xzse86 != null){
            String jsonLine = xzse86.getXZSE86JSON().substring(1, xzse86.getXZSE86JSON().length() - 1).replace("\\\"", "\"");
            result = JSON.parseObject(jsonLine, new TypeReference<List<XZSE86Dto>>() {});
        }
        return result;
    }

    /*

    getCaptchaByApi(),getHAMCSignature()
    private String getCaptchaByApi() {
        String captchaCode = "";
        try {
            String img64 = "";
            String capsionTicket = "";
            CloseableHttpResponse chkCaptResp = NetworkConnect.sendHttpGet(properties.getProperty("zhiCaptchaApiEn"), false);
            HttpEntity chkCaptEntity = chkCaptResp.getEntity();
            InputStream chkCaptIs = chkCaptEntity.getContent();
            String chkCaptHtml = IOUtils.toString(chkCaptIs, String.valueOf(Charsets.UTF_8));
            Document chkCaptDocument = Jsoup.parse(chkCaptHtml);
            JSONObject bodyHtml = JSON.parseObject(chkCaptDocument.getElementsByTag("body").html());
            boolean show_captcha = bodyHtml.getBooleanValue("show_captcha");
            //show_captcha = true 说明登录时需要验证码，重新用HttpPut再发送一次请求以获取验证图片
            if (show_captcha) {
                Header[] chkCaptRespSetCookie = chkCaptResp.getHeaders("set-cookie");
                for (Header h : chkCaptRespSetCookie
                ) {
                    String val = h.getValue();
                    if (val.contains("capsion_ticket")) {
                        capsionTicket = val.substring(0, val.indexOf("Domain") - 1);
                        break;
                    }
                }

                //第二次发送时：必须把第一次发送的返回结果中"capsion_ticket"的值存入到cookie中
                HttpPut httpPut = new HttpPut(properties.getProperty("zhiCaptchaApiEn"));
                httpPut.setHeader("cookie", capsionTicket);
                CloseableHttpClient httpPutClient = HttpClients.createDefault();
                CloseableHttpResponse putResponse = httpPutClient.execute(httpPut);
                HttpEntity putEntity = putResponse.getEntity();
                InputStream httpPutStream = putEntity.getContent();
                String putHtml = IOUtils.toString(httpPutStream, "UTF-8");
                Document putDocument = Jsoup.parse(putHtml);
                JSONObject img64Body = JSON.parseObject(putDocument.getElementsByTag("body").html());
                img64 = img64Body.getString("img_base64").replace("\n", "");
                Helper.SaveBase64ToImage(img64, "C:/temp/zhiHuYanZheng.jpg");
                //Todo:使用解析验证码的库来解析图片
                //captchaCode = !!!!!!!!!!!!;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Todo: 使用解析验证码的库来解析图片完成前，在这里打个断点，手动为captchaCode赋值
        return captchaCode;
    }

    private String getHAMCSignature() {
        String hex = "";
        String singStr = zhihuLoginDto.getGrantType() + zhihuLoginDto.getClientId() + zhihuLoginDto.getSource() + zhihuLoginDto.getTimestamp();
        try {
            String key = properties.getProperty("HAMCShaKey");
            byte[] data = key.getBytes(String.valueOf(Charsets.UTF_8));
            SecretKey secretKey = new SecretKeySpec(data, properties.getProperty("HmacSHA1"));
            // 生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance(properties.getProperty("HmacSHA1"));
            mac.init(secretKey);
            byte[] text = singStr.getBytes(String.valueOf(Charsets.UTF_8));
            byte[] encryptByte = mac.doFinal(text);
            hex = Hex.encodeHexString(encryptByte);

        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return hex;
    }

     */
}

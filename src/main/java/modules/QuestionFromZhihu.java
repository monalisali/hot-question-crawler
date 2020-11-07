package modules;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import dto.*;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.ConstantsHelper;
import utils.Helper;
import utils.NetworkConnect;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;


public class QuestionFromZhihu implements IQuestion {
    private Properties properties = Helper.GetAppProperties();
    private Properties changeProperties = Helper.getAppPropertiesByName("change.properties");
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

        for (XZSE86Dto h : this.getHotWordList()
        ) {
            connectDto.setxZse86(h.getxZse86Val());
            QuestionResultDto resp = sendQuestionRequest(h.getHotword(),connectDto);
            ZhihuResponseDto responseDto = convertQuestionResponseToDto(resp.getPagedHtmlResponse());
            //ZhihuResponseDto responseDto = mockSendQuestionRequestion();
            List<ZhihuResponseQuestionDto> questionDtos =  getQuesitionResult(responseDto);
            questionDtos.forEach(x->results.add(formatResponseDtoToQuestion(x)));
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
            urlStringBuilder.append(URLEncoder.encode(hotword, String.valueOf(Charsets.UTF_8)));
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
            stringBuilder.append(URLEncoder.encode(hotword, String.valueOf(Charsets.UTF_8)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private List<ZhihuResponseQuestionDto> getQuesitionResult(ZhihuResponseDto resp){
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
        ZhihuResponseDto responseDto = JSON.parseObject(resp, ZhihuResponseDto.class);
        return responseDto;
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
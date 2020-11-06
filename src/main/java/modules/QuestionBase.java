package modules;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import dto.XZSE86Dto;

import java.util.List;

public class QuestionBase {
    //所有热词
    private List<String> hotWordList;

    public QuestionBase(List<String> hotWordList){
        this.hotWordList = hotWordList;
    }

    public List<String> getHotWordList() {
        return hotWordList;
    }

    public void setHotWordList(List<String> hotWordList) {
        this.hotWordList = hotWordList;
    }
}

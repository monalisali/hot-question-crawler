package entity;

import java.math.BigInteger;
import java.sql.Timestamp;

public class QuestionContent {
    private String id;
    private String questionId;
    private BigInteger followerCount;
    private BigInteger browserCount;
    private Timestamp createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public BigInteger getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(BigInteger followerCount) {
        this.followerCount = followerCount;
    }

    public BigInteger getBrowserCount() {
        return browserCount;
    }

    public void setBrowserCount(BigInteger browserCount) {
        this.browserCount = browserCount;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}

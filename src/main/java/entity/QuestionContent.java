package entity;

import java.math.BigInteger;
import java.sql.Timestamp;

public class QuestionContent {
    private String id;
    private String combinedQuestionId;
    private BigInteger followerCount;
    private BigInteger browserCount;
    private Timestamp createTime;
    private int answerCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCombinedQuestionId() {
        return combinedQuestionId;
    }

    public void setCombinedQuestionId(String combinedQuestionId) {
        this.combinedQuestionId = combinedQuestionId;
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

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }
}

package dto;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

public class QuestionContentDto {
    private String id;
    private String combinedQuestionId;
    private BigInteger followerCount;
    private BigInteger browserCount;
    private Timestamp createTime;
    private LocalDate createTimeLocalDate;
    private QuestionContentDto comparsionMinContent;
    private QuestionContentDto comparsionMaxContent;
    private String topCateogryName;
    private String questionName;
    private String questionUrl;
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

    public LocalDate getCreateTimeLocalDate() {
        return createTimeLocalDate;
    }

    public void setCreateTimeLocalDate(LocalDate createTimeLocalDate) {
        this.createTimeLocalDate = createTimeLocalDate;
    }

    public QuestionContentDto getComparsionMinContent() {
        return comparsionMinContent;
    }

    public void setComparsionMinContent(QuestionContentDto comparsionMinContent) {
        this.comparsionMinContent = comparsionMinContent;
    }

    public QuestionContentDto getComparsionMaxContent() {
        return comparsionMaxContent;
    }

    public void setComparsionMaxContent(QuestionContentDto comparsionMaxContent) {
        this.comparsionMaxContent = comparsionMaxContent;
    }

    public String getTopCateogryName() {
        return topCateogryName;
    }

    public void setTopCateogryName(String topCateogryName) {
        this.topCateogryName = topCateogryName;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }
}

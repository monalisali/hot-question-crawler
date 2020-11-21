package dto;

import java.math.BigInteger;
import java.sql.Timestamp;

public class ParseQuestionExcelDto {
    private String id;
    private String topCategoryName;
    private String questionName;
    private String questionUrl;
    private BigInteger oldFollowerCount;
    private BigInteger oldBrowserCount;
    private Timestamp oldCreateTime;
    private BigInteger newFollowerCount;
    private BigInteger newBrowserCount;
    private Timestamp newCreateTime;
    private BigInteger diffFollowerCount;
    private BigInteger diffBrowserCount;
    private int diffCreateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopCategoryName() {
        return topCategoryName;
    }

    public void setTopCategoryName(String topCategoryName) {
        this.topCategoryName = topCategoryName;
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

    public BigInteger getOldFollowerCount() {
        return oldFollowerCount;
    }

    public void setOldFollowerCount(BigInteger oldFollowerCount) {
        this.oldFollowerCount = oldFollowerCount;
    }

    public BigInteger getOldBrowserCount() {
        return oldBrowserCount;
    }

    public void setOldBrowserCount(BigInteger oldBrowserCount) {
        this.oldBrowserCount = oldBrowserCount;
    }

    public Timestamp getOldCreateTime() {
        return oldCreateTime;
    }

    public void setOldCreateTime(Timestamp oldCreateTime) {
        this.oldCreateTime = oldCreateTime;
    }

    public BigInteger getNewFollowerCount() {
        return newFollowerCount;
    }

    public void setNewFollowerCount(BigInteger newFollowerCount) {
        this.newFollowerCount = newFollowerCount;
    }

    public BigInteger getNewBrowserCount() {
        return newBrowserCount;
    }

    public void setNewBrowserCount(BigInteger newBrowserCount) {
        this.newBrowserCount = newBrowserCount;
    }

    public Timestamp getNewCreateTime() {
        return newCreateTime;
    }

    public void setNewCreateTime(Timestamp newCreateTime) {
        this.newCreateTime = newCreateTime;
    }

    public BigInteger getDiffFollowerCount() {
        return diffFollowerCount;
    }

    public void setDiffFollowerCount(BigInteger diffFollowerCount) {
        this.diffFollowerCount = diffFollowerCount;
    }

    public BigInteger getDiffBrowserCount() {
        return diffBrowserCount;
    }

    public void setDiffBrowserCount(BigInteger diffBrowserCount) {
        this.diffBrowserCount = diffBrowserCount;
    }

    public int getDiffCreateTime() {
        return diffCreateTime;
    }

    public void setDiffCreateTime(int diffCreateTime) {
        this.diffCreateTime = diffCreateTime;
    }
}

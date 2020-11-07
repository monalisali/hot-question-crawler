package dto;

public class QuestionParseDto {
    //关注者人数
    private int followCount;
    //被浏览次数
    private int browseCount;
    //问题链接
    private String questionUrl;


    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getBrowseCount() {
        return browseCount;
    }

    public void setBrowseCount(int browseCount) {
        this.browseCount = browseCount;
    }

    public String getQuestionUrl() {
        return questionUrl;
    }

    public void setQuestionUrl(String questionUrl) {
        this.questionUrl = questionUrl;
    }


}

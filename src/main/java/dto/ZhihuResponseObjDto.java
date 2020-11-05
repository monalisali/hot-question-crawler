package dto;

public class ZhihuResponseObjDto {
    private String id;
    private String name;
    private String url;
    private String type;
    private String excerpt;
    private String introduction;
    private String description;
    private String avatar_url;
    private boolean is_following;
    private int questions_count;
    private int followers_count;
    private int follower_count;
    private int top_answer_count;
    private int score;
    private ZhihuResponseQuestionDto question;

    public ZhihuResponseQuestionDto getQuestion() {
        return question;
    }

    public void setQuestion(ZhihuResponseQuestionDto question) {
        this.question = question;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public boolean isIs_following() {
        return is_following;
    }

    public void setIs_following(boolean is_following) {
        this.is_following = is_following;
    }

    public int getQuestions_count() {
        return questions_count;
    }

    public void setQuestions_count(int questions_count) {
        this.questions_count = questions_count;
    }

    public int getFollowers_count() {
        return followers_count;
    }

    public void setFollowers_count(int followers_count) {
        this.followers_count = followers_count;
    }

    public int getFollower_count() {
        return follower_count;
    }

    public void setFollower_count(int follower_count) {
        this.follower_count = follower_count;
    }

    public int getTop_answer_count() {
        return top_answer_count;
    }

    public void setTop_answer_count(int top_answer_count) {
        this.top_answer_count = top_answer_count;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

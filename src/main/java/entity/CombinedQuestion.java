package entity;

import java.sql.Timestamp;

public class CombinedQuestion {
    private String id;
    private String topCategoryId;
    private String hotWordId;
    private String url;
    private String name;
    private Timestamp createTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHotWordId() {
        return hotWordId;
    }

    public void setHotWordId(String hotWordId) {
        this.hotWordId = hotWordId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getTopCategoryId() {
        return topCategoryId;
    }

    public void setTopCategoryId(String topCategoryId) {
        this.topCategoryId = topCategoryId;
    }
}

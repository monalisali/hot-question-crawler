package entity;

import java.math.BigInteger;

public class Question {
    private String id;
    private String hotWordId;
    private String url;
    private String source;
    private String name;

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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

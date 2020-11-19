package entity;

import java.math.BigInteger;

public class HotWord {
    private String id;
    private String topCategoryID;
    private String Name;
    private Boolean isDone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTopCategoryID() {
        return topCategoryID;
    }

    public void setTopCategoryID(String topCategoryID) {
        this.topCategoryID = topCategoryID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Boolean getDone() {
        return isDone;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }
}

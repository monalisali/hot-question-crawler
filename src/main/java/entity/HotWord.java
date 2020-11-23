package entity;

import java.math.BigInteger;

public class HotWord {
    private String id;
    private String topCategoryID;
    private String Name;
    private Boolean isDoneBaidu;
    private Boolean isDoneZhihu;

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

    public Boolean getDoneBaidu() {
        return isDoneBaidu;
    }

    public void setDoneBaidu(Boolean doneBaidu) {
        isDoneBaidu = doneBaidu;
    }

    public Boolean getDoneZhihu() {
        return isDoneZhihu;
    }

    public void setDoneZhihu(Boolean doneZhihu) {
        isDoneZhihu = doneZhihu;
    }
}

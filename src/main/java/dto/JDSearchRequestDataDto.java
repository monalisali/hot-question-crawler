package dto;

import java.util.List;

public class JDSearchRequestDataDto {
    private int categoryId;
    private int cat2Id;
    private int cat3Id;
    private int isZY;//是否未自营
    private String keywordType;
    private String searchType;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCat2Id() {
        return cat2Id;
    }

    public int getIsZY() {
        return isZY;
    }

    public void setIsZY(int isZY) {
        this.isZY = isZY;
    }

    public void setCat2Id(int cat2Id) {
        this.cat2Id = cat2Id;
    }

    public int getCat3Id() {
        return cat3Id;
    }

    public void setCat3Id(int cat3Id) {
        this.cat3Id = cat3Id;
    }

    public String getKeywordType() {
        return keywordType;
    }

    public void setKeywordType(String keywordType) {
        this.keywordType = keywordType;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }
}

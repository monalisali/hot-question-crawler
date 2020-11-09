package dto;

import jdk.nashorn.internal.runtime.options.Option;

import java.util.List;
import java.util.Optional;

public class JDSearchRequestDataDto {
    private int categoryId;
    private Optional<Integer> cat2Id;
    private Optional<Integer> cat3Id;
    private int isZY;//是否未自营
    private String keywordType;
    private String searchType;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Optional<Integer> getCat2Id() {
        return cat2Id;
    }

    public void setCat2Id(Optional<Integer> cat2Id) {
        this.cat2Id = cat2Id;
    }

    public Optional<Integer> getCat3Id() {
        return cat3Id;
    }

    public void setCat3Id(Optional<Integer> cat3Id) {
        this.cat3Id = cat3Id;
    }

    public int getIsZY() {
        return isZY;
    }

    public void setIsZY(int isZY) {
        this.isZY = isZY;
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

package dto;

public class JDCategoryDto {
    private String categoryName;
    private int id;
    private int parentId;
    private int level;
    private int cat1Id;
    private int cat2Id;
    private int cat3Id;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCat1Id() {
        return cat1Id;
    }

    public void setCat1Id(int cat1Id) {
        this.cat1Id = cat1Id;
    }

    public int getCat2Id() {
        return cat2Id;
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
}

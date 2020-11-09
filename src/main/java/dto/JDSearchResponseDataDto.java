package dto;

import java.util.List;

public class JDSearchResponseDataDto {
    private List<Object> unionGoods;
    private List<JDGoodsDto> unionGoodsParsed;
    private List<JDCategoryDto> catList1;
    private List<JDCategoryDto> catList2;
    private List<JDCategoryDto> catList3;
    private PageDto page;


    public List<Object> getUnionGoods() {
        return unionGoods;
    }

    public void setUnionGoods(List<Object> unionGoods) {
        this.unionGoods = unionGoods;
    }

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public List<JDGoodsDto> getUnionGoodsParsed() {
        return unionGoodsParsed;
    }

    public void setUnionGoodsParsed(List<JDGoodsDto> unionGoodsParsed) {
        this.unionGoodsParsed = unionGoodsParsed;
    }

    public List<JDCategoryDto> getCatList1() {
        return catList1;
    }

    public void setCatList1(List<JDCategoryDto> catList1) {
        this.catList1 = catList1;
    }

    public List<JDCategoryDto> getCatList2() {
        return catList2;
    }

    public void setCatList2(List<JDCategoryDto> catList2) {
        this.catList2 = catList2;
    }

    public List<JDCategoryDto> getCatList3() {
        return catList3;
    }

    public void setCatList3(List<JDCategoryDto> catList3) {
        this.catList3 = catList3;
    }
}

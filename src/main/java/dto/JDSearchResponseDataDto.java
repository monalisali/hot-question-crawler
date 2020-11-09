package dto;

import java.util.List;

public class JDSearchResponseDataDto {
    private List<Object> unionGoods;
    private List<JDGoodsDto> unionGoodsParsed;
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
}

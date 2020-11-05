package dto;

public class ZhihuResponseDataDto {
    private int index;
    private String id;
    private String type;
    private ZhihuResponseHighlightDto highlight;
    private ZhihuResponseObjDto object;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ZhihuResponseHighlightDto getHighlight() {
        return highlight;
    }

    public void setHighlight(ZhihuResponseHighlightDto highlight) {
        this.highlight = highlight;
    }

    public ZhihuResponseObjDto getObject() {
        return object;
    }

    public void setObject(ZhihuResponseObjDto object) {
        this.object = object;
    }


}

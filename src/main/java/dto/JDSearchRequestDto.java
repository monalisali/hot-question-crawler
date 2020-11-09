package dto;

public class JDSearchRequestDto {
    private int pageNo;
    private int pageSize;
    private String searchUUID;
    private JDSearchRequestDataDto data;
    private String requestUrl;


    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchUUID() {
        return searchUUID;
    }

    public void setSearchUUID(String searchUUID) {
        this.searchUUID = searchUUID;
    }

    public JDSearchRequestDataDto getData() {
        return data;
    }

    public void setData(JDSearchRequestDataDto data) {
        this.data = data;
    }
}

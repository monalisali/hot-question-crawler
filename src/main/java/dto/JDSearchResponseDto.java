package dto;

public class JDSearchResponseDto {
    private String code;
    private String message;
    private String requestId;
    private JDSearchResponseDataDto data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public JDSearchResponseDataDto getData() {
        return data;
    }

    public void setData(JDSearchResponseDataDto data) {
        this.data = data;
    }
}

package dto;

import utils.Helper;

import java.util.Properties;

public class ConnectDto {
    private static Properties properties = Helper.GetAppProperties();

    public ConnectDto(String requestUrl,
                      String method,
                      String accept, String contentType, String userAgent) {
        this.accept = accept;
        this.contentType = contentType;
        this.userAgent = userAgent;
        this.requestUrl = requestUrl;
        this.method = method;
        this.connectedByProxy = Boolean.parseBoolean(properties.getProperty("isConnectedByProxy"));
    }

    private String accept;
    private String contentType;
    private String userAgent;
    private String requestUrl;
    private String method;
    private boolean connectedByProxy;

    public boolean isConnectedByProxy() {
        return connectedByProxy;
    }

    public void setConnectedByProxy(boolean connectedByProxy) {
        this.connectedByProxy = connectedByProxy;
    }


    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }


    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }


    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }


    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }



}

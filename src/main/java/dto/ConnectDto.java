package dto;

import utils.Helper;

import java.util.Properties;

public class ConnectDto {
    private static Properties properties = Helper.GetAppProperties();
    private static Properties changeProperties = Helper.getAppPropertiesByName("change.properties");

    public ConnectDto(){

    }

    public ConnectDto(String requestUrl,
                      String method,
                      String accept,
                      String contentType,
                      String refer,
                      String cookie,
                      String source) {

        this.userAgent = properties.getProperty("userAgent");
        this.connectedByProxy = Boolean.parseBoolean(properties.getProperty("isConnectedByProxy"));
        this.xZse83 = properties.getProperty("xZse83");
        this.xZse86 = changeProperties.getProperty("xZse86");

        this.method = method;
        this.accept = accept;
        this.contentType = contentType;
        this.requestUrl = requestUrl;
        this.refer = refer;
        this.cookie = cookie;
        this.source = source;
    }

    private String accept;
    private String contentType;
    private String userAgent;
    private String requestUrl;
    private String method;
    private String xZse83;
    private String xZse86;
    private String refer;
    private String cookie;
    private String source;
    private boolean connectedByProxy;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

    public String getxZse86() {
        return xZse86;
    }

    public void setxZse86(String xZse86) {
        this.xZse86 = xZse86;
    }

    public String getxZse83() {
        return xZse83;
    }

    public void setxZse83(String xZse83) {
        this.xZse83 = xZse83;
    }


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

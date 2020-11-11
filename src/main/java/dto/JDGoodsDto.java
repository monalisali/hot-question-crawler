package dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JDGoodsDto {
    private BigInteger skuId;//商品Id
    private String skuName;//商品名称
    private String shopName;//商店名称
    private double wlPrice; //价格
    private double wlCommission; //佣金
    private double wlCommissionRatio; //佣金比
    private int isZY;//是否自营
    private int goodComments;//好评数量

    private String skuUrl; //商品链接
    private double finalPrice; //最终价格
    private String Category1Name;
    private String Category2Name;
    private String Category3Name;
    private BigDecimal inOrderComm30Days;
    private BigInteger inOrderCount30Days;



    public BigInteger getSkuId() {
        return skuId;
    }

    public void setSkuId(BigInteger skuId) {
        this.skuId = skuId;
        this.setSkuUrl("https://item.jd.com/" + skuId + ".html");
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public double getWlPrice() {
        return wlPrice;
    }

    public void setWlPrice(double wlPrice) {
        this.wlPrice = wlPrice;
    }

    public double getWlCommission() {
        return wlCommission;
    }

    public void setWlCommission(double wlCommission) {
        this.wlCommission = wlCommission;
    }

    public double getWlCommissionRatio() {
        return wlCommissionRatio;
    }

    public void setWlCommissionRatio(double wlCommissionRatio) {
        this.wlCommissionRatio = wlCommissionRatio;
    }

    public int getIsZY() {
        return isZY;
    }

    public void setIsZY(int isZY) {
        this.isZY = isZY;
    }

    public int getGoodComments() {
        return goodComments;
    }

    public void setGoodComments(int goodComments) {
        this.goodComments = goodComments;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public String getCategory1Name() {
        return Category1Name;
    }

    public void setCategory1Name(String category1Name) {
        Category1Name = category1Name;
    }

    public String getCategory2Name() {
        return Category2Name;
    }

    public void setCategory2Name(String category2Name) {
        Category2Name = category2Name;
    }

    public String getCategory3Name() {
        return Category3Name;
    }

    public void setCategory3Name(String category3Name) {
        Category3Name = category3Name;
    }

    public BigDecimal getInOrderComm30Days() {
        return inOrderComm30Days;
    }

    public void setInOrderComm30Days(BigDecimal inOrderComm30Days) {
        this.inOrderComm30Days = inOrderComm30Days;
    }

    public BigInteger getInOrderCount30Days() {
        return inOrderCount30Days;
    }

    public void setInOrderCount30Days(BigInteger inOrderCount30Days) {
        this.inOrderCount30Days = inOrderCount30Days;
    }
}

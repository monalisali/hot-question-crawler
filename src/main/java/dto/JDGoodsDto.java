package dto;

import java.math.BigDecimal;
import java.math.BigInteger;

public class JDGoodsDto {
    private BigInteger skuId;//商品Id
    private String skuName;//商品名称
    private String shopName;//商店名称
    private BigDecimal wlPrice; //价格
    private BigDecimal wlCommission; //佣金
    private double wlCommissionRatio; //佣金比
    private int isZY;//是否自营
    private int goodComments;//好评数量

    public BigInteger getSkuId() {
        return skuId;
    }

    public void setSkuId(BigInteger skuId) {
        this.skuId = skuId;
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

    public BigDecimal getWlPrice() {
        return wlPrice;
    }

    public void setWlPrice(BigDecimal wlPrice) {
        this.wlPrice = wlPrice;
    }

    public BigDecimal getWlCommission() {
        return wlCommission;
    }

    public void setWlCommission(BigDecimal wlCommission) {
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
}

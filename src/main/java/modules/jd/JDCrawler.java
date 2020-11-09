package modules.jd;

import modules.zhihu.ZhihuCrawler;

import java.util.Optional;

public class JDCrawler {
    public static void main(String[] args) {
        JDProduct jdProduct = new JDProduct(true);
        jdProduct.getJDProducts();
        String ss = "ss";
    }
}

package modules.jd;

import utils.Helper;
import java.util.Properties;

public class JDCrawler {
    private static Properties properties = Helper.GetAppProperties();
    public static void main(String[] args) {
        JDProduct jdProduct = new JDProduct(Boolean.parseBoolean(properties.getProperty("isToGetJdProductCategory")));
        jdProduct.getJDProducts();
        String ss = "ss";
    }
}

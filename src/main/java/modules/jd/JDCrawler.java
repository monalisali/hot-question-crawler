package modules.jd;


import utils.Helper;
import java.util.Properties;

public class JDCrawler {
    private static Properties properties = Helper.GetAppProperties();

    public static void main(String[] args) {
        if (Helper.checkNetworkConnection()) {
            System.out.println("网络测试通过");
            JDProduct jdProduct = new JDProduct(Boolean.parseBoolean(properties.getProperty("isToGetJdProductCategory")));
            jdProduct.getJDProducts();
        }else {
            System.out.println("网络测试不通过");
        }
    }
}

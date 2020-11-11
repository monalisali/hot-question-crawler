package modules.jd;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Helper;
import java.util.Properties;

public class JDCrawler {
    private static Properties properties = Helper.GetAppProperties();
    private static Logger logger = LogManager.getLogger(JDCrawler.class.getClass());

    public static void main(String[] args) { JDProduct jdProduct = new JDProduct(Boolean.parseBoolean(properties.getProperty("isToGetJdProductCategory")));
        jdProduct.getJDProducts();
        String ss = "ss";
    }
}

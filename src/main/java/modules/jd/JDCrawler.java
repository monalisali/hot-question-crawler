package modules.jd;

import dto.JDCategoryDto;
import modules.zhihu.ZhihuCrawler;
import utils.ConstantsHelper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDCrawler {
    public static void main(String[] args) {
        JDProduct jdProduct = new JDProduct(true);
        jdProduct.getJDProducts();
        String ss = "ss";
    }
}

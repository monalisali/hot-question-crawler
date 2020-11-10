package utils;

public class ConstantsHelper {
    public static final String CAETGORYNAME = "保温饭盒";

    public class PageHelper{
        public static  final int MAXPAGENUM = 2;
        public static final int PAGESIZE = 10;
        public static final int STARTINDEX = 0;
    }

    public class NetworkConnectConstant{
        public static final String CONNTSOURCE_ZHIHU = "ZhiHu_SearchQuestion";
        public static final String CONNTSOURCE_JD_SearchProduct = "JD_SearchProduct";
    }

    public class JDSearchProduct{
        public static final String SEARCH_TYPE = "st3";
        public static final String KEYWORDTYPE = "kt0";
        public static final String FIRST_CATEGORY_FILENAME = "JD商品一级类目.xls";
        public static final String SECONDE_CATEGORY_FILENAME = "JD商品二级类目.xls";
        public static final String Third_CATEGORY_FILENAME = "JD商品三级类目.xls";
        public static final String IGNORED_FIRST_CATEGORY = "JD商品不用获取商品的一级类目.xls";
        public static final String SHEETNAME_FIRST_CATEGORY = "一级类目";
        public static final String SHEETNAME_SECOND_CATEGORY = "二级类目";
        public static final String SHEETNAME_THIRD_CATEGORY = "三级类目";

        public static final int Page_Start = 1;
        public static final int Page_Size = 60;
    }
}

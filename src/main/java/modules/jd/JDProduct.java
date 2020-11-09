package modules.jd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import dto.*;
import org.apache.commons.codec.Charsets;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import utils.ConstantsHelper;
import utils.Helper;
import utils.NetworkConnect;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static java.sql.Types.NUMERIC;

public class JDProduct {
    private static Properties properties = Helper.GetAppProperties();
    private Properties changeProperties = Helper.getAppPropertiesByName("change.properties");

    private List<JDCategoryDto> categoryList;
    private boolean isToCreateJDCategory;

    public JDProduct(boolean isToCreateJDCategory){
        this.setToCreateJDCategory(isToCreateJDCategory);
        //this.setCategoryList(getCategoryList());
    }

    public void getJDProducts(){
        //JDSearchResponseDto mock = mockGetJDSearchGoodsResponse();
        //saveCategoryResultToExcel(mock.getData().getCatList2(),"二级类目");
        if(this.isToCreateJDCategory()){
            getCategoriesFromJD();
        }

        List<JDSearchResponseDto> result = new ArrayList<>();
        for (JDCategoryDto c: this.getCategoryList()
             ) {
            //todo: 首先要发送一次请求，以获得totalCount,pageSize,pageNo信息，然后再进行循环
            String resp = sendSearchProductRequest(1,60,"",737,
                    Optional.of(new Integer(794)),Optional.of(new Integer(880)),1);
            JDSearchResponseDto respDto = parseSearchRepsone(resp);
            if(respDto != null){
                result.add(respDto);
            }
        }
    }

    private String sendSearchProductRequest(int pageNo, int pageSize, String searchUUID, int c1, Optional<Integer> c2, Optional<Integer> c3, int isZY){
        String result = "";
        ConnectDto connectDto = createConnectObj(pageNo,pageSize,searchUUID,c1,c2,c3,isZY);
        HttpsURLConnection conn = NetworkConnect.createHttpConnection(connectDto);
        if (conn != null) {
            result = Helper.getHttpsURLConnectionResponse(conn);
        }
        return result;
    }

    private JDSearchResponseDto parseSearchRepsone(String response){
        JDSearchResponseDto responseDto = JSON.parseObject(response, JDSearchResponseDto.class);
        if(responseDto.getCode().equals("200")){
            List<Object> unionGoods = responseDto.getData().getUnionGoods();
            List<JDGoodsDto> parsedGoods = new ArrayList<>();
            for(int i = 0; i< unionGoods.size(); i++){
                JSONObject ele = (JSONObject) ((JSONArray) responseDto.getData().getUnionGoods().get(i)).get(0);
                String jsonString = JSONObject.toJSONString(ele);
                JDGoodsDto goodsDto = JSON.parseObject(jsonString,JDGoodsDto.class);
                if(goodsDto != null){
                    parsedGoods.add(goodsDto);
                }
            }
            responseDto.getData().setUnionGoodsParsed(parsedGoods);

        }
        else {
            responseDto = null;
        }

        return responseDto;
    }

    private ConnectDto createConnectObj(int pageNo, int pageSize, String searchUUID, int c1, Optional<Integer> c2, Optional<Integer>  c3,int isZY){
        ConnectDto connectDto = new ConnectDto();
        connectDto.setSource(ConstantsHelper.NetworkConnectConstant.CONNTSOURCE_JD_SearchProduct);
        connectDto.setMethod("POST");
        connectDto.setUserAgent(properties.getProperty("userAgent"));
        connectDto.setAccept("application/json, text/plain, */*");
        connectDto.setContentType("application/json;charset=UTF-8");
        connectDto.setOrigin("https://union.jd.com");
        connectDto.setRequestUrl(properties.getProperty("jdSearchProductsUrl"));
        connectDto.setCookie(changeProperties.getProperty("jdCookie"));

        JDSearchRequestDto requestDto = new JDSearchRequestDto();
        requestDto.setPageNo(pageNo);
        requestDto.setPageSize(pageSize);
        requestDto.setSearchUUID(searchUUID);

        JDSearchRequestDataDto requestDataDto = new JDSearchRequestDataDto();
        requestDataDto.setCategoryId(c1);
        requestDataDto.setCat2Id(c2);
        requestDataDto.setCat3Id(c3);
        requestDataDto.setIsZY(isZY);
        requestDataDto.setKeywordType(ConstantsHelper.JDSearchProduct.KEYWORDTYPE);
        requestDataDto.setSearchType(ConstantsHelper.JDSearchProduct.SEARCH_TYPE);

        requestDto.setData(requestDataDto);
        connectDto.setJdSearchRequestDto(requestDto);

        return connectDto;
    }

    private JDSearchResponseDto mockGetJDSearchGoodsResponse(){
        JDSearchResponseDto result = new JDSearchResponseDto();
        Path path = Paths.get("./src/main/resources/responseExample/JD查询商品返回结果.json");
        try {
            List<String> responseList = Files.readAllLines(path, Charsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            responseList.forEach(x->sb.append(x));
            result  = parseSearchRepsone(sb.toString());
            result.getData().getCatList2().forEach(x->x.setParentId(11));
            result.getData().getCatList2().forEach(x->x.setLevel(2));
//            JDSearchResponseDto responseDto = JSON.parseObject(sb.toString(), JDSearchResponseDto.class);
//            for(int i = 0; i< responseDto.getData().getUnionGoods().size(); i++){
//                JSONObject ele = (JSONObject) ((JSONArray) responseDto.getData().getUnionGoods().get(i)).get(0);
//                String jsonString = JSONObject.toJSONString(ele);
//                JDGoodsDto goodsDto = JSON.parseObject(jsonString,JDGoodsDto.class);
//                if(goodsDto != null){
//                    result.add(goodsDto);
//                }
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<JDCategoryDto> getCategoriesFromJD(){
        Workbook workbook = openFirstCategoryFile(ConstantsHelper.JDSearchProduct.FIRST_CATEGORY_FILENAME);
        List<JDCategoryDto> results = new ArrayList<>();
        List<JDCategoryDto> secondCategories = new ArrayList<>();
        List<JDCategoryDto> thirdCategories = new ArrayList<>();
        if(workbook!=null){
            List<JDCategoryDto> firstCategories = readFirstCategotyFile(workbook);
            for (JDCategoryDto f:firstCategories
                 ) {
                String resp = sendSearchProductRequest(1,60,"",f.getId(),
                        Optional.ofNullable(null),Optional.ofNullable(null),1);
                JDSearchResponseDto respDto = parseSearchRepsone(resp);
                List<JDCategoryDto> crtCategories = respDto.getData().getCatList2();
                if(crtCategories != null){
                    crtCategories.stream().forEach(x->x.setLevel(2));
                    crtCategories.stream().forEach(x->x.setParentId(f.getId()));
                    secondCategories.addAll(crtCategories);
                    System.out.println("获取当前二级科目完成：" + f.getCategoryName());
                }
                try {
                    Thread.currentThread().sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            for (JDCategoryDto s:secondCategories
                 ) {

                try {
                    Thread.currentThread().sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            saveCategoryResultToExcel(secondCategories,"二级类目");
//            saveCategoryResultToExcel(secondCategories,"三级类目");
        }
        return results;
    }

    private Workbook openFirstCategoryFile(String fileName){
        Workbook workbook = null;
        FileInputStream inputStream = null;
        String fileFullPath = Helper.getProjectOutputPath() + "京东/" + fileName;
        File excelFile = new File(fileFullPath);
        if (!excelFile.exists()) {
            return null;
        }

        try {
            inputStream = new FileInputStream(excelFile);
            workbook = new HSSFWorkbook(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workbook;
    }

    private List<JDCategoryDto> readFirstCategotyFile(Workbook workbook){
        List<JDCategoryDto> resultData = new ArrayList<>();
        Sheet sheet = workbook.getSheet("一级类目");
        if(sheet != null){
            int firstRowNum = sheet.getFirstRowNum();
            Row firstRow = sheet.getRow(firstRowNum);
            // 解析每一行的数据，构造数据对象
            int rowStart = firstRowNum + 1;
            int rowEnd = sheet.getPhysicalNumberOfRows();
            for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (null == row) {
                    continue;
                }

                JDCategoryDto  dto = convertRowToData(row);
                dto.setLevel(1);
                dto.setParentId(0);
                resultData.add(dto);
            }
        }
        return resultData;
    }

    private JDCategoryDto convertRowToData(Row row){
        JDCategoryDto dto = new JDCategoryDto();
        Cell cell;
        int cellNum = 0;
        cell = row.getCell(cellNum++);
        dto.setCategoryName(cell.getStringCellValue());
        cell = row.getCell(cellNum);
        dto.setId((int)cell.getNumericCellValue());
        return dto;
    }


    public String saveCategoryResultToExcel(List<JDCategoryDto> list, String sheetName){
        String outputPath = Helper.getProjectOutputPath();
        String filePath = outputPath + "京东/JD完整商品类目.xls";
        File oldFile = new File(filePath);
        if(oldFile.exists()){
            filePath = outputPath + "京东/JD完整商品类目_" + Helper.setFileNameDateFormat() + ".xls";
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Name");
        cell = row.createCell(1);
        cell.setCellValue("ID");
        cell = row.createCell(2);
        cell.setCellValue("ParentID");
        cell = row.createCell(3);
        cell.setCellValue("Level");
        sheet.setColumnWidth(0, 30 * 256);

        for (JDCategoryDto c:list
             ) {
            HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum() + 1);
            dataRow.createCell(0).setCellValue(c.getCategoryName());
            dataRow.createCell(1).setCellValue(c.getId());
            dataRow.createCell(2).setCellValue(c.getParentId());
            dataRow.createCell(3).setCellValue(c.getLevel());
        }

        File excelFile = new File(filePath);
        try {
            // 或者以流的形式写入文件 workbook.write(new FileOutputStream(xlsFile));
            workbook.write(excelFile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return filePath;
    }

    public List<JDCategoryDto> getCategoryList() {
        List<JDCategoryDto> list = new ArrayList<>();
        JDCategoryDto d1 = new JDCategoryDto();
        d1.setId(880);
        d1.setLevel(3);
        d1.setCategoryName("洗衣机");
        d1.setParentId(794);
        list.add(d1);

        categoryList = list;
        return categoryList;
    }

    public void setCategoryList(List<JDCategoryDto> categoryList) {
        this.categoryList = categoryList;
    }

    public boolean isToCreateJDCategory() {
        return isToCreateJDCategory;
    }

    public void setToCreateJDCategory(boolean toCreateJDCategory) {
        isToCreateJDCategory = toCreateJDCategory;
    }
}

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
import java.util.*;
import java.util.stream.Collectors;


public class JDProduct {
    private static Properties properties = Helper.GetAppProperties();
    private Properties changeProperties = Helper.getAppPropertiesByName("change.properties");

    private List<JDCategoryDto> c1List;
    private List<JDCategoryDto> c2List;
    private List<JDCategoryDto> c3List;
    private List<JDCategoryDto> toSearchCategoryList;
    private List<JDCategoryDto> c1ListIgnored;
    private boolean isToCreateJDCategory;

    public JDProduct(boolean isToCreateJDCategory) {
        this.setToCreateJDCategory(isToCreateJDCategory);
    }

    public void getJDProducts() {
        if (this.isToCreateJDCategory()) {
            getCategoriesFromJD();
        }
        setObjectCategoryProperties();
        setToSearchCategories();

        for (JDCategoryDto c1 : this.getC1List()
        ) {
            //一级类目相同的结果，存放在一起并保存为一个独立文件
            List<JDGoodsDto> sameCategoryLevelOneList = new ArrayList<>();
            List<JDCategoryDto> crtC2List = this.getC2List().stream().filter(x -> x.getParentId() == c1.getId()).collect(Collectors.toList());
            for (JDCategoryDto c2 : crtC2List
            ) {
                List<JDCategoryDto> crtC3List = this.getC3List().stream().filter((x -> x.getParentId() == c2.getId())).collect(Collectors.toList());
                for (JDCategoryDto c3 : crtC3List
                ) {
                    String firstPageResp = sendSearchProductRequest(ConstantsHelper.JDSearchProduct.Page_Start, ConstantsHelper.JDSearchProduct.Page_Size
                            , "", c1.getId(), Optional.of(c2.getId()), Optional.of(c3.getId()), 1);
                    JDSearchResponseDto firstPageRespDto = parseSearchRepsone(firstPageResp);
                    if (firstPageRespDto != null) {
                        JDSearchResponseDataDto dataDto = firstPageRespDto.getData();
                        if (dataDto != null) {
                            PageDto pageDto = dataDto.getPage();
                            sameCategoryLevelOneList.addAll(dataDto.getUnionGoodsParsed());
                            if (pageDto != null) {
                                sameCategoryLevelOneList.addAll(getPagedResponse(pageDto, c1.getId(), c2.getId(), c3.getId()));
                            }
                        }
                    }
                }
            }
        }
    }

    private List<JDGoodsDto> getPagedResponse(PageDto pageDto, int c1Id, int c2Id, int c3Id) {
        List<JDGoodsDto> results = new ArrayList<>();
        if (pageDto != null) {
            int pageTotalNum = (int) Math.ceil(pageDto.getTotalCount() / ConstantsHelper.JDSearchProduct.Page_Size);
            if (pageTotalNum > 1) {
                for (int p = 2; p <= pageTotalNum; p++) {
                    String pagedResp = sendSearchProductRequest(p, ConstantsHelper.JDSearchProduct.Page_Size
                            , "", c1Id, Optional.of(c2Id), Optional.of(c3Id), 1);
                    JDSearchResponseDto responseDto = parseSearchRepsone(pagedResp);
                    if (responseDto != null) {
                        JDSearchResponseDataDto dataDto = responseDto.getData();
                        if (dataDto != null && dataDto.getUnionGoodsParsed() != null) {
                            results.addAll(dataDto.getUnionGoodsParsed());
                        }
                    }
                    try {
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        return results;
    }

    private String sendSearchProductRequest(int pageNo, int pageSize, String searchUUID, int c1, Optional<Integer> c2, Optional<Integer> c3, int isZY) {
        String result = "";
        ConnectDto connectDto = createConnectObj(pageNo, pageSize, searchUUID, c1, c2, c3, isZY);
        HttpsURLConnection conn = NetworkConnect.createHttpConnection(connectDto);
        if (conn != null) {
            result = Helper.getHttpsURLConnectionResponse(conn);
        }
        return result;
    }

    private JDSearchResponseDto parseSearchRepsone(String response) {
        if (response.isEmpty()) {
            return null;
        }

        JDSearchResponseDto responseDto = JSON.parseObject(response, JDSearchResponseDto.class);
        if (responseDto.getCode().equals("200")) {
            List<Object> unionGoods = responseDto.getData().getUnionGoods();
            List<JDGoodsDto> parsedGoods = new ArrayList<>();
            for (int i = 0; i < unionGoods.size(); i++) {
                JSONObject ele = (JSONObject) ((JSONArray) responseDto.getData().getUnionGoods().get(i)).get(0);
                String jsonString = JSONObject.toJSONString(ele);
                JDGoodsDto goodsDto = JSON.parseObject(jsonString, JDGoodsDto.class);
                if (goodsDto != null) {
                    parsedGoods.add(goodsDto);
                }
            }
            responseDto.getData().setUnionGoodsParsed(parsedGoods);

        } else {
            responseDto = null;
        }

        return responseDto;
    }

    private ConnectDto createConnectObj(int pageNo, int pageSize, String searchUUID, int c1, Optional<Integer> c2, Optional<Integer> c3, int isZY) {
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

    private JDSearchResponseDto mockGetJDSearchGoodsResponse() {
        JDSearchResponseDto result = new JDSearchResponseDto();
        Path path = Paths.get("./src/main/resources/responseExample/JD查询商品返回结果.json");
        try {
            List<String> responseList = Files.readAllLines(path, Charsets.UTF_8);
            StringBuilder sb = new StringBuilder();
            responseList.forEach(x -> sb.append(x));
            result = parseSearchRepsone(sb.toString());
            result.getData().getCatList2().forEach(x -> x.setParentId(11));
            result.getData().getCatList2().forEach(x -> x.setLevel(2));
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

    //获取当前类目的前提是，上级类目一级存在。所以，一级类目是自己手动整理的。
    private List<JDCategoryDto> getCategoriesFromJD() {
        List<JDCategoryDto> results = new ArrayList<>();
        //获取二级类目
        results.addAll(getAndSaveCategoryFromJD(ConstantsHelper.JDSearchProduct.FIRST_CATEGORY_FILENAME
                , ConstantsHelper.JDSearchProduct.SHEETNAME_FIRST_CATEGORY
                , ConstantsHelper.JDSearchProduct.SHEETNAME_SECOND_CATEGORY, 2));

        //获取三级类目
        results.addAll(getAndSaveCategoryFromJD(ConstantsHelper.JDSearchProduct.SECONDE_CATEGORY_FILENAME
                , ConstantsHelper.JDSearchProduct.SHEETNAME_SECOND_CATEGORY
                , ConstantsHelper.JDSearchProduct.SHEETNAME_THIRD_CATEGORY, 3));

        return results;
    }

    private List<JDCategoryDto> getAndSaveCategoryFromJD(String toReadFileName, String toReadSheetName
            , String toSaveSheetName, int toSaveCategoryLevel) {
        Workbook workbook = openCategoryFile(toReadFileName);
        List<JDCategoryDto> results = new ArrayList<>();
        if (workbook != null) {
            results = getAndSaveCategories(readCategotyFile(workbook, toReadSheetName)
                    , toSaveSheetName, toSaveCategoryLevel);
        }

        return results;
    }

    private Workbook openCategoryFile(String fileName) {
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

    private List<JDCategoryDto> readCategotyFile(Workbook workbook, String sheetName) {
        List<JDCategoryDto> resultData = new ArrayList<>();
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet != null) {
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
                JDCategoryDto dto = convertRowToData(row);
                resultData.add(dto);
            }
        }
        return resultData;
    }

    private JDCategoryDto convertRowToData(Row row) {
        JDCategoryDto dto = new JDCategoryDto();
        Cell cell;
        int cellNum = 0;
        cell = row.getCell(cellNum++);
        dto.setCategoryName(cell.getStringCellValue());
        cell = row.getCell(cellNum++);
        dto.setId((int) cell.getNumericCellValue());
        cell = row.getCell(cellNum++);
        dto.setParentId((int) cell.getNumericCellValue());
        cell = row.getCell(cellNum);
        dto.setLevel((int) cell.getNumericCellValue());
        return dto;
    }

    private List<JDCategoryDto> getAndSaveCategories(List<JDCategoryDto> parentList, String sheetName, int level) {
        List<JDCategoryDto> results = new ArrayList<>();
        List<JDCategoryDto> crtCategories = new ArrayList<>();
        String resp = "";
        int count = 1;
        System.out.println("共有" + (level - 1) + "级类目" + parentList.size() + "个需要获取下级类目");

        for (JDCategoryDto f : parentList
        ) {
            //获取类目时一定要把"isZY"设置为0（非自营），否则一些类目就没有下级类目了
            if (level == 2) {
                resp = sendSearchProductRequest(1, 60, "", f.getId(),
                        Optional.ofNullable(null), Optional.ofNullable(null), 0);
            } else {
                resp = sendSearchProductRequest(1, 60, "", f.getParentId()
                        , Optional.of(f.getId()), Optional.ofNullable(null), 0);
            }

            if (resp.isEmpty()) {
                continue;
            }

            JDSearchResponseDto respDto = parseSearchRepsone(resp);
            if (level == 2) {
                crtCategories = respDto.getData().getCatList2();
            } else {
                crtCategories = respDto.getData().getCatList3();
            }
            if (crtCategories != null) {
                crtCategories.forEach(x -> x.setLevel(level));
                crtCategories.forEach(x -> x.setParentId(f.getId()));
                results.addAll(crtCategories);
                System.out.println("第" + (count++) + "个" + (level - 1) + "级类目完成：" + f.getCategoryName());
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        saveCategoryResultToExcel(results, sheetName);
        return results;
    }

    //sheetName也作为文件名的一部分
    private String saveCategoryResultToExcel(List<JDCategoryDto> list, String sheetName) {
        String outputPath = Helper.getProjectOutputPath();
        String filePrexfix = "京东/JD商品" + sheetName;
        String filePath = outputPath + filePrexfix + ".xls";
        File oldFile = new File(filePath);
        if (oldFile.exists()) {
            filePath = outputPath + filePrexfix + "_" + Helper.setFileNameDateFormat() + ".xls";
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        //设置表头
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("Name");
        cell = row.createCell(1);
        cell.setCellValue("Id");
        cell = row.createCell(2);
        cell.setCellValue("ParentId");
        cell = row.createCell(3);
        cell.setCellValue("Level");
        sheet.setColumnWidth(0, 30 * 256);

        for (JDCategoryDto c : list
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

    private void setObjectCategoryProperties() {
        Workbook workbook2 = openCategoryFile(ConstantsHelper.JDSearchProduct.SECONDE_CATEGORY_FILENAME);
        if (workbook2 != null) {
            this.setC2List(readCategotyFile(workbook2, ConstantsHelper.JDSearchProduct.SHEETNAME_SECOND_CATEGORY));
        }

        Workbook workbook3 = openCategoryFile(ConstantsHelper.JDSearchProduct.Third_CATEGORY_FILENAME);
        if (workbook3 != null) {
            this.setC3List(readCategotyFile(workbook3, ConstantsHelper.JDSearchProduct.SHEETNAME_THIRD_CATEGORY));
        }

        Workbook workbook4 = openCategoryFile(ConstantsHelper.JDSearchProduct.IGNORED_FIRST_CATEGORY);
        if(workbook4 != null){
            this.setC1ListIgnored(readCategotyFile(workbook4, "Sheet1"));
        }

        Workbook workbook1 = openCategoryFile(ConstantsHelper.JDSearchProduct.FIRST_CATEGORY_FILENAME);
        if (workbook1 != null) {
            List<JDCategoryDto> allC1 = readCategotyFile(workbook1, ConstantsHelper.JDSearchProduct.SHEETNAME_FIRST_CATEGORY);
            List<Integer> ignoredC1Id = this.getC1ListIgnored().stream().map(JDCategoryDto::getId).collect(Collectors.toList());
            List<JDCategoryDto> activeC1 = new ArrayList<>();
            for (JDCategoryDto c1: allC1
                 ) {
                if(!ignoredC1Id.stream().anyMatch(x->x == c1.getId())){
                    activeC1.add(c1);
                }
            }
            this.setC1List(activeC1);
        }
    }

    private void setToSearchCategories() {
        List<JDCategoryDto> list = new ArrayList<>();
        for (JDCategoryDto c3 : this.getC3List()
        ) {
            Optional<JDCategoryDto> c2 = this.getC2List().stream().filter(x -> x.getId() == c3.getParentId()).findFirst();
            if (c2.isPresent()) {
                Optional<JDCategoryDto> c1 = this.getC1List().stream().filter(x -> x.getId() == c2.get().getParentId()).findFirst();
                if (c1.isPresent()) {
                    JDCategoryDto dto = creatToSearchCategory(c3, c1.get().getId(), c2.get().getId());
                    list.add(dto);
                }
            }
        }
        //按照一级类目ID升序排列
        list.sort((o1, o2) -> o1.getCat1Id() - o2.getCat1Id());
        this.setToSearchCategoryList(list);
    }

    private JDCategoryDto creatToSearchCategory(JDCategoryDto category3, int c1Id, int c2Id) {
        JDCategoryDto d = new JDCategoryDto();
        d.setCategoryName(category3.getCategoryName());
        d.setLevel(category3.getLevel());
        d.setCat1Id(c1Id);
        d.setCat2Id(c2Id);
        d.setCat3Id(category3.getId());
        return d;
    }

    public boolean isToCreateJDCategory() {
        return isToCreateJDCategory;
    }

    public void setToCreateJDCategory(boolean toCreateJDCategory) {
        isToCreateJDCategory = toCreateJDCategory;
    }

    public List<JDCategoryDto> getC1List() {
        return c1List;
    }

    public void setC1List(List<JDCategoryDto> c1List) {
        this.c1List = c1List;
    }

    public List<JDCategoryDto> getC2List() {
        return c2List;
    }

    public void setC2List(List<JDCategoryDto> c2List) {
        this.c2List = c2List;
    }

    public List<JDCategoryDto> getC3List() {
        return c3List;
    }

    public void setC3List(List<JDCategoryDto> c3List) {
        this.c3List = c3List;
    }

    public List<JDCategoryDto> getToSearchCategoryList() {
        return toSearchCategoryList;
    }

    public void setToSearchCategoryList(List<JDCategoryDto> toSearchCategoryList) {
        this.toSearchCategoryList = toSearchCategoryList;
    }

    public List<JDCategoryDto> getC1ListIgnored() {
        return c1ListIgnored;
    }

    public void setC1ListIgnored(List<JDCategoryDto> c1ListIgnored) {
        this.c1ListIgnored = c1ListIgnored;
    }
}

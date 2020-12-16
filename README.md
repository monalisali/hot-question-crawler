# 1. 程序目的
## 1.1 通过热词，从百度和知乎抓取知乎问题内容，并进行新老数据的对比
**执行ZhihuCrawler.java**
1. 通过热词，从百度、知乎抓取所有知乎Question
2. 获取Question中的内容：**关注人数，被浏览次数**
3. 根据参数，对问题内容进行比对：关注人数增量，浏览次数增量

## 1.2 抓取京东联盟商品
**执行JDCrawler.java**
1. 通过京东联盟类目列表，获取所有3级类目下所有商品

# 2. 程序使用步骤
## 2.1 获取关键字对应的知乎问题

### 2.1.1 确保先做了这几件事
1. 确保 dbo.TopCategory（品类名称） 和 dbo.HotWord（热词） 已经有需要的数据了 <br/>
   数据由zhihu-get-dropdownlist-keywords 项目生成，[参考链接](https://github.com/monalisali/zhihu-get-dropdownlist-keywords/blob/main/README.md)

2. 确保已经为热词生成了 XZSE86 数据 <br />
   dbo.XZSE86已经存放了知乎抓取问题时需要的XZSE86加密数据。它由zhihu-login 项目生成，[参考链接](https://github.com/monalisali/zhihu-login/blob/master/%E9%87%8D%E8%A6%81%E4%BA%8B%E9%A1%B9.md)


### 2.1.2 运行 hot-question-crawler 项目下的 ZhihuCrawler.java
#### 1. 目的
   * 程序会通过热词抓取热词在百度、知乎中的"知乎问题"
   
#### 2. 需要预先设置的内容
   * 创建src\output
   * change.properties的zhiHuCookie (用"子凡"账号在网页上登录一下，做一下关键字查询操作，然后把cookie复制过来)
   * dbo.XZSE86已经存放了知乎抓取问题时需要的XZSE86加密数据。它由zhihu-login 项目生成，[参考链接](https://github.com/monalisali/zhihu-login/blob/master/%E9%87%8D%E8%A6%81%E4%BA%8B%E9%A1%B9.md)
   * app.properties
      * startDate: 问题比对，从哪一天开始比对
      * compareOffset：问题比对，要比对几天的数据
      
#### 3. 重要内容
   * 品类是否需要比对，由dbo.TopCategory中的IsActive控制：
     * 1：执行
     * 0或者null：不执行
   * 热词是否重新获取Question，由dbo.HotWord中的 isDoneZhihu,isDoneBaidu 控制
      * 从 zhihu-get-dropdownlist-keywords 添加的最新热词，isDoneZhihu,isDoneBaidu 默认都为null。所以，第一次肯定要获取下Question
      * 第一次获取Question完成后，热词的isDoneZhihu 或者 isDoneBaidu 会被设置为1。所以第二次运行程序时，默认不会再重新抓取Question，而是直接从db获取
      * isDoneZhihu 控制知乎抓取问题
      * isDoneBaidu 控制百度抓取问题
      * 1: 执行
      * 0或者null：不执行
   

#### 4. 从百度抓取知乎问题
1. ConstantsHelper.PageHelper.STARTINDEX 设置的是抓取结果的起始页，设置为0，**这个不用改**，ConstantsHelper.PageHelper.MAXPAGENUM 设置的是抓取的最大页码，
现在设置为2。也就是说，现在抓取前3页的知乎问题

#### 5. 从知乎抓取知乎问题
从知乎抓取问题（即模拟在输入框中输入关键字，然后点击"搜索"按钮的操作），有两点要特别注意：<br>
a) 先要模拟知乎登录: <br>
   hot-question-crawler 项目取巧了，没有模拟登录。而是直接用账号在浏览器中登录一下，然后把cookie复制过来 <br>
b) XZse86 请求头：<br>
   这是知乎防范爬虫的机制，具体破解可以参考：[XZse86相关说明](https://github.com/monalisali/zhihu-login/blob/master/%E9%87%8D%E8%A6%81%E4%BA%8B%E9%A1%B9.md)


#### 6. 比较结果保存
1. 文件被保存在src\output\品类名称\
2. 每次运行结果不会删除原来的文件，会以程序启动时输入的品类名称 + 时间的形式生成新的文件


## 2.2 抓取京东联盟商品
1. 目的
   * 通过三级类目ID获取该类目下的商品信息（报文中需要传入当前三级类目对应的一级、二级类目ID）
   
2. 需要预先设置的内容
  * src/output: <br>
    程序保存文件的根目录
  * src/ouput/京东/: <br>
    JD商品类目文件:
    * JD商品一级类目.xls
    * JD商品二级类目.xls
    * JD商品三级类目.xls
    * JD商品不用获取商品的一级类目.xls (自己手动做的)
  * src/output/京东/京东商品导出：<br>
    JD商品导出文件, “京东商品导出”文件夹下，每个一级类目为一个子文件
  * change.properties文件中的jdCookie ：用账号 makemoneyyy1@163.com 登录一下，获取cookie
  * app.properties文件中的isToGetJdProductCategory:<br>
    * true: 获取一级、二级、三级类目文件
    * false：不获取
    * src/ouput/京东/ 下如果已经有JD商品一级类目.xls，JD商品二级类目.xls，JD商品三级类目.xls，那么就设置为false
 
### 2.2.1 运行 hot-question-crawler 项目下的 JDCrawler.java

### 2.2.3 结果保存
1. 每一个一级类目作为一个独立的文件，保存在src/ouput/京东/京东商品导出/一级类目名称/
2. 每次运行结果不会删除原来的文件，会以一级类目 + 时间的形式生成新的文件


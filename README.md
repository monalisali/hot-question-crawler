# 1. 程序目的
## 1.1 通过长尾词，从百度和知乎抓取相关的知乎问题内容
1. 通过关键字，从百度抓取所有知乎的Question
2. 通过关键字，模拟知乎关键字搜索操作，抓取关键字对应的Question
3. 把1,2中的关键字取交集
4. 抓取3中Question的数据：**关注人数，被浏览次数**
5. 保存结果到\src\output\品类名称\

## 1.2 抓取京东联盟商品
1. 一级商品目录是自己手动准备的
2. 通过一级类目ID，获取二级类目
3. 通过二级类目，获取三级类目
4. 通过一级、二级、三级目录文件，获取每个三级类目的: 一级类目ID，二级类目ID
5. 通过4）的数据,构建每个三级类目的请求报文，从而获取类目下的商品信息
6. 文件保存到\src\output\京东\


# 2. 使用程序步骤
## 2.1 获取关键字对应的知乎问题


### 2.1.1 运行 zhihu-get-dropdownlist-keywords 项目

1. 获取某个品类在知乎下拉框中所有的关键字
2. 把结果复制到 hot-question-crawler 项目的 hotWordsToQuestion.txt 中


### 2.1.2 运行 hot-question-crawler 项目下的 ZhihuCrawler.java
1. 目的
   * 程序会通过关键字抓取关键字在百度、知乎中的"知乎问题"
   
2. 预先需要设置的内容
   * 在src\output
   * 确认 ConstantsHelper.CAETGORYNAME： 它当前需要处理的品类名称，会作为执行结果的文件夹和文件前缀
   * hotWordsToQuestion.txt：百度抓取用到的关键词，不要放历史内容
   * output/xZse86Result.json：知乎抓取用到的关键字 + 每个关键字对应的xZse86值，不要放历史内容
   * change.properties的zhiHuCookie ("子凡"账号)
   

#### 2.1.2.1 从百度抓取知乎问题
1. hotWordsToQuestion.txt 有当前需要处理的关键字
2. ConstantsHelper.PageHelper.STARTINDEX 设置的是抓取结果的起始页，设置为0，**这个不用改**， ConstantsHelper.PageHelper.MAXPAGENUM 设置的是抓取的最大页码，
现在设置为2。也就是说，现在抓取前3也的知乎问题

#### 2.1.2.2 从知乎抓取知乎问题
从知乎抓取问题（即模拟在输入框中输入关键字，然后点击"搜索"按钮的操作），有两点要特别注意：**先要登录，XZse86 请求头**
[XZse86相关说明](https://github.com/monalisali/zhihu-login/blob/master/%E9%87%8D%E8%A6%81%E4%BA%8B%E9%A1%B9.md)

1. 把 hot-question-crawler 中的 hotWordsToQuestion.txt 内容，复制到Python项目——zhihu-login 中的 hotWordsToQuestion.txt 中去
2. zhihu-login 中的 _encryptXZse86Value() 会为每个关键字计算出对应的XZse86值，并保存在 output/xZse86Result.json
3. 把 zhihu-login 中的 output/xZse86Result.json 内容，复制到 hot-question-crawler 中的 xZse86Result.json 
4. 用"子凡"账号，在网页上登录一下知乎，并做一次查询，拿到返回的cookie，并把它复制到change.properties的zhiHuCookie中去

**从zhihu-login的 xZse86Result.json 复制内容到 hot-question-crawler 时要注意：**
a) 要么直接复制过去
b) 如果要复制到印象笔记中以后备用的话，一定要写复制到记事本，然后再复制到印象笔记的一般笔记中。**不要复制到印象笔记的markdown中，从markdown在复制到 hot-question-crawler 后，fastJson在
  解析时会报错，不知道什么原因**

#### 2.1.2.3 结果保存
1. 文件被保存在src\output\ConstantsHelper.CAETGORYNAME\
2. 每次运行结果不会删除原来的文件，会以ConstantsHelper.CAETGORYNAME + 时间的形式生成新的文件


## 2.2 抓取京东联盟商品
1. 目的
   * 通过京东商品一级类目、二级类目、三级类目获取类目下的商品信息
   
2. 预先需要设置的内容
  * src/output: 程序保存文件的根目录
  * src/ouput/京东: JD商品类目文件
  * src/output/京东/京东商品导出：JD商品导出文件, “京东商品导出”文件夹下，每个一级类目为一个子文件
  * change.properties文件中的jdCookie （makemoneyyy1@163.com）
 
### 2.2.1 运行 hot-question-crawler 项目下的 JDCrawler.java

1. 检查 src/output/京东/目录下是否有: JD商品一级类目.xls、JD商品二级类目.xls、JD商品三级类目.xls
2. 没有的话，要把 app.properies 中的 isToGetJdProductCategory 值设置为 true: 程序会先执行类目抓取再进行商品抓取。设置为false，则直接进行商品抓取
3. 用 makemoneyyy1@163.com 在网页上登陆一次京东联盟，然后把返回的cookie，并把它复制到change.properties中的jdCookie

### 2.2.3 结果保存
1. 每一个一级类目作为一个独立的文件，保存在src/ouput/京东/京东商品导出/一级类目名称/
2. 每次运行结果不会删除原来的文件，会以一级类目 + 时间的形式生成新的文件




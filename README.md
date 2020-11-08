### 1. 程序目的
1. 通过关键字，从百度抓取所有知乎的Question
2. 通过关键字，模拟知乎关键字搜索操作，抓取关键字对应的Question
3. 把1,2中的关键字取交集
4. 抓取3中Question的数据：关注人数，被浏览次数
5. 保存结果到xlsx


### 2. 执行前的准备工作
1. 运行 zhihu-get-dropdownlist-keywords 项目，把结果复制到 hot-question-crawler 项目的 hotWordsToQuestion.txt 中
2. 运行 Python 项目—— zhihu-login，把 _encryptXZse86Value()，保存在 \output\xZse86Result.json 中的结果复制到 hot-question-crawler 
   项目的 xZse86Result.json 中
3. 确认 ConstantsHelper.CAETGORYNAME 的值是否为当前需要处理的品类名称，它会作为保存的文件夹和文件名前缀

### 3. 说明

#### 3.1 hotWordsToQuestion.txt
1. 文件中的内容是 zhihu-get-dropdownlist-keywords 项目运行的结果。**文件中只放入当前需要的热词，不要放任何历史记录**
2. 它主要给QuestionFromBaidu.java使用

#### 3.2 xZse86Result.json
1. 文件中的内容是 Python 项目—— zhihu-login 中 _encryptXZse86Value()运行后，保存在Python项目目录下的 \output\xZse86Result.json 文件内容
2. 它主要用来为QuestionFromZhihu.java使用


#### 3.3 src/output/
保存所有处理结果文件



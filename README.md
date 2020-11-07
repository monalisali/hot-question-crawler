### 1. 程序目的
1. 通过关键字，从百度抓取所有知乎的Question
2. 通过关键字，模拟知乎关键字搜索操作，抓取关键字对应的Question
3. 把1,2中的关键字取交集


### 2. 执行前的准备工作
1. 运行 zhihu-get-dropdownlist-keywords 项目，把结果复制到 hot-question-crawler 项目的 hotWordsToQuestion.txt 中
2. 运行 Python 项目—— zhihu-login，把 _encryptXZse86Value()，保存在 \output\xZse86Result.json 中的结果复制到 hot-question-crawler 
   项目的 xZse86Result.json 中

### 3. 说明

#### 3.1 hotWordsToQuestion.txt
1. 文件中的内容是 zhihu-get-dropdownlist-keywords 项目运行的结果。**文件中只放入当前需要的热词，不要放任何历史记录**
2. 它主要给QuestionFromBaidu.java使用

#### 3.2 xZse86Result.json
1. 文件中的内容是 Python 项目—— zhihu-login 中 _encryptXZse86Value()运行后，保存在Python项目目录下的 \output\xZse86Result.json 文件内容
2. 它主要用来为QuestionFromZhihu.java使用




# AGENTS.md - IWillReciteWords 背单词项目

## 项目概述
JavaFX 桌面背单词应用，支持单词库管理、错词复习、学习统计。

## 技术栈
- Java 11
- JavaFX 17
- Maven
- JUnit 5

## 目录结构

src/main/java/com/iwillrecitewords/
- Main.java (程序入口)
- controller/ (控制器)
- model/ (Word数据模型)
- service/ (业务逻辑)
    - WordService.java (单词库管理)
    - WrongWordService.java (错词管理)
    - StudyStatService.java (学习统计)
- util/ (FileUtil工具类)
- view/ (视图层)

src/test/java/com/sprint0/nexus/
- statistics/ (统计相关测试)
- service/ (服务层测试)

## 核心模块职责

=== WordService ===
- getRandomWord() - 随机获取单词
- getTotalWordCount() - 获取单词总数
- getWordLibrary() - 获取全部单词

=== WrongWordService ===
- addWrongWord(Word) - 添加错词
- getWrongWordList() - 获取错词列表
- getWrongWordCount() - 获取错词数量

=== StudyStatService ===
- incrementLearnedCount() - 增加已学单词数
- getLearnedCount() - 获取已学单词数

## 编码规范
- 缩进：4个空格
- public方法必须有Javadoc注释
- 类名：PascalCase (如 WordService)
- 方法名：camelCase (如 getRandomWord)
- 包名：全小写

## 禁止操作
- 不要修改 module-info.java
- 不要在 service 中直接操作 UI 组件
- 不要硬编码文件路径
- 不要删除 words.txt 和 wrong_words.txt 数据文件

## 测试规范
- 测试类命名：类名 + Test
- 使用 @BeforeEach 初始化测试数据
- 测试方法命名：test + 方法名 + _When + 场景

## 运行命令
mvn clean compile (编译)
mvn test (运行测试)
mvn javafx:run (运行应用)

## 数据文件
- words.txt - 单词库 (共9602个单词)
- wrong_words.txt - 错词本 (自动生成)
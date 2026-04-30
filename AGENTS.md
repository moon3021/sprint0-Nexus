# IWillReciteWords AGENTS.md
## 项目架构概述
基于JavaFX 22的考研英语背单词桌面应用，采用MVP分层架构。
- View层：JavaFX FXML + 控制器，负责界面渲染与用户交互
- Controller层：事件处理与视图调度，连接View和Service
- Service层：纯业务逻辑，无UI依赖，可独立测试
- Model层：不可变数据实体，仅提供getter方法
- Util层：通用工具类，封装文件读写等底层操作

## 目录结构说明
```
IWillReciteWords/
├── src/main/java/com/iwillrecitewords/
│ ├── controller/ # MVP 控制器层（事件处理）
│ ├── model/ # 数据实体类
│ ├── service/ # 业务服务类（核心修改区）
│ ├── util/ # 通用工具类
│ ├── view/ # JavaFX 视图类
│ ├── Main.java # 程序入口
│ └── MainUI.java # 主界面入口
├── src/test/java/ # JUnit 5 单元测试
├── src/main/resources/ # FXML 文件、词库、图片资源
└── pom.xml # Maven 项目配置
```

## 核心模块职责
| 模块 | 核心职责 | 对外接口 |
|------|----------|----------|
| HomeController | 主页事件处理，跳转各功能页面 | initialize() |
| LearnController | 背单词页面逻辑，处理"认识/记错"事件 | initialize() |
| WordService | 词库加载、随机单词获取、词库统计 | getRandomWord(), getWordCount() |
| WrongWordService | 错词添加、去重、持久化、查询 | addWrongWord(), getWrongWordList(), getWrongWordCount() |
| FileUtil | 通用UTF-8文件读写工具 | readFile(String), writeFile(String, String) |

## 编码规范约束
1.  严格遵循MVP分层：View只做渲染，Controller只做事件转发，所有业务逻辑必须写在Service层
2.  所有public方法必须做入参前置校验，禁止传入null
3.  Word实体类是不可变对象（所有字段final），仅提供getter，不提供setter
4.  所有文件读写必须通过FileUtil，禁止直接使用java.io.File
5.  提交代码前必须确保本地执行`mvn clean test`全绿通过

## 禁止操作清单
❌ 禁止在View或Controller层编写任何业务逻辑
❌ 禁止修改Word类的final字段或添加setter方法
❌ 禁止硬编码文件路径，统一使用相对路径
❌ 禁止跳过单元测试直接合并代码
❌ 禁止在单个PR中同时修改超过2个核心模块
❌ 禁止引入任何非必要的第三方依赖
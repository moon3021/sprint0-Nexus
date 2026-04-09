// Sprint1 原始代码完整备份，Sprint2重构前快照
package com.iwillrecitewords;

import com.iwillrecitewords.service.StudyStatService;
import com.iwillrecitewords.service.WordService;
import com.iwillrecitewords.view.HomeView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 程序入口：设置合适的窗口大小，非全屏
 */
public class MainUI extends Application {
    // 全局服务单例，全页面复用
    public static final WordService WORD_SERVICE = new WordService();
    public static final StudyStatService STAT_SERVICE = new StudyStatService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 窗口基础配置
        primaryStage.setTitle("我会背单词 - 考研英语学习工具");

        // ====================== 核心修改：设置合适的窗口大小 ======================
        // 1. 设置合适的初始尺寸（适配绝大多数笔记本电脑）
        primaryStage.setWidth(1100);
        primaryStage.setHeight(750);

        // 2. 允许用户调整窗口大小，但设置最小尺寸防止布局错乱
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);

        // 3. 【关键】强制取消全屏/最大化，启动时就是合适的窗口大小
        primaryStage.setMaximized(false);
        // 启动主页面
        HomeView homeView = new HomeView(primaryStage);
        primaryStage.setScene(homeView.getScene());

        // 显示窗口
        primaryStage.show();
    }
}
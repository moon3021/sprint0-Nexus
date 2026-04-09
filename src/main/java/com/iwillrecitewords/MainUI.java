package com.iwillrecitewords;

import com.iwillrecitewords.service.StudyStatService;
import com.iwillrecitewords.service.WordService;
import com.iwillrecitewords.service.WrongWordService;
import com.iwillrecitewords.view.HomeView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * 程序主入口（适配BaseView重构，无getScene()调用）
 */
public class MainUI extends Application {

    // 全局静态服务实例
    public static final WordService WORD_SERVICE = new WordService();
    public static final StudyStatService STAT_SERVICE = new StudyStatService();
    public static final WrongWordService WRONG_WORD_SERVICE = new WrongWordService();

    @Override
    public void start(Stage primaryStage) {
        // 窗口基础配置
        primaryStage.setTitle("I Will Recite Words 背单词软件");
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);

        // 重构后：直接调用 show() 显示页面，不再用 getScene()
        HomeView homeView = new HomeView(primaryStage);
        homeView.show();

        // 显示窗口
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
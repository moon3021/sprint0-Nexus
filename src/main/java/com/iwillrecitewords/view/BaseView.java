package com.iwillrecitewords.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 页面基类（最终修复版：解决跨包访问权限问题）
 */
public abstract class BaseView {
    protected final Stage stage;
    protected Scene scene;

    public BaseView(Stage stage) {
        this.stage = stage;
    }

    protected void init() {
        this.scene = initUI();
        bindEvents();
    }

    protected abstract Scene initUI();
    protected abstract void bindEvents();

    public void show() {
        stage.setScene(scene);
        stage.show();
    }

    // ====================== 【核心修复】把 protected 改为 public ======================
    public void switchTo(BaseView targetView) {
        targetView.show();
    }

    public Scene getScene() {
        return scene;
    }

    public Stage getStage() {
        return stage;
    }
}
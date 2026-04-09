package com.iwillrecitewords.controller;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.view.HomeView;
import javafx.application.Platform;
import javafx.scene.control.Alert;

/**
 * 主页面控制器：集中处理主页面的所有业务逻辑
 */
public class HomeController {
    private HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
    }

    /**
     * 获取今日已学数量（给View调用）
     */
    public int getLearnedCount() {
        return MainUI.STAT_SERVICE.getLearnedCount();
    }

    /**
     * 获取错词复习数量（给View调用）
     */
    public int getReviewCount() {
        return MainUI.WRONG_WORD_SERVICE.getWrongWordCount();
    }

    /**
     * 处理签到按钮点击（业务逻辑）
     */
    public void handleSignInClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("签到成功");
            alert.setHeaderText("🎉 今日签到完成！");
            alert.setContentText("已为你记录今日学习，继续加油吧！");
            alert.showAndWait();
        });
    }

    /**
     * 处理错词复习按钮点击（业务逻辑）
     */
    public void handleReviewClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("功能开发中");
            alert.setHeaderText("📖 错词复习功能");
            alert.setContentText("该功能将在Sprint3上线，敬请期待！");
            alert.showAndWait();
        });
    }

    /**
     * 处理词库按钮点击（业务逻辑）
     */
    public void handleBookClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("功能开发中");
            alert.setHeaderText("📕 词库管理功能");
            alert.setContentText("该功能将在Sprint3上线，敬请期待！");
            alert.showAndWait();
        });
    }

    /**
     * 处理统计按钮点击（业务逻辑）
     */
    public void handleStatClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("功能开发中");
            alert.setHeaderText("📊 学习统计功能");
            alert.setContentText("该功能将在Sprint3上线，敬请期待！");
            alert.showAndWait();
        });
    }

    /**
     * 刷新统计数据（给View调用，后续扩展用）
     */
    public void refreshStatData() {
        // 后续可以扩展：从文件重新加载统计数据
    }
}
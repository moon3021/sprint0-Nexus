package com.iwillrecitewords.controller;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.strategy.WrongWordReviewStrategy;
import com.iwillrecitewords.view.HomeView;
import com.iwillrecitewords.view.LearnView;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class HomeController {
    private HomeView view;

    public HomeController(HomeView view) {
        this.view = view;
    }

    public int getLearnedCount() {
        return MainUI.STAT_SERVICE.getLearnedCount();
    }

    public int getReviewCount() {
        return MainUI.WRONG_WORD_SERVICE.getWrongWordCount();
    }

    public void handleSignInClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("签到成功");
            alert.setHeaderText("🎉 今日签到完成！");
            alert.setContentText("已为你记录今日学习，继续加油吧！");
            alert.showAndWait();
        });
    }

    public void handleReviewClick() {
        Platform.runLater(() -> {
            int wrongWordCount = MainUI.WRONG_WORD_SERVICE.getWrongWordCount();
            if (wrongWordCount <= 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("暂无错词");
                alert.setHeaderText("📖 你的错词本是空的");
                alert.setContentText("先去学习单词，标记记错的单词，再来复习吧！");
                alert.showAndWait();
                return;
            }

            // 现在 switchTo 是 public，跨包可以正常访问了！
            LearnView reviewView = new LearnView(view.getStage(), new WrongWordReviewStrategy());
            view.switchTo(reviewView);
        });
    }

    public void handleBookClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("功能开发中");
            alert.setHeaderText("📕 词库管理功能");
            alert.setContentText("该功能将在Sprint3上线，敬请期待！");
            alert.showAndWait();
        });
    }

    public void handleStatClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("功能开发中");
            alert.setHeaderText("📊 学习统计功能");
            alert.setContentText("该功能将在Sprint3上线，敬请期待！");
            alert.showAndWait();
        });
    }

    public void refreshStatData() {}
}
package com.iwillrecitewords.controller;

import com.iwillrecitewords.MainUI;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.view.HomeView;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.util.List;

/**
 * 主页面控制器：严格遵循MVP架构，仅调度业务逻辑
 * Sprint3完成：词库管理+错词复习功能（弹窗实现，贴合项目原生设计）
 */
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

    // 签到功能（原有逻辑不变）
    public void handleSignInClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("签到成功");
            alert.setHeaderText("🎉 今日签到完成！");
            alert.setContentText("已为你记录今日学习，继续加油吧！");
            alert.showAndWait();
        });
    }

    // ====================== Sprint3 完成：错词复习功能 ======================
    public void handleReviewClick() {
        Platform.runLater(() -> {
            List<Word> wrongWords = MainUI.WRONG_WORD_SERVICE.getWrongWordList();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错词复习");
            alert.setHeaderText("📖 我的错词列表");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setPrefSize(600, 400);

            StringBuilder content = new StringBuilder();
            if (wrongWords.isEmpty()) {
                content.append("太棒了！当前没有错词~");
            } else {
                for (Word word : wrongWords) {
                    content.append(word.getWord()).append(" [").append(word.getPhonetic()).append("]\n");
                    content.append("释义：").append(word.getMeaning()).append("\n\n");
                }
            }
            textArea.setText(content.toString());

            VBox vBox = new VBox(textArea);
            alert.getDialogPane().setContent(vBox);
            alert.showAndWait();
        });
    }

    // ====================== Sprint3 完成：词库管理功能 ======================
    public void handleBookClick() {
        Platform.runLater(() -> {
            List<Word> wordLibrary = MainUI.WORD_SERVICE.getWordLibrary();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("词库管理");
            alert.setHeaderText("📕 考研英语词库");

            TextArea textArea = new TextArea();
            textArea.setEditable(false);
            textArea.setPrefSize(600, 400);

            StringBuilder content = new StringBuilder();
            for (Word word : wordLibrary) {
                content.append(word.getWord()).append(" [").append(word.getPhonetic()).append("]\n");
                content.append("释义：").append(word.getMeaning()).append("\n\n");
            }
            textArea.setText(content.toString());

            VBox vBox = new VBox(textArea);
            alert.getDialogPane().setContent(vBox);
            alert.showAndWait();
        });
    }

    // 统计功能（保持开发中，不变）
    public void handleStatClick() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("功能开发中");
            alert.setHeaderText("📊 学习统计功能");
            alert.setContentText("该功能将在后续迭代上线！");
            alert.showAndWait();
        });
    }

    public void refreshStatData() {}
}
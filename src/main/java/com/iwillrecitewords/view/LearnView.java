// Sprint1 原始代码完整备份，Sprint2重构前快照
package com.iwillrecitewords.view;

import com.iwillrecitewords.controller.LearnController;
import com.iwillrecitewords.model.Word;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * 纯净背单词页面：仅负责UI渲染，业务逻辑全部委托给LearnController
 */
public class LearnView {
    private final Stage stage;
    private final LearnController controller; // 持有Controller引用，只调用Controller方法

    // UI组件（仅保留UI相关）
    private Label wordLabel;
    private Label phoneticLabel;
    private Label meaningLabel;
    private Label exampleEnLabel;
    private Label exampleCnLabel;
    private Label learnedCountLabel;

    // 构造函数
    public LearnView(Stage stage) {
        this.stage = stage;
        // 初始化Controller，把自己传给Controller
        this.controller = new LearnController(this);
    }

    public Scene getScene() {
        // 根布局：BorderPane适配宽屏
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 248, 245),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        // ====================== 顶部：返回按钮+学习统计 ======================
        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(30, 40, 20, 40));
        topBar.setSpacing(20);

        // 返回主页按钮
        Button backBtn = new Button("← 返回主页");
        backBtn.setFont(Font.font("System", FontWeight.NORMAL, 18));
        backBtn.setBackground(Background.EMPTY);
        backBtn.setTextFill(Color.rgb(100, 100, 100));
        backBtn.setCursor(javafx.scene.Cursor.HAND);
        backBtn.setOnAction(e -> {
            // 返回主页面（UI跳转，无业务逻辑）
            HomeView homeView = new HomeView(stage);
            stage.setScene(homeView.getScene());
        });

        // 已学数量统计（从Controller获取数据）
        learnedCountLabel = new Label("📚 今日已学：" + controller.getLearnedCount() + " 词");
        learnedCountLabel.setFont(Font.font(18));
        learnedCountLabel.setTextFill(Color.rgb(120, 120, 120));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(backBtn, spacer, learnedCountLabel);
        root.setTop(topBar);

        // ====================== 中间：单词核心内容 ======================
        VBox wordContent = new VBox(40);
        wordContent.setAlignment(Pos.CENTER);
        wordContent.setPadding(new Insets(40, 100, 40, 100));
        wordContent.setMaxWidth(800);

        // 从Controller获取当前单词，初始化UI
        Word currentWord = controller.getCurrentWord();

        // 单词主体
        wordLabel = new Label(currentWord.getWord());
        wordLabel.setFont(Font.font("System", FontWeight.BOLD, 64));
        wordLabel.setTextFill(Color.BLACK);

        // 音标
        phoneticLabel = new Label(currentWord.getPhonetic().isEmpty() ? "" : currentWord.getPhonetic());
        phoneticLabel.setFont(Font.font(32));
        phoneticLabel.setTextFill(Color.rgb(80, 80, 80));

        // 词性+释义（从Controller获取拼接好的内容）
        meaningLabel = new Label(controller.getPosAndMeaning(currentWord));
        meaningLabel.setFont(Font.font(32));
        meaningLabel.setTextFill(Color.rgb(50, 50, 50));
        meaningLabel.setWrapText(true);

        // 例句区域
        VBox exampleBox = new VBox(15);
        exampleBox.setPadding(new Insets(30, 0, 30, 0));
        exampleBox.setAlignment(Pos.CENTER_LEFT);

        exampleEnLabel = new Label(currentWord.getExampleEn().isEmpty() ? "" : currentWord.getExampleEn());
        exampleEnLabel.setFont(Font.font(24));
        exampleEnLabel.setWrapText(true);

        exampleCnLabel = new Label(currentWord.getExampleCn().isEmpty() ? "" : currentWord.getExampleCn());
        exampleCnLabel.setFont(Font.font(24));
        exampleCnLabel.setTextFill(Color.rgb(100, 100, 100));
        exampleCnLabel.setWrapText(true);

        exampleBox.getChildren().addAll(exampleEnLabel, exampleCnLabel);
        wordContent.getChildren().addAll(wordLabel, phoneticLabel, meaningLabel, exampleBox);
        root.setCenter(wordContent);

        // ====================== 底部：操作按钮 ======================
        HBox bottomBtnBox = new HBox(100);
        bottomBtnBox.setAlignment(Pos.CENTER);
        bottomBtnBox.setPadding(new Insets(40, 0, 60, 0));

        // 记错了按钮
        Button wrongBtn = new Button("记错了");
        wrongBtn.setFont(Font.font("System", FontWeight.BOLD, 24));
        wrongBtn.setTextFill(Color.rgb(255, 59, 48));
        wrongBtn.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 240, 240),
                new CornerRadii(15),
                Insets.EMPTY
        )));
        wrongBtn.setPrefSize(200, 80);
        wrongBtn.setCursor(javafx.scene.Cursor.HAND);
        // 按钮点击只调用Controller方法，无业务逻辑
        wrongBtn.setOnAction(e -> controller.handleWrongButtonClick());

        // 继续按钮
        Button nextBtn = new Button("认识，下一个");
        nextBtn.setFont(Font.font("System", FontWeight.BOLD, 24));
        nextBtn.setTextFill(Color.WHITE);
        nextBtn.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 175, 85),
                new CornerRadii(15),
                Insets.EMPTY
        )));
        nextBtn.setPrefSize(280, 80);
        nextBtn.setCursor(javafx.scene.Cursor.HAND);
        // 按钮点击只调用Controller方法，无业务逻辑
        nextBtn.setOnAction(e -> controller.handleKnowButtonClick());

        bottomBtnBox.getChildren().addAll(wrongBtn, nextBtn);
        root.setBottom(bottomBtnBox);

        return new Scene(root);
    }

    // ====================== 暴露给Controller调用的UI更新方法 ======================
    /**
     * 更新单词相关的UI（给Controller调用）
     */
    public void updateWordUI(Word word) {
        wordLabel.setText(word.getWord());
        phoneticLabel.setText(word.getPhonetic().isEmpty() ? "" : word.getPhonetic());
        meaningLabel.setText(controller.getPosAndMeaning(word));
        exampleEnLabel.setText(word.getExampleEn().isEmpty() ? "" : word.getExampleEn());
        exampleCnLabel.setText(word.getExampleCn().isEmpty() ? "" : word.getExampleCn());
    }

    /**
     * 更新已学数量的UI（给Controller调用）
     */
    public void updateLearnedCountUI() {
        learnedCountLabel.setText("📚 今日已学：" + controller.getLearnedCount() + " 词");
    }
}
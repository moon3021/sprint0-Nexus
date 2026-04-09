package com.iwillrecitewords.view;

import com.iwillrecitewords.controller.LearnController;
import com.iwillrecitewords.model.Word;
import com.iwillrecitewords.strategy.LearningStrategy;
import com.iwillrecitewords.strategy.NormalLearnStrategy;
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

public class LearnView extends BaseView {
    private final LearnController controller;

    private Label wordLabel;
    private Label phoneticLabel;
    private Label meaningLabel;
    private Label exampleEnLabel;
    private Label exampleCnLabel;
    private Label progressLabel;

    // 【默认构造函数】普通学习模式，和之前完全兼容
    public LearnView(Stage stage) {
        this(stage, new NormalLearnStrategy());
    }

    // 【新增构造函数】支持外部传入学习策略（错词复习用这个）
    public LearnView(Stage stage, LearningStrategy strategy) {
        super(stage);
        this.controller = new LearnController(this, strategy);
        init();
    }

    @Override
    protected Scene initUI() {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 248, 245),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(30, 40, 20, 40));

        Button backBtn = new Button("← 返回主页");
        backBtn.setFont(Font.font(18));
        backBtn.setBackground(Background.EMPTY);
        backBtn.setTextFill(Color.GRAY);
        backBtn.setCursor(javafx.scene.Cursor.HAND);
        backBtn.setId("backBtn");

        progressLabel = new Label();
        progressLabel.setFont(Font.font(18));
        progressLabel.setTextFill(Color.DARKGRAY);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(backBtn, spacer, progressLabel);
        root.setTop(topBar);

        VBox contentBox = new VBox(30);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(50, 100, 50, 100));
        contentBox.setMaxWidth(900);

        wordLabel = new Label();
        wordLabel.setFont(Font.font("System", FontWeight.BOLD, 60));

        phoneticLabel = new Label();
        phoneticLabel.setFont(Font.font(30));
        phoneticLabel.setTextFill(Color.GRAY);

        meaningLabel = new Label();
        meaningLabel.setFont(Font.font(32));
        meaningLabel.setWrapText(true);

        VBox exampleBox = new VBox(15);
        exampleEnLabel = new Label();
        exampleCnLabel = new Label();
        exampleEnLabel.setFont(Font.font(24));
        exampleCnLabel.setFont(Font.font(24));
        exampleCnLabel.setTextFill(Color.GRAY);
        exampleEnLabel.setWrapText(true);
        exampleCnLabel.setWrapText(true);
        exampleBox.getChildren().addAll(exampleEnLabel, exampleCnLabel);

        contentBox.getChildren().addAll(wordLabel, phoneticLabel, meaningLabel, exampleBox);
        root.setCenter(contentBox);

        HBox btnBox = new HBox(60);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(40, 0, 60, 0));

        Button wrongBtn = new Button("记错了");
        wrongBtn.setFont(Font.font(24));
        wrongBtn.setStyle("-fx-background-color: #FFECEC; -fx-text-fill: #FF3B30; -fx-background-radius: 15;");
        wrongBtn.setPrefSize(200, 80);
        wrongBtn.setCursor(javafx.scene.Cursor.HAND);
        wrongBtn.setId("wrongBtn");

        Button knowBtn = new Button("认识，下一个");
        knowBtn.setFont(Font.font(24));
        knowBtn.setStyle("-fx-background-color: #00AF54; -fx-text-fill: white; -fx-background-radius: 15;");
        knowBtn.setPrefSize(280, 80);
        knowBtn.setCursor(javafx.scene.Cursor.HAND);
        knowBtn.setId("knowBtn");

        btnBox.getChildren().addAll(wrongBtn, knowBtn);
        root.setBottom(btnBox);

        // 初始化UI数据
        updateWordUI(controller.getCurrentWord());
        updateLearnedCountUI();

        return new Scene(root, 1280, 800);
    }

    @Override
    protected void bindEvents() {
        Button backBtn = (Button) scene.lookup("#backBtn");
        backBtn.setOnAction(e -> switchTo(new HomeView(stage)));

        Button wrongBtn = (Button) scene.lookup("#wrongBtn");
        wrongBtn.setOnAction(e -> controller.handleWrongButtonClick());

        Button knowBtn = (Button) scene.lookup("#knowBtn");
        knowBtn.setOnAction(e -> controller.handleKnowButtonClick());
    }

    // ====================== 给 Controller 调用的更新方法 ======================
    public void updateWordUI(Word word) {
        if (word == null) return;

        wordLabel.setText(word.word());
        phoneticLabel.setText(word.hasPhonetic() ? word.phonetic() : "");
        meaningLabel.setText(word.partOfSpeech() + " " + word.meaning());
        exampleEnLabel.setText(word.exampleEn());
        exampleCnLabel.setText(word.exampleCn());
    }

    public void updateLearnedCountUI() {
        progressLabel.setText(controller.getProgressText());
    }

    public void showLearningComplete() {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("学习完成");
        alert.setHeaderText("🎉 本轮学习已全部完成！");
        alert.showAndWait();
        switchTo(new HomeView(stage));
    }
}
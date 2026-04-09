package com.iwillrecitewords.view;

import com.iwillrecitewords.controller.HomeController;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class HomeView extends BaseView {
    private final HomeController controller;

    public HomeView(Stage stage) {
        super(stage);
        // 【关键1】先创建controller
        this.controller = new HomeController(this);
        // 【关键2】再初始化UI
        init();
    }

    @Override
    protected Scene initUI() {
        BorderPane root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 240, 235),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        // 顶部签到
        VBox topContainer = new VBox(20);
        topContainer.setAlignment(Pos.CENTER);
        topContainer.setPadding(new Insets(40, 0, 20, 0));

        StackPane signInCard = new StackPane();
        signInCard.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.7),
                new CornerRadii(25),
                Insets.EMPTY
        )));
        signInCard.setPrefSize(400, 180);
        signInCard.setCursor(javafx.scene.Cursor.HAND);

        VBox signInContent = new VBox(10);
        signInContent.setAlignment(Pos.CENTER);
        Label signInIcon = new Label("📅");
        signInIcon.setFont(Font.font(36));
        Label signInTitle = new Label("每日签到");
        signInTitle.setFont(Font.font("System", FontWeight.BOLD, 32));
        Label dateLabel = new Label(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 EEEE")));
        dateLabel.setFont(Font.font(18));
        dateLabel.setTextFill(Color.rgb(80, 80, 80));
        signInContent.getChildren().addAll(signInIcon, signInTitle, dateLabel);

        Button signInBtn = new Button();
        signInBtn.setPrefSize(400, 180);
        signInBtn.setOpacity(0);
        signInBtn.setCursor(javafx.scene.Cursor.HAND);
        signInBtn.setId("signInBtn");

        signInCard.getChildren().addAll(signInContent, signInBtn);
        topContainer.getChildren().add(signInCard);
        root.setTop(topContainer);

        // 中间功能按钮
        HBox centerContainer = new HBox(80);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setPadding(new Insets(20, 0, 40, 0));

        StackPane learnCard = createFunctionCard(
                "📚 开始学习",
                "Learn",
                String.valueOf(controller.getLearnedCount()),
                Color.rgb(255, 110, 30)
        );
        learnCard.setId("learnCard");

        StackPane reviewCard = createFunctionCard(
                "📖 错词复习",
                "Review",
                String.valueOf(controller.getReviewCount()),
                Color.rgb(30, 130, 255)
        );
        reviewCard.setId("reviewCard");

        centerContainer.getChildren().addAll(learnCard, reviewCard);
        root.setCenter(centerContainer);

        // 底部导航
        HBox bottomNav = new HBox(200);
        bottomNav.setAlignment(Pos.CENTER);
        bottomNav.setPadding(new Insets(30, 0, 40, 0));
        bottomNav.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.5),
                CornerRadii.EMPTY,
                Insets.EMPTY
        )));

        Label homeNav = new Label("🏠 首页");
        homeNav.setFont(Font.font("System", FontWeight.BOLD, 20));
        homeNav.setTextFill(Color.rgb(255, 110, 30));
        homeNav.setCursor(javafx.scene.Cursor.HAND);

        Label bookNav = new Label("📕 词库");
        bookNav.setFont(Font.font("System", FontWeight.BOLD, 20));
        bookNav.setTextFill(Color.rgb(100, 100, 100));
        bookNav.setCursor(javafx.scene.Cursor.HAND);
        bookNav.setId("bookNav");

        Label statNav = new Label("📊 统计");
        statNav.setFont(Font.font("System", FontWeight.BOLD, 20));
        statNav.setTextFill(Color.rgb(100, 100, 100));
        statNav.setCursor(javafx.scene.Cursor.HAND);
        statNav.setId("statNav");

        bottomNav.getChildren().addAll(homeNav, bookNav, statNav);
        root.setBottom(bottomNav);

        return new Scene(root, 1280, 800);
    }

    @Override
    protected void bindEvents() {
        Button signInBtn = (Button) scene.lookup("#signInBtn");
        signInBtn.setOnAction(e -> controller.handleSignInClick());

        Button learnBtn = (Button) scene.lookup("#learnCard").getUserData();
        learnBtn.setOnAction(e -> switchTo(new LearnView(stage)));

        Button reviewBtn = (Button) scene.lookup("#reviewCard").getUserData();
        reviewBtn.setOnAction(e -> controller.handleReviewClick());

        Label bookNav = (Label) scene.lookup("#bookNav");
        bookNav.setOnMouseClicked(e -> controller.handleBookClick());

        Label statNav = (Label) scene.lookup("#statNav");
        statNav.setOnMouseClicked(e -> controller.handleStatClick());
    }

    private StackPane createFunctionCard(String subTitle, String title, String number, Color themeColor) {
        StackPane card = new StackPane();
        card.setBackground(new Background(new BackgroundFill(
                Color.rgb(255, 255, 255, 0.7),
                new CornerRadii(25),
                Insets.EMPTY
        )));
        card.setPrefSize(350, 250);
        card.setCursor(javafx.scene.Cursor.HAND);

        VBox content = new VBox(15);
        content.setAlignment(Pos.CENTER);
        Label subTitleLabel = new Label(subTitle);
        subTitleLabel.setFont(Font.font(18));
        subTitleLabel.setTextFill(Color.rgb(100, 100, 100));
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 48));
        Label numLabel = new Label("已学习：" + number + " 词");
        numLabel.setFont(Font.font(24));
        numLabel.setTextFill(themeColor);
        content.getChildren().addAll(subTitleLabel, titleLabel, numLabel);

        Button clickBtn = new Button();
        clickBtn.setPrefSize(350, 250);
        clickBtn.setOpacity(0);
        clickBtn.setCursor(javafx.scene.Cursor.HAND);

        card.getChildren().addAll(content, clickBtn);
        card.setUserData(clickBtn);

        return card;
    }
}
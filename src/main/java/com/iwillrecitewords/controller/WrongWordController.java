package com.iwillrecitewords.controller;

import com.iwillrecitewords.model.WrongWord;
import com.iwillrecitewords.service.WrongWordService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class WrongWordController {
    @FXML
    private TableView<WrongWord> wrongWordTable;
    @FXML
    private TableColumn<WrongWord, Long> idColumn;
    @FXML
    private TableColumn<WrongWord, String> wordColumn;
    @FXML
    private TableColumn<WrongWord, String> meaningColumn;
    @FXML
    private TableColumn<WrongWord, String> timeColumn;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button clearBtn;

    private final WrongWordService service = WrongWordService.getInstance();
    private final ObservableList<WrongWord> wrongWordList = FXCollections.observableArrayList();

    // 界面初始化
    @FXML
    public void initialize() {
        // 绑定表格列
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));
        meaningColumn.setCellValueFactory(new PropertyValueFactory<>("meaning"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("createTime"));

        // 加载当前用户错词（默认用户ID=1）
        loadWrongWords(1L);

        // 删除按钮事件
        deleteBtn.setOnAction(e -> {
            WrongWord selected = wrongWordTable.getSelectionModel().getSelectedItem();
            if (selected != null) {
                service.deleteWrongWord(selected.getId());
                loadWrongWords(1L);
                showAlert("成功", "删除错词成功");
            } else {
                showAlert("提示", "请先选择要删除的错词");
            }
        });

        // 清空按钮事件
        clearBtn.setOnAction(e -> {
            service.clearWrongWords(1L);
            loadWrongWords(1L);
            showAlert("成功", "清空错词本成功");
        });
    }

    // 加载错词列表
    private void loadWrongWords(Long userId) {
        wrongWordList.clear();
        wrongWordList.addAll(service.getWrongWordsByUserId(userId));
        wrongWordTable.setItems(wrongWordList);
    }

    // 弹窗提示
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // 供外部调用添加错词
    public void addWrongWord(WrongWord word) {
        service.addWrongWord(word);
        loadWrongWords(1L);
    }
}
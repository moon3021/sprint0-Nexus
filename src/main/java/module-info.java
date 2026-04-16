module com.iwillrecitewords {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.iwillrecitewords to javafx.graphics;
    // 开放页面包给FXML（如果后续用FXML）
    opens com.iwillrecitewords.view to javafx.fxml;
    // 导出包
    exports com.iwillrecitewords;
    exports com.iwillrecitewords.view;
    exports com.iwillrecitewords.service;
    exports com.iwillrecitewords.model;
}
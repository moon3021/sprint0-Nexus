module com.iwillrecitewords {
    requires javafx.controls;
    requires javafx.fxml;
    opens com.iwillrecitewords to javafx.graphics;
    opens com.iwillrecitewords.view to javafx.fxml;
    exports com.iwillrecitewords;
    exports com.iwillrecitewords.view;
    exports com.iwillrecitewords.service;
    exports com.iwillrecitewords.model;

    opens com.iwillrecitewords to org.junit.jupiter;
}
module com.iwillrecitewords {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.iwillrecitewords to javafx.fxml;
    opens com.iwillrecitewords.view to javafx.fxml;
    exports com.iwillrecitewords;
    exports com.iwillrecitewords.view;
    exports com.iwillrecitewords.controller;
    exports com.iwillrecitewords.model;
    exports com.iwillrecitewords.service;
    exports com.iwillrecitewords.repository;
    exports com.iwillrecitewords.util;
    exports com.iwillrecitewords.strategy;
}
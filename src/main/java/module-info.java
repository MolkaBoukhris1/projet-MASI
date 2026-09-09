module com.iit.masi.drawingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;

    opens com.iit.masi to javafx.fxml;
    opens com.iit.masi.controller to javafx.fxml;
    opens com.iit.masi.model to com.google.gson;
    
    exports com.iit.masi;
    exports com.iit.masi.controller;
    exports com.iit.masi.model;
    exports com.iit.masi.factory;
    exports com.iit.masi.command;
    exports com.iit.masi.strategy;
    exports com.iit.masi.observer;
    exports com.iit.masi.service;
    exports com.iit.masi.repository;
}

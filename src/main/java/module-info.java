module com.example.perfumeshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires java.xml.bind;

    opens com.example.perfumeshop to javafx.fxml;
    exports com.example.perfumeshop;
    exports com.example.perfumeshop.view_model;
    opens com.example.perfumeshop.view_model to javafx.fxml;
    exports com.example.perfumeshop.view_model.commands;
    opens com.example.perfumeshop.view_model.commands to javafx.fxml;
    exports com.example.perfumeshop.view;
    opens com.example.perfumeshop.view to javafx.fxml;
    exports com.example.perfumeshop.view_model.commands.person;
    opens com.example.perfumeshop.view_model.commands.person to javafx.fxml;
    exports com.example.perfumeshop.view_model.commands.product;
    opens com.example.perfumeshop.view_model.commands.product to javafx.fxml;

    exports com.example.perfumeshop.model;
    opens com.example.perfumeshop.view_model.commands.file to java.xml.bind;
//    opens com.example.perfumeshop.model to java.xml.bind; // Add this line
    opens com.example.perfumeshop.model;
}

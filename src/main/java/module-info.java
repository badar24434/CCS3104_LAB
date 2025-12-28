module com.csc.lab.ccs3104_lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.rmi;
    requires java.sql;

    opens com.csc3402.lab.ccs3104_lab to javafx.fxml;
    exports com.csc3402.lab.ccs3104_lab;
    exports com.csc3402.lab.ccs3104_lab.LAB1;
    opens com.csc3402.lab.ccs3104_lab.LAB1 to javafx.fxml;
    exports com.csc3402.lab.ccs3104_lab.LAB2;
    exports com.csc3402.lab.ccs3104_lab.LAB3;
    opens com.csc3402.lab.ccs3104_lab.LAB3 to javafx.fxml;
    opens com.csc3402.lab.ccs3104_lab.LAB2 to javafx.fxml;
    exports com.csc3402.lab.ccs3104_lab.LAB4;
    opens com.csc3402.lab.ccs3104_lab.LAB4 to javafx.fxml;
    exports com.csc3402.lab.ccs3104_lab.LAB5;
    opens com.csc3402.lab.ccs3104_lab.LAB5 to javafx.fxml;
    exports com.csc3402.lab.ccs3104_lab.LAB6;
    opens com.csc3402.lab.ccs3104_lab.LAB6 to javafx.fxml;
    exports com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE;
    opens com.csc3402.lab.ccs3104_lab.PROJECT_EXAMPLE to javafx.fxml;
}
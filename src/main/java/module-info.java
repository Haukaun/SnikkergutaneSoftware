module com.snikkergutane {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.snikkergutane to javafx.fxml;
    exports com.snikkergutane;
    exports com.snikkergutane.project;
    exports com.snikkergutane.project.task;
}
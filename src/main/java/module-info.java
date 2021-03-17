module org.example {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.snikkergutane to javafx.fxml;
    exports com.snikkergutane;
}
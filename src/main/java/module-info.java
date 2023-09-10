module com.example.cascadia {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cascadia to javafx.fxml;
    opens com.example.cascadia.scoring to javafx.fxml, javafx.base;
    exports com.example.cascadia;
}
module com.example.indivproj2_v1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires jbcrypt;


    opens com.example.indivproj2_v1 to javafx.fxml, com.google.gson;

    exports com.example.indivproj2_v1;
}
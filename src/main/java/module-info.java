module com.example.infosystemcollege {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires org.apache.commons.codec;


    opens personalPass to javafx.fxml;
    exports personalPass;
}
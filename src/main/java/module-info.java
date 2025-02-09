module org.example.javafx_fees {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.javafx_fees to javafx.fxml;
    exports org.example.javafx_fees;
    exports org.example.javafx_fees.model;
    opens org.example.javafx_fees.model to javafx.fxml;
    exports org.example.javafx_fees.utils;
    opens org.example.javafx_fees.utils to javafx.fxml;
}
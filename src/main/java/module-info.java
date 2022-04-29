module com.kkulpa.checkers.checkers {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.kkulpa.checkers.checkers to javafx.fxml;
    exports com.kkulpa.checkers.checkers;
}
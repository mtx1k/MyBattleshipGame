module de.shekhovtsov.mybattleshipgame {
    requires javafx.controls;
    requires javafx.fxml;


    opens de.shekhovtsov.mybattleshipgame to javafx.fxml;
    exports de.shekhovtsov.mybattleshipgame;
}
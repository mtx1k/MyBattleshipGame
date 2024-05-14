package de.shekhovtsov.mybattleshipgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class GameController {
    @FXML
    public Label topLabel;
    @FXML
    public Button startButton;
    @FXML
    public Button randomButton;
    @FXML
    public VBox computerBox;
    @FXML
    public VBox userBox;

    @FXML
    public void start(ActionEvent actionEvent) {
        randomButton.setDisable(true);
        startButton.setDisable(true);
    }
    @FXML
    public void random(ActionEvent actionEvent) {
        Field userField = new Field();
        userField.fillFieldRandomly();
        Field computerField = new Field();
        computerField.fillFieldRandomly();

        FieldButtons userFieldButtons = new FieldButtons(userBox, userField.getShips(), true, topLabel);
        FieldButtons computerFieldButtons = new FieldButtons(computerBox, computerField.getShips(), false, topLabel);
        GameAction.setUserField(userFieldButtons);
        GameAction.setComputerField(computerFieldButtons);
    }
}
package de.shekhovtsov.mybattleshipgame;

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

    private FieldButtons userFieldButtons;
    private FieldButtons computerFieldButtons;

    @FXML
    public void start() {
        randomButton.setDisable(true);
        startButton.setDisable(true);
        GameAction gameAction = new GameAction(userFieldButtons, computerFieldButtons);
        gameAction.setButtonsHandler(computerFieldButtons);
    }
    @FXML
    public void random() {
        userFieldButtons = new FieldButtons(userBox, new Field(), true);
    }
    @FXML
    private void initialize() {
        userFieldButtons = new FieldButtons(userBox, new Field(), true);
        computerFieldButtons = new FieldButtons(computerBox, new Field(), false);

    }
}
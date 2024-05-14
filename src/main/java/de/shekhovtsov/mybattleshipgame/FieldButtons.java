package de.shekhovtsov.mybattleshipgame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FieldButtons {
    @FXML
    private Label topLabel;
    private SimpleCell[][] field;
    private Button[][] buttons = new Button[10][10];
    private VBox vbox;
    private ArrayList<SimpleCell> hittedShips = new ArrayList<>();
    private boolean isUser;

    public FieldButtons(VBox vbox, Field field, boolean isUser, Label label) {
        this.vbox = vbox;
        this.field = field.getField();
        this.isUser = isUser;
        this.topLabel = label;
        createField();
    }



    private void createField() {
        vbox.getChildren().clear();
        for (int i = 0; i < 10; i++) {
            HBox hBox = new HBox();

            for (int j = 0; j < 10; j++) {
                Button button = new Button();
                button.setMinHeight(30);
                button.setMinWidth(30);
                button.getProperties().put("x", i);
                button.getProperties().put("y", j);
                hBox.getChildren().add(button);
                buttons[i][j] = button;
                if (isUser) {
                    button.setDisable(true);
                    if(isShip(i, j)) {
                        button.setStyle("-fx-background-color: #8480FE");
                    }
                }
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        GameAction action = new GameAction();
                        if (action.userAction(event)) {
                            if(isEnd(getHittedShips())) {
                                blockAllButtons();
                                topLabel.setText("You WIN!!!!!!!!!!!");
                            }
                            return;
                        }
                        while (action.computerAction()) {
                            if (isEnd(GameAction.getUserField().getHittedShips())) {
                                blockAllButtons();
                                topLabel.setText("You are LOOSER!!! HA-HA");
                            }
                        };
                    }
                });
            }
            vbox.getChildren().add(hBox);
        }
    }

    private void blockAllButtons() {
        buttons = GameAction.getComputerField().getButtons();
        for (Button[] button : buttons) {
            for (Button value : button) {
                value.setDisable(true);
            }
        }
    }

    private boolean isShip(int x, int y) {
        return field[x][y].getId() > 0;
    }

    private boolean isEnd(ArrayList<SimpleCell> hitted) {
        return hitted.size() == 20;
    }

    public SimpleCell[][] getShips() {
        return field;
    }

    public Button[][] getButtons() {
        return buttons;
    }

    public ArrayList<SimpleCell> getHittedShips() {
        return hittedShips;
    }

}

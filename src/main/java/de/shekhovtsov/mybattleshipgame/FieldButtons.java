package de.shekhovtsov.mybattleshipgame;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FieldButtons {

    private SimpleCell[][] field;
    private VBox vbox;
   // private ArrayList<SimpleCell> hitShips = new ArrayList<>();
    private boolean isUser;

    public FieldButtons(VBox vbox, Field field, boolean isUser) {
        this.vbox = vbox;
        this.field = field.getField();
        this.isUser = isUser;
        createField();
    }

    public VBox getVbox() {
        return vbox;
    }

    public SimpleCell[][] getField() {
        return field;
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
                //button.setOnAction(new GameAction());
                hBox.getChildren().add(button);
                if (isUser) {
                    button.setDisable(true);
                    if(isShip(i, j)) {
                        button.setStyle("-fx-background-color: #8480FE");
                    }
                }
//                button.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//                        GameAction action = new GameAction();
//                        if (action.userAction(event)) {
//                            if(isEnd(getHittedShips())) {
//                                blockAllButtons();
//                                topLabel.setText("You WIN!!!!!!!!!!!");
//                            }
//                            return;
//                        }
//                        while (action.computerAction()) {
//                            if (isEnd(GameAction.getUserField().getHittedShips())) {
//                                blockAllButtons();
//                                topLabel.setText("You are LOOSER!!! HA-HA");
//                            }
//                        };
//                    }
//                });
            }
            vbox.getChildren().add(hBox);
        }
    }

    public void blockAllButtons() {
        for (int i = 0; i < vbox.getChildren().size(); i++) {
            HBox hBox = (HBox) vbox.getChildren().get(i);
            for (int j = 0; j < hBox.getChildren().size(); j++) {
                Button button = (Button) hBox.getChildren().get(j);
                button.setDisable(true);
            }
        }
    }

    private boolean isShip(int x, int y) {
        return field[x][y].getId() > 0;
    }
//
//    private boolean isEnd(ArrayList<SimpleCell> hit) {
//        return hit.size() == 20;
//    }

//    public ArrayList<SimpleCell> getHitShips() {
//        return hitShips;
//    }
}

package de.shekhovtsov.mybattleshipgame;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Random;

public class GameAction implements EventHandler<ActionEvent> {

    private final ArrayList<SimpleCell> hitUserShips = new ArrayList<>();
    private final ArrayList<SimpleCell> hitCompShips = new ArrayList<>();
    private final ArrayList<SimpleCell> computerHits = new ArrayList<>();
    private final FieldButtons computerFieldButtons;
    private final FieldButtons userField;

    public GameAction(FieldButtons userField, FieldButtons computerFieldButtons) {
        this.userField = userField;
        this.computerFieldButtons = computerFieldButtons;
        //setButtonsHandler();
    }

    public boolean userAction(ActionEvent event) {
        SimpleCell[][] field = computerFieldButtons.getField();

        Button button = (Button) event.getSource();
        int x = (int) button.getProperties().get("x");
        int y = (int) button.getProperties().get("y");
        button.setDisable(true);

        if (field[x][y].getId() == 0) {
            button.setStyle("-fx-background-color: #81D8D0");
        }
        if (field[x][y].getId() > 0) {
            button.setStyle("-fx-background-color: #826D8C");

            hitUserShips.add(field[x][y]);
            ArrayList<SimpleCell> hitShip = isDead(field[x][y], hitUserShips);
            if (hitShip != null) {
                ArrayList<SimpleCell> waterAroundDeadShip = Field.getCoordinatesAroundShip(hitShip, field);
                for (SimpleCell simpleCell : waterAroundDeadShip) {
                    HBox hBox = (HBox) computerFieldButtons.getVbox().getChildren().get(simpleCell.getX());
                    Button fieldButton = (Button) hBox.getChildren().get(simpleCell.getY());
                    fieldButton.setStyle("-fx-background-color: #81D8D0");
                    fieldButton.setDisable(true);
                }
            }
            return true;
        }
        return false;
    }

    public boolean computerAction() {
        SimpleCell[][] field = userField.getField();
        Random random = new Random();
        int x, y;
        while (true) {
            x = random.nextInt(0, 10);
            y = random.nextInt(0, 10);

            if (!isExist(x, y)) {
                computerHits.add(new SimpleCell(x, y));
                break;
            }
        }
        if (field[x][y].getId() == 0) {
            getButtonFromField(userField, x, y).setStyle("-fx-background-color: #81D8D0");
        }
        if (field[x][y].getId() > 0) {
            getButtonFromField(userField, x, y).setStyle("-fx-background-color: #826D8C");
            hitCompShips.add(field[x][y]);
            ArrayList<SimpleCell> hitShip = isDead(field[x][y], hitCompShips);
            if (hitShip != null) {
                ArrayList<SimpleCell> waterAroundDeadShip = Field.getCoordinatesAroundShip(hitShip, field);
                computerHits.addAll(waterAroundDeadShip);
                for (SimpleCell simpleCell : waterAroundDeadShip) {
                    Button button = getButtonFromField(userField, simpleCell.getX(), simpleCell.getY());
                    button.setStyle("-fx-background-color: #81D8D0");
                    button.setDisable(true);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public void handle(ActionEvent event) {
        if (userAction(event)) {
            if (isEnd(hitUserShips)) {
                computerFieldButtons.blockAllButtons();
               // topLabel.setText("You WIN!!!!!!!!!!!");
            }
            return;
        }
        while (computerAction()) {
            if (isEnd(hitCompShips)) {
                computerFieldButtons.blockAllButtons();
                break;
               // topLabel.setText("You are LOOSER!!! HA-HA");
            }
        }
    }

    private ArrayList<SimpleCell> isDead(SimpleCell current, ArrayList<SimpleCell> hitShips) {
        ArrayList<SimpleCell> hitShip = new ArrayList<>();
        int length = current.getId() / 10;
        for (SimpleCell simpleCell : hitShips) {
            if (simpleCell.getId() == current.getId()) {
                hitShip.add(simpleCell);
            }
        }
        if (hitShip.size() == length) {
            return hitShip;
        }
        return null;
    }

    private boolean isExist(int x, int y) {
        for (SimpleCell computerHit : computerHits) {
            if (computerHit.getX() == x && computerHit.getY() == y) {
                return true;
            }
        }
        return false;
    }

    public void setButtonsHandler(FieldButtons fieldButtons) {
        VBox vBox = fieldButtons.getVbox();
        for (int i = 0; i < vBox.getChildren().size(); i++) {
            HBox hBox = (HBox) vBox.getChildren().get(i);
            for (int j = 0; j < hBox.getChildren().size(); j++) {
                Button button = (Button) hBox.getChildren().get(j);
                button.setOnAction(this);
            }
            System.out.println();
        }
    }

    private boolean isEnd(ArrayList<SimpleCell> hit) {
        return hit.size() == 20;
    }

    private Button getButtonFromField(FieldButtons fieldButtons, int x, int y) {
        HBox hBox = (HBox) fieldButtons.getVbox().getChildren().get(x);
        return (Button) hBox.getChildren().get(y);
    }
}
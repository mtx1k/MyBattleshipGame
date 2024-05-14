package de.shekhovtsov.mybattleshipgame;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.Random;

public class GameAction {
    private SimpleCell[][] field;
    private Button[][] buttons;
    private ArrayList<SimpleCell> hittedShips;
    private static ArrayList<SimpleCell> computerHits = new ArrayList<>();
    private static FieldButtons computerField;
    private static FieldButtons userField;
    private  static int counter = 0;

    public static void setUserField(FieldButtons userField) {
        GameAction.userField = userField;
    }

    public static void setComputerField(FieldButtons computerField) {
        GameAction.computerField = computerField;
    }

    public static FieldButtons getComputerField() {
        return computerField;
    }

    public static FieldButtons getUserField() {
        return userField;
    }

    private void initialize(boolean isUser) {
        if (isUser) {
            this.field = computerField.getShips();
            this.buttons = computerField.getButtons();
            this.hittedShips = computerField.getHittedShips();
        } else {
            this.field = userField.getShips();
            this.buttons = userField.getButtons();
            this.hittedShips = userField.getHittedShips();
        }
    }

    public boolean userAction(ActionEvent event) {
        initialize(true);

        Button button = (Button) event.getSource();
        int x = (int) button.getProperties().get("x");
        int y = (int) button.getProperties().get("y");
        button.setDisable(true);

        if (field[x][y].getId() == 0) {
            button.setStyle("-fx-background-color: #81D8D0");
        }
        if (field[x][y].getId() > 0) {

            button.setStyle("-fx-background-color: #826D8C");

            hittedShips.add(field[x][y]);
            ArrayList<SimpleCell> hittedShip = isDead(field[x][y]);
            if (hittedShip != null) {
                ArrayList<SimpleCell> waterAroundDeadShip = Field.getCoordinatesAroundShip(hittedShip, field);
                for (SimpleCell simpleCell : waterAroundDeadShip) {
                    buttons[simpleCell.getX()][simpleCell.getY()].setStyle("-fx-background-color: #81D8D0");
                    buttons[simpleCell.getX()][simpleCell.getY()].setDisable(true);
                }
            }
            return true;
        }
        return false;
    }

    public boolean computerAction() {
        initialize(false);
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
            buttons[x][y].setStyle("-fx-background-color: #81D8D0");
        }
        if (field[x][y].getId() > 0) {
            buttons[x][y].setStyle("-fx-background-color: #826D8C");
            hittedShips.add(field[x][y]);
            ArrayList<SimpleCell> hittedShip = isDead(field[x][y]);
            if (hittedShip != null) {
                ArrayList<SimpleCell> waterAroundDeadShip = Field.getCoordinatesAroundShip(hittedShip, field);
                computerHits.addAll(waterAroundDeadShip);
                for (SimpleCell simpleCell : waterAroundDeadShip) {
                    buttons[simpleCell.getX()][simpleCell.getY()].setStyle("-fx-background-color: #81D8D0");
                    buttons[simpleCell.getX()][simpleCell.getY()].setDisable(true);
                }
            }
            return true;
        }
        return false;
    }

    private ArrayList<SimpleCell> isDead(SimpleCell current) {
        ArrayList<SimpleCell> hittedShip = new ArrayList<>();
        int length = current.getId() / 10;
        for (SimpleCell simpleCell : hittedShips) {
            if (simpleCell.getId() == current.getId()) {
                hittedShip.add(simpleCell);
            }
        }
        if (hittedShip.size() == length) {
            return hittedShip;
        }
        return null;
    }

    private boolean isExist(int x, int y) {
        for (int i = 0; i < computerHits.size(); i++) {
            if (computerHits.get(i).getX() == x && computerHits.get(i).getY() == y) {
                return true;
            }
        }
        return false;
    }
}
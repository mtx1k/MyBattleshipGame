package de.shekhovtsov.mybattleshipgame;

import java.util.ArrayList;

public class Field {
    private final int FIELD_SIZE = 10;
    private SimpleCell[][] field;

    public Field() {

    }

    public int getFIELD_SIZE() {
        return FIELD_SIZE;
    }

    public SimpleCell[][] getField() {
        if (field == null) {
            fillFieldRandomly();
        }
        return field;
    }

    private void firstFill() {
        field = new SimpleCell[FIELD_SIZE][FIELD_SIZE];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = new SimpleCell(9, i, j);
            }
        }
    }

    public void fillFieldRandomly() {
        firstFill();
        int maxCellCount= 4;
        int minCellCount = 1;
        for (int i = maxCellCount; i > 0; i--) {
            for (int j = minCellCount; j <= maxCellCount - i + 1; j++) {
                Ship ship = new Ship(this);
                ArrayList<SimpleCell> coordinates = ship.createShip(i, j);
                addShipsOnField(coordinates);
                fillAroundShip(coordinates);
            }
        }
        finishFill();
    }

    private void finishFill() {
        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (field[i][j].id() == 9) {
                    field[i][j] = new SimpleCell(0, field[i][j].x(), field[i][j].y());
                    //field[i][j].setId(0);
                }
            }
        }
    }

    private void addShipsOnField(ArrayList<SimpleCell> finishedShip) {
        for (SimpleCell simpleCell : finishedShip) {
            int x = simpleCell.x();
            int y = simpleCell.y();
            field[x][y] = simpleCell;
        }
    }

    private void fillAroundShip(ArrayList<SimpleCell> shipCoordinates) {
        ArrayList<SimpleCell> coordinatesAroundShip = getCoordinatesAroundShip(shipCoordinates, this.getField());
        for (SimpleCell ship : coordinatesAroundShip) {
            field[ship.x()][ship.y()] = new SimpleCell(0, ship.x(), ship.y());
            //field[ship.getX()][ship.getY()].setId(0);
        }
    }

    public static ArrayList<SimpleCell> getCoordinatesAroundShip(ArrayList<SimpleCell> shipCoordinates, SimpleCell[][] currentShips) {
        ArrayList<SimpleCell> coordinates = new ArrayList<>();
        for (SimpleCell simpleCell: shipCoordinates) {
            int x = simpleCell.x();
            int y = simpleCell.y();
            int xStart = x - 1;
            int xFinish = x + 1;
            int yStart = y - 1;
            int yFinish = y + 1;
            if (x - 1 < 0) {
                xStart = x;
            } else if (x + 1 > 9) {
                xFinish = x;
            }
            if (y - 1 < 0) {
                yStart = y;
            } else if (y + 1 > 9) {
                yFinish = y;
            }
            for (int i = xStart; i <= xFinish; i++) {
                for (int j = yStart; j <= yFinish; j++) {
                    if (currentShips[i][j].id() == 9 || currentShips[i][j].id() == 0) {
                        coordinates.add(new SimpleCell(0, i, j));
                    }
                }
            }
        }
        return coordinates;
    }
}
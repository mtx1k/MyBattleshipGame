package de.shekhovtsov.mybattleshipgame;

import java.util.ArrayList;
import java.util.Random;

public class Ship {
    private final int FIELD_SIZE;
    private final SimpleCell[][] field;

    public Ship(Field field) {
        FIELD_SIZE = field.getFIELD_SIZE();
        this.field = field.getShips();
    }

    public ArrayList<SimpleCell> createShip(int length, int id) {
        Random random = new Random();
        ArrayList<SimpleCell> coordinates;
        while (true) {
            int x = random.nextInt(0, FIELD_SIZE);
            int y = random.nextInt(0, FIELD_SIZE);
            ArrayList<SimpleCell> possibleCoordinates = getPossibleCoordinates(x, y, length, id);
            if (possibleCoordinates != null) {
                coordinates = getFinalShipCoordinates(possibleCoordinates, length);
                break;
            }
        }
        return coordinates;
    }

    private ArrayList<SimpleCell> getPossibleCoordinates(int x, int y, int length, int id) {
        Random random = new Random();
        boolean isHorizontal = random.nextBoolean();
        ArrayList<SimpleCell> possibleCoordinates = new ArrayList<>();
        int start, finish;
        int newID = length * 10 + id;
        if (isHorizontal) {
            start = y - length + 1;
            finish = y + length - 1;
            for (int i = start; i <= finish; i++) {
                if (i < 0 || i >= FIELD_SIZE) {
                    continue;
                }
                if (field[x][i].getId() == 9) {
                    possibleCoordinates.add(new SimpleCell(newID, x, i));
                }
            }
        }
        if (possibleCoordinates.isEmpty() || possibleCoordinates.size() < length) {
            possibleCoordinates = new ArrayList<>();
            start = x - length + 1;
            finish = x + length - 1;
            for (int i = start; i <= finish; i++) {
                if (i < 0 || i >= FIELD_SIZE) {
                    continue;
                }
                if (field[i][y].getId() == 9) {
                    possibleCoordinates.add(new SimpleCell(newID, i, y));
                }
            }
        }
        if (possibleCoordinates.size() < length) {
            return null;
        }
        return possibleCoordinates;
    }

    private ArrayList<SimpleCell> getFinalShipCoordinates(ArrayList<SimpleCell> possibleCoordinates, int length) {
        ArrayList<SimpleCell> coordinates = new ArrayList<>();
        for (int i = 0; i <= possibleCoordinates.size() - length; i++) {
            Random random = new Random();
            boolean isNext = random.nextBoolean();
            if (possibleCoordinates.size() - i == length || !isNext) {
                for (int j = 0; j < length; j++) {
                    coordinates.add(possibleCoordinates.get(j));
                }
                break;
            }
        }
        return coordinates;
    }
}

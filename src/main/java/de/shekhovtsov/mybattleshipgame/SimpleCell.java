package de.shekhovtsov.mybattleshipgame;

public class SimpleCell {
    private int id;
    private final int x;
    private final int y;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SimpleCell(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public SimpleCell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

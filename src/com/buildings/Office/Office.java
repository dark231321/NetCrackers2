package com.buildings.Office;
import org.jetbrains.annotations.NotNull;

public class Office {
    static private final Office DEFAULT_OFFICE = new  Office(1, 250);
    private double square;
    private int countRooms;

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        this.square = square;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        this.countRooms = countRooms;
    }

    static public Office of(){
        return DEFAULT_OFFICE;
    }

    @NotNull
    static public Office of(double square) {
        return new Office(1, square);
    }

    @NotNull
    static public Office of(int countRooms, double square){
        return new Office(countRooms, square);
    }

    private Office(int countRooms, double square){
        this.square = square;
        this.countRooms = countRooms;
    }
}

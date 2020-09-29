package com.buildings.build;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Flat {
    static private final Flat DEFAULT_FLAT = new  Flat(2, 50);
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

    static public Flat of(){
        return DEFAULT_FLAT;
    }

    @NotNull
    static public Flat of(double square) {
        return new Flat(2, square);
    }

    @NotNull
    static public Flat of(int countRooms, double square){
        return new Flat(countRooms, square);
    }

    private Flat(int countRooms, double square){
        this.square = square;
        this.countRooms = countRooms;
    }
}

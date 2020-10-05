package com.buildings.build;

import com.buildings.Exceptions.InvalidRoomsCountException;
import com.buildings.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;

public class Flat implements Space {
    static private final Flat DEFAULT_FLAT = new  Flat(2, 50);
    private double square;
    private int countRooms;

    public double getSquare() {
        return square;
    }

    public void setSquare(double square) {
        if(square <= 0)
            throw new InvalidSpaceAreaException();
        this.square = square;
    }

    public int getCountRooms() {
        return countRooms;
    }

    public void setCountRooms(int countRooms) {
        if(countRooms <= 0)
            throw new InvalidRoomsCountException();
        this.countRooms = countRooms;
    }

    static public Flat of(){
        return DEFAULT_FLAT;
    }

    @NotNull
    static public Flat of(double square) {
        if(square <= 0)
            throw new InvalidSpaceAreaException();
        return new Flat(2, square);
    }

    @NotNull
    static public Flat of(int countRooms, double square){
        if(square <= 0)
            throw new InvalidSpaceAreaException();
        if(countRooms <= 0)
            throw new InvalidRoomsCountException();
        return new Flat(countRooms, square);
    }

    private Flat(int countRooms, double square){
        this.square = square;
        this.countRooms = countRooms;
    }
}

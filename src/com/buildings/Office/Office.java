package com.buildings.Office;
import com.buildings.Exceptions.InvalidRoomsCountException;
import com.buildings.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;

public class Office implements Space {
    static private final Office DEFAULT_OFFICE = new  Office(1, 250);
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

    static public Office of(){
        return DEFAULT_OFFICE;
    }

    @NotNull
    static public Office of(double square) {
        if(square <= 0)
            throw new InvalidSpaceAreaException();
        return new Office(1, square);
    }

    @NotNull
    static public Office of(int countRooms, double square){
        if(square <= 0)
            throw new InvalidSpaceAreaException();
        if(countRooms <= 0)
            throw new InvalidRoomsCountException();
        return new Office(countRooms, square);
    }

    private Office(int countRooms, double square){
        this.square = square;
        this.countRooms = countRooms;
    }
}

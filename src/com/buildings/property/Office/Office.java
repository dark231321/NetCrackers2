package com.buildings.property.Office;
import com.buildings.property.Exceptions.InvalidRoomsCountException;
import com.buildings.property.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Space;

public class Office implements Space {

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

    public Office(){
        this.square = 50.0;
        this.countRooms = 2;
    }

    public Office(double square){
        this.square = square;
        this.countRooms = 2;
    }

    public Office(int countRooms, double square){
        this.square = square;
        this.countRooms = countRooms;
    }

    @Override
    public String toString() {
        return "Office{" +
                "Square=" + square +
                ", countRooms=" + countRooms +
                '}';
    }
}

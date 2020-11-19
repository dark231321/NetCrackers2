package com.buildings.property.Dwelling;

import com.buildings.property.Exceptions.InvalidRoomsCountException;
import com.buildings.property.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;

public class Flat implements Space {
    private double square;
    private int countRooms;

    @Override
    public double getSquare() {
        return square;
    }

    @Override
    public void setSquare(double square) {
        if(square <= 0)
            throw new InvalidSpaceAreaException();
        this.square = square;
    }

    @Override
    public int getCountRooms() {
        return countRooms;
    }

    @Override
    public void setCountRooms(int countRooms) {
        if(countRooms <= 0)
            throw new InvalidRoomsCountException();
        this.countRooms = countRooms;
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        return super.clone();
    }

    public Flat(){
        this.square = 50.0;
        this.countRooms = 2;
    }

    public Flat(double square){
        this.square = square;
        this.countRooms = 2;
    }

    public Flat(int countRooms, double square){
        this.square = square;
        this.countRooms = countRooms;
    }

    @Override
    public String toString() {
        return "Flat{" +
                "square=" + square +
                ", countRooms=" + countRooms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flat flat = (Flat) o;
        return Double.compare(flat.square, square) == 0 &&
                countRooms == flat.countRooms;
    }

    @Override
    public int hashCode() {
        return countRooms ^ (int) ((long) square);
    }

    @Override
    public int compareTo(@NotNull Space space) {
        return Double.compare(space.getSquare(), this.square);
    }
}

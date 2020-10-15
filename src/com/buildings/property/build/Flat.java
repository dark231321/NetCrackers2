package com.buildings.property.build;

import com.buildings.property.Exceptions.InvalidRoomsCountException;
import com.buildings.property.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Space;

import java.util.Objects;

public class Flat implements Space {
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

    @Override
    public Object clone() {
        return new Flat(this.countRooms, this.square);
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
}

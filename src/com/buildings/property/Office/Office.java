package com.buildings.property.Office;
import com.buildings.property.util.Exceptions.InvalidRoomsCountException;
import com.buildings.property.util.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Space;

public class Office implements Space {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Office office = (Office) o;
        return Double.compare(office.square, square) == 0 &&
                countRooms == office.countRooms;
    }

    @Override
    public int hashCode() {
        return countRooms ^ (int)((long) square >> 32);
    }

    @Override
    public int compareTo(Space space) {
        return Double.compare(space.getSquare(), this.square);
    }
}


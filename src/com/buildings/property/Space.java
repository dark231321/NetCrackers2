package com.buildings.property;

import java.io.Serializable;
import java.util.Comparator;

public interface Space extends Cloneable, Serializable, Comparable<Space> {

    double getSquare();

    void setSquare(double square);

    int getCountRooms();

    void setCountRooms(int countRooms);

    Object clone() throws CloneNotSupportedException;
}


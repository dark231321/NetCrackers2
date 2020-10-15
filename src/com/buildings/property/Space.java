package com.buildings.property;

import com.buildings.Container.Alghorithms.MyCloneable;

public interface Space extends MyCloneable {

    double getSquare();

    void setSquare(double square);

    int getCountRooms();

    void setCountRooms(int countRooms);

    Object clone();
}

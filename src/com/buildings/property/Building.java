package com.buildings.property;

import com.buildings.Container.AbstractArray;

import java.io.Serializable;
import java.util.Iterator;

public interface Building extends Cloneable, Iterable<Floor>, Serializable {

    Iterator<Floor> iterator();

    Space getSpace(int numberOffice);

    void removeSpace(int numberFlat);

    void setSpace(int numberOffice, Space space);

    Floor set(int index, Floor OfficeFloor);

    Floor get(int index);

    int size();

    int getCountRooms();

    int getCountSpace();

    double getBestSpace();

    double getSquare();

    AbstractArray<Space> sortedSpace();

    AbstractArray<Floor> getSpaceList();

    Object clone() throws CloneNotSupportedException;
}

package com.buildings.property;

import com.buildings.Container.AbstractArray;
import com.buildings.Container.Alghorithms.MyCloneable;

public interface Building extends MyCloneable {

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

    AbstractArray<? extends Floor> getSpaceList();

}

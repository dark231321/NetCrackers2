package com.buildings.property;

import com.buildings.Container.AbstractArray;
import com.buildings.Container.MyListIterator;


public interface Floor {
    MyListIterator<Space> MyListIterator(int index);

    int size();

    AbstractArray<? extends Space> getSpaceList();

    Space set(int index, Space space);

    double getSquare();

    Space get(int index);

    Space setRooms(int index, int newCountRooms);

    double getBestSpace();

    boolean Remove(int index);

    int getCountRooms();

}

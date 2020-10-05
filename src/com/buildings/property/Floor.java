package com.buildings.property;

import com.buildings.Container.MyLinkedList;
import com.buildings.Container.MyListIterator;


public interface Floor {
    MyListIterator<? extends Space> MyListIterator(int index);

    int size();

    double getSquare();

    Space get(int index);

    Space setRooms(int index, int newCountRooms);

    double getBestSpace();

    boolean Remove(int index);

    int getCountRooms();

}

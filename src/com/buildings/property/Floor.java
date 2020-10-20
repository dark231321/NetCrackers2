package com.buildings.property;

import com.buildings.Container.AbstractArray;
import com.buildings.Container.ListIterator;

import java.io.Serializable;
import java.util.Iterator;


public interface Floor extends Serializable, Cloneable, Iterable<Space>, Comparable<Floor> {

    Iterator<Space> iterator();

    ListIterator<Space> MyListIterator(int index);

    int size();

    AbstractArray<Space> getSpaceList();

    Space set(int index, Space space);

    double getSquare();

    Space get(int index);

    Space setRooms(int index, int newCountRooms);

    double getBestSpace();

    boolean Remove(int index);

    int getCountRooms();

    Object clone() throws CloneNotSupportedException;


}

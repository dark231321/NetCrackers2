package com.buildings.property;


public interface Building {

    Space getSpace(int numberOffice);

    void removeSpace(int numberFlat);

    Floor get(int index);

    int size();

    int getCountRooms();

    int getCountSpace();

    double getBestSpace();

    double getSquare();

}

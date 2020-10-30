package com.buildings.property.Factorys;

import com.buildings.Container.AbstractArray;
import com.buildings.Container.MyLinkedList;
import com.buildings.property.Building;
import com.buildings.property.Floor;
import com.buildings.property.Office.Office;
import com.buildings.property.Office.OfficeBuilding;
import com.buildings.property.Office.OfficeFloor;
import com.buildings.property.Space;

public class OfficeFactory implements BuildingFactory {
    @Override
    public Space createSpace(double area) {
        return new Office(area);
    }

    @Override
    public Space createSpace(int roomsCount, double area) {
        return new Office(roomsCount, area);
    }

    @Override
    public Floor createFloor(int spacesCount) {
        return new OfficeFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space[] spaces) {
        return new OfficeFloor(new MyLinkedList<>(spaces));
    }

    @Override
    public Building createBuilding(int floorsCount, int[] spacesCounts) {
        return new OfficeBuilding(floorsCount, spacesCounts);
    }

    @Override
    public Building createBuilding(Floor[] floors) {
        return new OfficeBuilding(new MyLinkedList<>(floors));
    }
}

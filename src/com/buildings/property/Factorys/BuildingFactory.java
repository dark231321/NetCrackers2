package com.buildings.property.Factorys;

import com.buildings.Container.AbstractArray;
import com.buildings.property.Building;
import com.buildings.property.Floor;
import com.buildings.property.Space;

public interface BuildingFactory {
    Space createSpace(double area);

    Space createSpace(int roomsCount, double area);

    Floor createFloor(int spacesCount);

    Floor createFloor(Space[] spaces);

    Building createBuilding(int floorsCount, int[] spacesCounts);

    Building createBuilding(Floor[] floors);

}

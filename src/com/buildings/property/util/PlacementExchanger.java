package com.buildings.property.util;

import com.buildings.property.Building;
import com.buildings.property.util.Exceptions.InexchangeableFloorsException;
import com.buildings.property.util.Exceptions.InexchangeableSpacesException;
import com.buildings.property.Floor;
import com.buildings.property.Space;

public class PlacementExchanger {
    public static boolean spaceEquals(Space first, Space second){
        return first.getSquare() == second.getSquare() &&
                    first.getCountRooms() == second.getCountRooms();
    }

    public static boolean floorEquals(Floor first,  Floor second){
        return first.getCountRooms() == second.getCountRooms() &&
                first.getSquare() == second.getSquare();
    }

    public static void exchangeFloorRooms(Floor floor1, int index1,
                                          Floor floor2, int index2)
            throws InexchangeableSpacesException {
        Space firstSpace = floor1.get(index1);
        Space secondSpace = floor2.get(index2);

        if(spaceEquals(firstSpace, secondSpace))
            throw new InexchangeableSpacesException(firstSpace, secondSpace);
        floor1.set(index1, secondSpace);
        floor2.set(index2, firstSpace);
    }

    public static void exchangeBuildingFloors(Building building1, int index1,
                                              Building building2, int index2)
            throws InexchangeableFloorsException{
        Floor firstFloor = building1.get(index1);
        Floor secondFloor = building2.get(index2);

        if(floorEquals(firstFloor, secondFloor)){
            throw new InexchangeableFloorsException(firstFloor, secondFloor);
        }

        building1.set(index1, secondFloor);
        building2.set(index2, firstFloor);
    }
}

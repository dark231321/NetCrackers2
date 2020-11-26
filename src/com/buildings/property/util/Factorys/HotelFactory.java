package com.buildings.property.util.Factorys;

import com.buildings.property.Building;
import com.buildings.property.Dwelling.Hotel.Hotel;
import com.buildings.property.Dwelling.Hotel.HotelFloor;
import com.buildings.property.Floor;
import com.buildings.property.Space;

public class HotelFactory extends DwellingFactory {
    @Override
    public Floor createFloor(int spacesCount) {
        return new HotelFloor(spacesCount);
    }

    @Override
    public Floor createFloor(Space... spaces) { return new HotelFloor(spaces); }

    @Override
    public Building createBuilding(int floorsCount, int... spacesCounts) { return new Hotel(floorsCount, spacesCounts); }

    @Override
    public Building createBuilding(Floor... floors) { return new Hotel(floors); }
}

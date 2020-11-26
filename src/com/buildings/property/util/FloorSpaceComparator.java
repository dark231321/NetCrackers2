package com.buildings.property.util;

import com.buildings.property.Floor;

import java.util.Comparator;

public class FloorSpaceComparator implements Comparator<Floor> {
    @Override
    public int compare(Floor o1, Floor o2) {
        return (int) (o1.getSquare() - o2.getSquare());
    }
}

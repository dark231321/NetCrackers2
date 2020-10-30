package com.buildings.property;

import java.util.Comparator;

public class SpaceRoomsComparator implements Comparator<Space> {
    @Override
    public int compare(Space o1, Space o2) {
        return o1.getCountRooms() - o2.getCountRooms();
    }
}

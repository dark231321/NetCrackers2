package com.buildings.property.Dwelling.Hotel;

import com.buildings.Container.MyArrayList;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Floor;

public class Hotel extends Dwelling {

    public Hotel(int countFloor, int[] countSpaces) {
        super(countFloor);
        for(int tmp : countSpaces){
            super.FloorList.add(new HotelFloor(tmp));
        }
    }

    public Hotel(MyArrayList<Floor> floors){
        super(floors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.size() ^ super.getSpaceList().hashCode();
    }

    public int getCountStar(){
        int star = 1;
        int cur;
        for (Floor floor: super.getSpaceList()){
            if(floor instanceof  HotelFloor)
                if((cur = ((HotelFloor) floor).getCountStars()) > star)
                    star = cur;
        }
        return star;
    }

    @Override
    public double getBestSpace() {
        double bestSpace = 0.0;
        double cur = 0.0;
        for (Floor floor: super.getSpaceList()){
            if(floor instanceof  HotelFloor)
                if((cur = floor.getBestSpace()) > bestSpace)
                    bestSpace = cur;
        }
        return bestSpace;
    }

    @Override
    public String toString() {
        return "HotelFloor{" +
                "count Stars = " + getCountStar() +
                " count Floors = " + super.size() +
                super.getSpaceList().toString() +
                '}';
    }
}

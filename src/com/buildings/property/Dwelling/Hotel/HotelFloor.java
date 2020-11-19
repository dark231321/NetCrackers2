package com.buildings.property.Dwelling.Hotel;

import com.buildings.Container.MyArrayList;
import com.buildings.Container.MyCollection;
import com.buildings.Container.MyLinkedList;
import com.buildings.property.Dwelling.DwellingFloor;
import com.buildings.property.Space;

import java.util.Objects;

public class HotelFloor extends DwellingFloor {
    private int countStars;
    private static int defaultCountStars = 1;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HotelFloor spaces = (HotelFloor) o;
        return countStars == spaces.countStars;
    }

    @Override
    public int hashCode() {
        return super.size() ^ super.getSpaceList().hashCode() ^ countStars;
    }

    public HotelFloor(int size) {
        super(size);
        this.countStars = defaultCountStars;
    }

    public HotelFloor(Space[] spaces) {
        super(spaces);
        this.countStars = defaultCountStars;
    }

    public void setCountStars(int countStars) {
        this.countStars = countStars;
    }

    public int getCountStars() {
        return countStars;
    }

    @Override
    public double getBestSpace() {
        MyArrayList<Space> floorList = super.getSpaceList();
        double coeff = this.countStars * 0.25;
        double bestSpace = 0.0;
        double cur = 0.0;
        for(Space space: floorList){
            if((cur = space.getSquare() * coeff) > bestSpace)
                bestSpace = cur;
        }
        return bestSpace;
    }

    @Override
    public String toString() {
        return "HotelFloor{" +
                "count Stars = " + countStars +
                "count Floor = " + super.size() +
                super.getSpaceList().toString() +
                '}';
    }
}

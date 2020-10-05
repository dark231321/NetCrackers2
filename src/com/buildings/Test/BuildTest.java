package com.buildings.Test;

import com.buildings.Container.MyArrayList;
import com.buildings.build.Dwelling;
import com.buildings.build.DwellingFloor;
import com.buildings.build.Flat;

public class BuildTest {
    static Dwelling dwelling;

    private BuildTest(){
    }

    static public void start(){
        MyArrayList<DwellingFloor> myArrayList = new MyArrayList<>();
        start(myArrayList);
        dwelling = Dwelling.ofDwelling(myArrayList);
        print();
    }

    static private void start(MyArrayList<DwellingFloor> myArrayList) {
        for(int j=0; j<2; j++) {
            MyArrayList<Flat> flatList = new MyArrayList<>();
            for (int i = 0; i < 4; i++) {
                if(i == 0)
                    flatList.add(Flat.of());
                else
                    flatList.add(Flat.of(i, i * 10));
            }
            myArrayList.set(j, new DwellingFloor(flatList));
        }
    }

    static private void print(){
        System.out.println();
        System.out.println("Best space: " + dwelling.getBestSpace());
        System.out.println("Count Rooms: " + dwelling.getCountRooms());
        System.out.println("Total square: " + dwelling.getSquare());
        for(int i = 0; i < dwelling.getCountSpace(); i++) {
            var it = dwelling.getSpace(i);
            System.out.println(it.getSquare() + " " + it.getCountRooms());
        }
        var res = dwelling.sortedFlat();
        System.out.println("After Sort");
        for(int i=0;i < res.size(); i++) {
            System.out.print(res.get(i).getSquare() + " ");
        }
    }
}

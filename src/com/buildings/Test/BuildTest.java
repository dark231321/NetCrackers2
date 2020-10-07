package com.buildings.Test;

import com.buildings.Container.MyArrayList;
import com.buildings.build.Dwelling;
import com.buildings.build.DwellingFloor;
import com.buildings.build.Flat;
import com.buildings.property.Space;

import java.util.Arrays;

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

    static private void printDate(){
        System.out.println();
        System.out.println("Best space: " + dwelling.getBestSpace());
        System.out.println("Count Rooms: " + dwelling.getCountRooms());
        System.out.println("Total square: " + dwelling.getSquare());

        var it = dwelling.getSpaceList().iterator();
        while(it.hasNext()){
            var spaceList = it.next().getSpaceList();
            var spaceIt = spaceList.iterator();
            while (spaceIt.hasNext()) {
                Space space = spaceIt.next();
                System.out.print("Count rooms: " + space.getCountRooms() + " Square: " + space.getSquare() + "\n");
            }
            System.out.println();
        }
    }

    static private void exceptions(){
        try { dwelling.set(10, new DwellingFloor(3)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { dwelling.setSpace(10, Flat.of(300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { dwelling.setSpace(0, Flat.of(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { dwelling.setSpace(0, Flat.of(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try {  dwelling.set(-10, new DwellingFloor(3)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }
    }

    static private void print(){
        printDate();
        System.out.println("Before changes");
        dwelling.setSpace(1, Flat.of(300));
        printDate();
        dwelling.removeSpace(1);
        printDate();
        dwelling.set(0, new DwellingFloor(3));
        printDate();
        var res = dwelling.sortedFlat();
        for(int i=0;i < res.size(); i++) {
            System.out.print(res.get(i).getSquare() + " ");
        }
        System.out.println();
        System.out.println("EXCEPTIONS: ");
        exceptions();
    }
}

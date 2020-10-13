package com.buildings.Test;

import com.buildings.Container.MyArrayList;
import com.buildings.property.build.Dwelling;
import com.buildings.property.build.DwellingFloor;
import com.buildings.property.build.Flat;
import com.buildings.property.Floor;
import com.buildings.property.Space;

import java.util.Arrays;

public class BuildTest {
    static Dwelling dwelling;

    private BuildTest(){
    }

    static public void start(){
        MyArrayList<Floor> myArrayList = new MyArrayList<>();
        start(myArrayList);
        dwelling = Dwelling.ofDwelling(myArrayList);
        print();
    }

    static private void start(MyArrayList<Floor> myArrayList) {
        for(int j=0; j<2; j++) {
            MyArrayList<Space> flatList = new MyArrayList<>();
            for (int i = 0; i < 4; i++) {
                if(i == 0)
                    flatList.add(new Flat());
                else
                    flatList.add(new Flat(i, i*10));
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

        try { dwelling.setSpace(10, new Flat(300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { dwelling.setSpace(0,new Flat(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { dwelling.setSpace(0, new Flat(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try {  dwelling.set(-10, new DwellingFloor(3)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }
    }

    static private void print(){
        printDate();
        var s = dwelling.toString();
        System.out.println(s);
        System.out.println("Before changes");
        dwelling.setSpace(1, new Flat(300));
        printDate();
        dwelling.removeSpace(1);
        printDate();
        dwelling.set(0, new DwellingFloor(3));
        printDate();
        var res = dwelling.sortedSpace();
        for(int i=0;i < res.size(); i++) {
            System.out.print(res.get(i).getSquare() + " ");
        }
        System.out.println();
        System.out.println("EXCEPTIONS: ");
        exceptions();
    }
}

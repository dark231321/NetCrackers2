package com.buildings.Test;

import com.buildings.Container.MyArrayList;
import com.buildings.property.*;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Dwelling.DwellingFloor;
import com.buildings.property.Dwelling.Flat;
import com.buildings.property.Algorithms.Buildings;
import com.buildings.property.Dwelling.Hotel.Hotel;
import com.buildings.property.Dwelling.Hotel.HotelFloor;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class BuildTest {
    static Dwelling dwelling;
    static Dwelling dwelling2;
    private BuildTest(){
    }

    static public void start() throws IOException, CloneNotSupportedException {
        MyArrayList<Floor> myArrayList = new MyArrayList<>();
        MyArrayList<Floor> myArrayList1 = new MyArrayList<>();
        start(myArrayList1);
        start(myArrayList);
        dwelling = new Dwelling(myArrayList);
        dwelling2 = new Dwelling(myArrayList1);
        System.out.println(dwelling.equals(dwelling2));
        print();
    }

    static private void start(MyArrayList<Floor> myArrayList) {
        for(int j=0; j<2; j++) {
            MyArrayList<Space> flatList = new MyArrayList<>();
            for (int i = 0; i < 4; i++) {
                if(i == 0)
                    flatList.add(new Flat());
                else
                    flatList.add(new Flat(i, i* 10));
            }
            myArrayList.set(j, new DwellingFloor(flatList));
        }
    }

    static private void printDate(Dwelling dwe){
        System.out.println();
        System.out.println("Best space: " + dwe.getBestSpace());
        System.out.println("Count Rooms: " + dwe.getCountRooms());
        System.out.println("Total square: " + dwe.getSquare());

        var it = dwe.getSpaceList().iterator();
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

    static private void print()
            throws IOException, CloneNotSupportedException {
        printDate(dwelling);
        Buildings.writeBuilding(dwelling, new FileWriter("Building.txt"));
        Buildings.outputBuilding(dwelling, new FileOutputStream("Building.bin"));
        var copy = (Dwelling) dwelling.clone();
        dwelling.set(0, new HotelFloor(1));
        //Buildings.sortBuilding(dwelling, new FloorSpaceComparator(), new SpaceRoomsComparator());
        printDate(dwelling);
        //System.out.println((Hotel)dwelling.toString());
        printDate(copy);
    }
}

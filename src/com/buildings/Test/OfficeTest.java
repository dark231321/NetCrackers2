package com.buildings.Test;

import com.buildings.Container.MyLinkedList;
import com.buildings.property.Algorithms.Buildings;
import com.buildings.property.Building;
import com.buildings.property.Office.Office;
import com.buildings.property.Office.OfficeBuilding;
import com.buildings.property.Office.OfficeFloor;
import com.buildings.property.Dwelling.DwellingFloor;
import com.buildings.property.Floor;
import com.buildings.property.Space;

import java.util.Arrays;

public class OfficeTest {
    static OfficeBuilding officeBuilding;
    static OfficeBuilding officeBuilding1;
    static int k = 1;
    private OfficeTest(){
    }

    static public void start()
            throws CloneNotSupportedException {
        MyLinkedList<Floor> myArrayList = new MyLinkedList<>();
        start(myArrayList);
        officeBuilding = OfficeBuilding.ofOfficeBuilding(myArrayList);
        officeBuilding1 = OfficeBuilding.ofOfficeBuilding(myArrayList);
        System.out.println("Equals: " + officeBuilding.getSpaceList().equals(officeBuilding1.getSpaceList()));
        print();
    }

    static private void start(MyLinkedList<Floor> myArrayList) {
        for(int j=0; j < 2; j++) {
            MyLinkedList<Space> flatList = new MyLinkedList<>();
            for (int i = 0; i < 4; i++) {
                if(i == 0)
                    flatList.add(new Office());
                else
                    flatList.add(new Office(i,i*10 * k));
            }
            myArrayList.add(new OfficeFloor(flatList));
        }
        k++;
    }

    static private void printDate(OfficeBuilding off){
        System.out.println();
        System.out.println("Best space: " + off.getBestSpace());
        System.out.println("Count Rooms: " + off.getCountRooms());
        System.out.println("Total square: " + off.getSquare());

        var it = off.getSpaceList().iterator();
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

        try { officeBuilding.set(10, new OfficeFloor(3)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { officeBuilding.setSpace(10, new Office(300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { officeBuilding.setSpace(0, new Office(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { officeBuilding.setSpace(0, new Office(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try {  officeBuilding.set(-10, new DwellingFloor(3)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }
    }

    static private void print()
            throws CloneNotSupportedException {
        printDate(officeBuilding);
        var newOffice = (OfficeBuilding) officeBuilding.clone();
        officeBuilding.set(1,new OfficeFloor(1));
        Buildings.sortBuilding(officeBuilding);
        printDate(officeBuilding);
        System.out.println(newOffice.toString());
        printDate(newOffice);
    }
}

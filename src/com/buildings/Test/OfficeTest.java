package com.buildings.Test;

import com.buildings.Container.MyLinkedList;
import com.buildings.Office.Office;
import com.buildings.Office.OfficeBuilding;
import com.buildings.Office.OfficeFloor;
import com.buildings.build.DwellingFloor;
import com.buildings.property.Space;

import java.util.Arrays;

public class OfficeTest {
    static OfficeBuilding officeBuilding;

    private OfficeTest(){
    }

    static public void start(){
        MyLinkedList<OfficeFloor> myArrayList = new MyLinkedList<>();
        start(myArrayList);
        officeBuilding = OfficeBuilding.ofOfficeBuilding(myArrayList);
        print();
    }

    static private void start(MyLinkedList<OfficeFloor> myArrayList) {
        for(int j=0; j < 2; j++) {
            MyLinkedList<Office> flatList = new MyLinkedList<>();
            for (int i = 0; i < 4; i++) {
                if(i == 0)
                    flatList.add(Office.of());
                else
                    flatList.add(Office.of(i, i * 10));
            }
            myArrayList.add(new OfficeFloor(flatList));
        }
    }

    static private void printDate(){
        System.out.println();
        System.out.println("Best space: " + officeBuilding.getBestSpace());
        System.out.println("Count Rooms: " + officeBuilding.getCountRooms());
        System.out.println("Total square: " + officeBuilding.getSquare());

        var it = officeBuilding.getSpaceList().iterator();
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

        try { officeBuilding.setSpace(10, Office.of(300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { officeBuilding.setSpace(0, Office.of(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { officeBuilding.setSpace(0, Office.of(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try {  officeBuilding.set(-10, new DwellingFloor(3)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }
    }

    static private void print(){
        printDate();
        System.out.println("Before changes");
        officeBuilding.setSpace(1, Office.of(300));
        printDate();
        officeBuilding.removeSpace(1);
        printDate();
        officeBuilding.set(0, new OfficeFloor(3));
        printDate();
        var res = officeBuilding.sortedOffice();
        for(int i=0;i < res.size(); i++) {
            System.out.print(res.get(i).getSquare() + " ");
        }
        System.out.println();
        System.out.println("EXCEPTIONS: ");
        exceptions();
    }
}

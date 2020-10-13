package com.buildings.Test;

import com.buildings.Container.MyLinkedList;
import com.buildings.property.Office.Office;
import com.buildings.property.Office.OfficeBuilding;
import com.buildings.property.Office.OfficeFloor;
import com.buildings.property.build.DwellingFloor;
import com.buildings.property.Floor;
import com.buildings.property.Space;

import java.util.Arrays;

public class OfficeTest {
    static OfficeBuilding officeBuilding;

    private OfficeTest(){
    }

    static public void start(){
        MyLinkedList<Floor> myArrayList = new MyLinkedList<>();
        start(myArrayList);
        officeBuilding = OfficeBuilding.ofOfficeBuilding(myArrayList);
        print();
    }

    static private void start(MyLinkedList<Floor> myArrayList) {
        for(int j=0; j < 2; j++) {
            MyLinkedList<Space> flatList = new MyLinkedList<>();
            for (int i = 0; i < 4; i++) {
                if(i == 0)
                    flatList.add(new Office());
                else
                    flatList.add(new Office(i,i*10));
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

        try { officeBuilding.setSpace(10, new Office(300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { officeBuilding.setSpace(0, new Office(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try { officeBuilding.setSpace(0, new Office(-300)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }

        try {  officeBuilding.set(-10, new DwellingFloor(3)); }
        catch (Exception ex){ System.out.println(Arrays.toString(ex.getStackTrace())); }
    }

    static private void print(){
        printDate();
        var s = officeBuilding.toString();
        System.out.println(s);
        System.out.println("Before changes");
        officeBuilding.setSpace(1, new Office(300));
        printDate();
        officeBuilding.removeSpace(1);
        printDate();
        officeBuilding.set(0, new OfficeFloor(3));
        printDate();
        var res = officeBuilding.sortedSpace();
        for(int i=0;i < res.size(); i++) {
            System.out.print(res.get(i).getSquare() + " ");
        }
        System.out.println();
        System.out.println("EXCEPTIONS: ");
        exceptions();
    }
}

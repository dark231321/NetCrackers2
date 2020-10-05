package com.buildings.Test;

import com.buildings.Container.MyLinkedList;
import com.buildings.Office.Office;
import com.buildings.Office.OfficeBuilding;
import com.buildings.Office.OfficeFloor;

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
        for(int j=0; j<2; j++) {
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

    static private void print(){
        System.out.println();
        System.out.println("Best space: " + officeBuilding.getBestSpace());
        System.out.println("Count Rooms: " + officeBuilding.getCountRooms());
        System.out.println("Total square: " + officeBuilding.getSquare());
        for(int i = 0; i < officeBuilding.getCountSpace(); i++) {
            var it = officeBuilding.getSpace(i);
            System.out.println(it.getSquare() + " " + it.getCountRooms());
        }
        var res = officeBuilding.sortedOffice();
        System.out.println("After Sort");
        for(int i=0;i < res.size(); i++) {
            System.out.print(res.get(i).getSquare() + " ");
        }
    }
}

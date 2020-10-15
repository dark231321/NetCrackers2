package com.buildings;
import com.buildings.Container.MyArrayList;
import com.buildings.Container.MyLinkedList;
import com.buildings.Test.BuildTest;
import com.buildings.Test.OfficeTest;
import com.buildings.property.Office.Office;
import com.buildings.property.Space;
import com.buildings.property.build.Flat;

public class Main {
    public static void main(String[] args) {
        Space space = new Flat(3, 20);
        Space space1 = new Flat(2,40);
        MyArrayList<Space> test = new MyArrayList<>();
        test.add(space);
        test.add(space1);
        var newArr = test.clone();
        space.setCountRooms(10);
        System.out.println(test.toString());
        System.out.println(newArr.toString());
        //BuildTest.start();
        //OfficeTest.start();
    }
}

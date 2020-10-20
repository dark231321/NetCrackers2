package com.buildings;

import com.buildings.Container.Alghorithms.ArraysMethods;
import com.buildings.Test.OfficeTest;
import com.buildings.property.Building;
import com.buildings.property.Algorithms.Buildings;

import com.buildings.property.Dwelling.Flat;
import com.buildings.property.Office.Office;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        Space[] spaces = {new Office(4,100),
                          new Office(100),
                          new Office(50),
                          new Office(1,250)};
        System.out.println(Arrays.toString(spaces));
        ArraysMethods.sort(spaces,0,spaces.length-1, null);
        System.out.println(Arrays.toString(spaces));
        Building newBuilding = Buildings.inputBuilding(new FileInputStream("Building.bin"));
        System.out.println(newBuilding.toString());
        Buildings.sortBuilding(newBuilding);
        //Buildings.serializeBuilding(newBuilding,new FileOutputStream("building.dat"));
        //Building des = Buildings.deserializeBuilding(new FileInputStream("building.dat"));
        //System.out.println(des.equals(newBuilding));
        OfficeTest.start();
        System.out.println(newBuilding.toString());
    }
}

package com.buildings;

import com.buildings.property.Algorithms.Buildings;
import com.buildings.property.Building;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Dwelling.Hotel.Hotel;
import com.buildings.property.Factorys.BuildingFactory;
import com.buildings.property.Factorys.DwellingFactory;
import com.buildings.property.Factorys.HotelFactory;
import com.buildings.property.Factorys.OfficeFactory;
import com.buildings.property.Office.Office;
import com.buildings.property.Office.OfficeBuilding;
import com.buildings.property.threads.*;

import java.io.*;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, CloneNotSupportedException, InterruptedException {
        BuildingFactory officeFactory = new OfficeFactory();
        BuildingFactory dwellingFactory = new DwellingFactory();
        BuildingFactory hotelFactory = new HotelFactory();
        Dwelling dwelling = (Dwelling) dwellingFactory.createBuilding(4, new int[]{1, 3, 4, 5});
        OfficeBuilding officeBuilding = (OfficeBuilding) officeFactory.createBuilding(4,new int[]{2,4,5,1});
        Hotel hotel = (Hotel) hotelFactory.createBuilding(4, new int[]{1, 3, 4, 5});
        Buildings.setAbstractFactory(hotelFactory);
        Building buildingOffice = Buildings.readBuilding(new FileReader("Building.txt"));
        System.out.println(buildingOffice.toString());
        Buildings.serializeBuilding(buildingOffice, new FileOutputStream("building.dat"));
        Building building = Buildings.deserializeBuilding(new FileInputStream("building.dat"));
        Building building1 = Buildings.deserializeBuilding(new FileInputStream("building.dat"));
        if(Buildings.deserializeBuilding(new FileInputStream("building.dat")) instanceof Hotel)
            System.out.println("Dwelling");
    }
}

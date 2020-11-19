package com.buildings;

import com.buildings.property.Algorithms.Buildings;
import com.buildings.property.Building;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Dwelling.DwellingFloor;
import com.buildings.property.Dwelling.Flat;
import com.buildings.property.Dwelling.Hotel.Hotel;
import com.buildings.property.Factorys.BuildingFactory;
import com.buildings.property.Factorys.DwellingFactory;
import com.buildings.property.Factorys.HotelFactory;
import com.buildings.property.Factorys.OfficeFactory;
import com.buildings.property.Floor;
import com.buildings.property.Office.OfficeBuilding;
import com.buildings.property.Space;
import com.buildings.property.threads.Semaphore;
import com.buildings.property.threads.SequentalCleaner;
import com.buildings.property.threads.SequentalRepairer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;


public class Main {

    public static void main(String[] args)
            throws IOException, CloneNotSupportedException, InterruptedException, InvocationTargetException,
            NoSuchMethodException, InstantiationException, IllegalAccessException {

        //BuildingFactory officeFactory = new OfficeFactory();
        //BuildingFactory dwellingFactory = new DwellingFactory();
        //BuildingFactory hotelFactory = new HotelFactory();
        //Dwelling dwelling = (Dwelling) dwellingFactory.createBuilding(4, new int[]{1, 3, 4, 5});
        //OfficeBuilding officeBuilding = (OfficeBuilding) officeFactory.createBuilding(4,new int[]{2,4,5,1});
        //Hotel hotel = (Hotel) hotelFactory.createBuilding(4, new int[]{1, 3, 4, 5});
        //Buildings.setAbstractFactory(hotelFactory);
        //Building buildingOffice = Buildings.readBuilding(new FileReader("Building.txt"));
        Building building = Buildings.ofBuilding(4, new int[]{1, 3, 4, 5}, Dwelling.class);
        Floor floor = Buildings.ofFloor(4, DwellingFloor.class);
        Space space = Buildings.ofSpace(2, 50.0, Flat.class);
        System.out.println(building.toString());
        System.out.println(floor.toString());
        System.out.println(space.toString());

        //TEST
        Semaphore semaphore = new Semaphore();
        Thread sequentalCleaner = new Thread(new SequentalCleaner(floor, semaphore));
        Thread sequentalRepairer =  new Thread(new SequentalRepairer(floor, semaphore));
        sequentalCleaner.start();
        sequentalRepairer.start();
    }
}

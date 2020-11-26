package com.buildings;

import com.buildings.property.Building;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Dwelling.DwellingFloor;
import com.buildings.property.Dwelling.Flat;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import com.buildings.property.threads.Semaphore;
import com.buildings.property.threads.SequentalCleaner;
import com.buildings.property.threads.SequentalRepairer;
import com.buildings.property.util.Buildings;
import com.buildings.property.util.Factorys.DwellingFactory;

public class Main {

    public static void main(String[] args) {
        Buildings.setAbstractFactory(new DwellingFactory());

        Building building = Buildings.ofBuilding(Dwelling.class, 2,1,3);
        System.out.print("Dwelling building: ");
        for (var floor: building) {
            System.out.println();
            for (var space : floor)
                System.out.print(space.toString() + " ");
        }
        System.out.println("\n----------------------------------------------" +
                "\nDwelling floor:");

        Floor floor = Buildings.ofFloor(4, DwellingFloor.class);
        for (var space: floor)
            System.out.print(space.toString() + " ");

        Space space = Buildings.ofSpace(2, 50.0, Flat.class);
        System.out.println("\n----------------------------------------------");

        Building building1 = Buildings.ofBuilding(2, 1,3);

        System.out.println("Equals a building created by a factory and a building created with reflection:\n "
                + building.equals(building1)
                +"\n----------------------------------------------");

        Floor floor1 = Buildings.ofFloor(4);
        System.out.println("Equals a floor created by a factory and a floor created with reflection:\n "
                + floor.equals(floor1)
                +"\n----------------------------------------------");
        Space space1 = Buildings.ofSpace(2, 50.0);
        System.out.println("Equals a space created by a factory and a space created with reflection:\n "
                + space.equals(space1)
                +"\n----------------------------------------------");


        //TEST
        System.out.println("Semaphore test: ");
        Semaphore semaphore = new Semaphore();
        Thread sequentalCleaner = new Thread(new SequentalCleaner(floor, semaphore));
        Thread sequentalRepairer =  new Thread(new SequentalRepairer(floor, semaphore));
        sequentalCleaner.start();
        sequentalRepairer.start();
    }
}

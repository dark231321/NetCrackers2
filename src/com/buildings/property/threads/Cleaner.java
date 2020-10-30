package com.buildings.property.threads;

import com.buildings.property.Floor;
import com.buildings.property.Space;

public class Cleaner implements Runnable {
    private final Floor floor;

    public Cleaner(Floor floor) {
        this.floor = floor;
    }

    @Override
    public void run() {
        synchronized (this.floor){
            int i = 0;
            for (Space space:
                    floor) {
                System.out.println(" Cleaning room number " + ++i + " with total area " + space.getSquare() + "square metres");
            }
        }
    }
}

package com.buildings.property.threads;

import com.buildings.property.Floor;
import com.buildings.property.Space;

public class Repairer implements Runnable {
    private Floor floor;

    public Repairer(Floor floor){
        this.floor = floor;
    }

    @Override
    public void run() {
        synchronized (this.floor){
            int i = 0;
            for (Space space:
                 floor) {
                System.out.println("Repairing space number " + ++i + " with total area " + space.getSquare() + " square meters");
            }
        }
    }
}

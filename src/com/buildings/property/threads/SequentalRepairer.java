package com.buildings.property.threads;

import com.buildings.property.Floor;
import com.buildings.property.Space;

public class SequentalRepairer implements Runnable {
    private final Floor floor;
    private Semaphore semaphore;

    public SequentalRepairer(Floor floor,Semaphore semaphore){
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        semaphore.beginRepair();
        int i = 0;
        for (Space space:
                floor) {
            System.out.println("Repairing space number " + ++i + " with total area " + space.getSquare() + " square meters");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        semaphore.endRepair();
    }
}

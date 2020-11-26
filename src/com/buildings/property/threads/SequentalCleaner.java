package com.buildings.property.threads;

import com.buildings.property.Floor;
import com.buildings.property.Space;

public class SequentalCleaner implements Runnable {
    private final Floor floor;
    private Semaphore semaphore;

    public SequentalCleaner(Floor floor, Semaphore semaphore){
        this.floor = floor;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        int i = 0;
        for (Space space: floor) {
            try {
                semaphore.beginClean();
                System.out.println("Cleaning room number " + ++i + " with total area " + space.getSquare() + " square meters");
                semaphore.endClean();
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

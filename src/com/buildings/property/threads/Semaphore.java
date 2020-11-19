package com.buildings.property.threads;

import java.util.LinkedList;
import java.util.Queue;

public class Semaphore {
    private Queue<Object> cleanQueue;
    private Queue<Object> repairQueue;
    private boolean repaired = false;
    public int state;

    public Semaphore() {
        this.cleanQueue = new LinkedList<>();
        this.repairQueue = new LinkedList<>();
    }

    public void beginRepair(){
        Object object = new Object();
        synchronized (object){
            try {
                if(repaired || state > 0){
                    this.repairQueue.add(object);
                    object.wait();
                }
                else{
                    state++;
                    repaired = !repaired;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void beginClean() {
        Object object = new Object();
        synchronized (object) {
            try {
                if (!repaired || state > 0) {
                    this.cleanQueue.add(object);
                    object.wait();
                } else {
                    state++;
                    repaired = !repaired;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void endClean(){
        Object object = this.repairQueue.poll();
        state--;
        if (null == object)
            return;
        synchronized (object){
            object.notify();
        }
    }

    public void endRepair(){
        Object object = this.cleanQueue.poll();
        state--;
        if (null == object)
            return;
        synchronized (object){
            object.notify();
        }
    }
}

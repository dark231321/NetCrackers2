package com.buildings.property.threads;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Semaphore {
    private final BlockingQueue<Object> cleanQueue;
    private final BlockingQueue<Object> repairQueue;
    private final AtomicBoolean repaired = new AtomicBoolean(false);
    private final AtomicInteger state = new AtomicInteger(0);

    public Semaphore() {
        this.cleanQueue = new LinkedBlockingQueue<>();
        this.repairQueue = new LinkedBlockingQueue<>();
    }

    public void beginRepair(){
        Object object = new Object();
        synchronized (object){
            try {
                if(repaired.get() || state.get() != 0) {
                    this.repairQueue.add(object);
                    object.wait();
                } else {
                    state.incrementAndGet();
                    repaired.set(true);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void beginClean() throws InterruptedException {
        Object object = new Object();
        synchronized (object) {
            try {
                if (!repaired.get() || state.get() != 0) {
                    this.cleanQueue.add(object);
                    object.wait();
                } else {
                    state.incrementAndGet();
                    repaired.set(false);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void endClean(){
        Object object = this.repairQueue.poll();
        state.decrementAndGet();
        if (null == object)
            return;
        synchronized (object){
            state.incrementAndGet();
            object.notify();
        }
    }

    public void endRepair(){
        Object object = this.cleanQueue.poll();
        state.decrementAndGet();
        if (null == object)
            return;
        synchronized (object){
            state.incrementAndGet();
            object.notify();
        }
    }
}

package com.buildings.property.Decorators;

import com.buildings.Container.AbstractArray;
import com.buildings.Container.ListIterator;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class SynchronizedFloor implements Floor {
    private Floor floor;

    public SynchronizedFloor(Floor floor){
        this.floor = floor;
    }

    @Override
    public synchronized Iterator<Space> iterator() {
        return this.floor.iterator();
    }

    @Override
    public synchronized ListIterator<Space> myListIterator(int index) {
        return this.myListIterator(index);
    }

    @Override
    public synchronized int size() {
        return this.floor.size();
    }

    @Override
    public synchronized AbstractArray<Space> getSpaceList() {
        return this.floor.getSpaceList();
    }

    @Override
    public synchronized Space set(int index, Space space) {
        return this.floor.set(index, space);
    }

    @Override
    public synchronized double getSquare() {
        return this.floor.getSquare();
    }

    @Override
    public synchronized Space get(int index) {
        return this.floor.get(index);
    }

    @Override
    public synchronized Space setRooms(int index, int newCountRooms) {
        return this.floor.setRooms(index, newCountRooms);
    }

    @Override
    public synchronized double getBestSpace() {
        return this.floor.getBestSpace();
    }

    @Override
    public synchronized boolean remove(int index) {
        return this.floor.remove(index);
    }

    @Override
    public synchronized int getCountRooms() {
        return this.floor.getCountRooms();
    }

    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        return this.floor.clone();
    }

    @Override
    public synchronized int compareTo(@NotNull Floor floor) {
        return this.floor.compareTo(floor);
    }
}

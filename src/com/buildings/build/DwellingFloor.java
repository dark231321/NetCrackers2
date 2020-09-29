package com.buildings.build;

import com.buildings.Container.MyArrayList;
import com.buildings.Container.MyIterator;
import com.buildings.Container.MyListIterator;
import org.jetbrains.annotations.NotNull;

public class DwellingFloor {
    private MyArrayList<Flat> flatList;

    private int countRooms = 0;
    private double square = 0.0;

    public DwellingFloor(int size) {
        if(size < 0)
            throw new IllegalArgumentException();

        this.flatList = new MyArrayList<>(size);
        for(int i = 0; i < size; i++)
            flatList.add(Flat.of());
        getCalculation();
    }

    public MyListIterator<Flat> flatMyListIterator(int index){
        return flatList.iterator(index);
    }

    public DwellingFloor(MyArrayList<Flat> flatList){
        this.flatList = flatList;
        getCalculation();
    }

    public int size() {
        return this.flatList.size();
    }

    private void getCalculation(){
        MyIterator<Flat> it = flatList.iterator();
        while (it.hasNext()){
            Flat flat = it.next();
            countRooms += flat.getCountRooms();
            square     += flat.getSquare();
        }
    }

    public int getCountRooms() {
        if(countRooms < 0)
            throw new IllegalArgumentException();
        return countRooms;
    }

    public double getSquare(){
        if(countRooms < 0)
            throw new IllegalArgumentException();
        return square;
    }

    public Flat get(int index) {
        return flatList.get(index);
    }

    public Flat set(int index, @NotNull Flat flat){
        Flat tmp = flatList.iterator(index).next();
        this.countRooms += flat.getCountRooms()  - tmp.getCountRooms();
        this.square += flat.getSquare() - tmp.getSquare();
        flatList.set(index, flat);
        return flat;
    }

    public Flat setRooms(int index, int newCountRooms) {
        MyListIterator<Flat> it = flatList.iterator(index);
        it.next().setCountRooms(newCountRooms);
        return flatList.get(index);
    }

    public MyArrayList<Flat> getFlatList() {
        return flatList;
    }

    public boolean Remove(int index){
        Flat tmp = flatList.iterator(index).next();
        this.square -= tmp.getSquare();
        this.countRooms -= tmp.getCountRooms();
        return flatList.remove(index);
    }

    public double getBestSpace(){
        MyIterator<Flat> it = flatList.iterator();
        double tmp = 0;
        while (it.hasNext()){
            Flat flat = it.next();
            if(tmp < flat.getSquare())
                tmp = flat.getSquare();
        }
        return tmp;
    }
}

package com.buildings.Office;

import com.buildings.Container.MyLinkedList;
import com.buildings.Container.MyIterator;
import com.buildings.Container.MyListIterator;
import org.jetbrains.annotations.NotNull;

public class OfficeFloor {
    private MyLinkedList<Office> OfficeList;

    private int countRooms = 0;
    private double square = 0.0;

    public OfficeFloor(int size) {
        if(size < 0)
            throw new IllegalArgumentException();

        this.OfficeList = new MyLinkedList<>();
        for(int i = 0; i < size; i++)
            OfficeList.add(Office.of());
        getCalculation();
    }

    public MyListIterator<Office> OfficeMyListIterator(int index){
        return OfficeList.iterator(index);
    }

    public OfficeFloor(MyLinkedList<Office> OfficeList){
        this.OfficeList = OfficeList;
        getCalculation();
    }

    public int size() {
        return this.OfficeList.size();
    }

    private void getCalculation(){
        MyIterator<Office> it = OfficeList.iterator();
        while (it.hasNext()){
            Office Office = it.next();
            countRooms += Office.getCountRooms();
            square     += Office.getSquare();
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

    public Office get(int index) {
        return OfficeList.get(index);
    }

    public Office set(int index, @NotNull Office Office){
        Office tmp = OfficeList.iterator(index).next();
        this.countRooms += Office.getCountRooms()  - tmp.getCountRooms();
        this.square += Office.getSquare() - tmp.getSquare();
        OfficeList.set(index, Office);
        return Office;
    }

    public Office setRooms(int index, int newCountRooms) {
        MyListIterator<Office> it = OfficeList.iterator(index);
        it.next().setCountRooms(newCountRooms);
        return OfficeList.get(index);
    }

    public MyLinkedList<Office> getOfficeList() {
        return OfficeList;
    }

    public boolean Remove(int index){
        Office tmp = OfficeList.iterator(index).get();
        this.square -= tmp.getSquare();
        this.countRooms -= tmp.getCountRooms();
        return OfficeList.remove(index);
    }

    public double getBestSpace(){
        MyIterator<Office> it = OfficeList.iterator();
        double tmp = 0;
        while (it.hasNext()){
            Office Office = it.next();
            if(tmp < Office.getSquare())
                tmp = Office.getSquare();
        }
        return tmp;
    }
}

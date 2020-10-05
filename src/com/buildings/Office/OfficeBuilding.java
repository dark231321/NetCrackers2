package com.buildings.Office;

import com.buildings.Container.MyLinkedList;
import com.buildings.Container.MyIterator;
import com.buildings.Container.MyListIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class OfficeBuilding {
    private MyLinkedList<OfficeFloor> OfficeList;
    private static MyLinkedList<OfficeFloor> Office_LIST;
    private int countRooms = 0;
    private double square = 0.0;

    public OfficeBuilding(MyLinkedList<Integer> countFloor){
        if(countFloor.size() < 0)
            throw new IllegalArgumentException();
        OfficeList = new MyLinkedList<>();
        for(int i =0; i < countFloor.size(); i++){
            OfficeList.set(i, new OfficeFloor(countFloor.get(i)));
        }
        getCalculation();
    }

    private OfficeBuilding() {
        if(Office_LIST == null)
            throw new IllegalArgumentException();
        this.OfficeList = Office_LIST;
        Office_LIST = null;
        getCalculation();
    }

    @NotNull
    public static OfficeBuilding ofOfficeBuilding(MyLinkedList<OfficeFloor> OfficeList) {
        Objects.requireNonNull(OfficeList);
        Office_LIST = OfficeList;
        return new OfficeBuilding();
    }

    private void getCalculation(){
        MyIterator<OfficeFloor> it = OfficeList.iterator();
        while (it.hasNext()){
            OfficeFloor OfficeFloor = it.next();
            countRooms += OfficeFloor.getCountRooms();
            square     += OfficeFloor.getSquare();
        }
    }

    @Nullable
    private MyListIterator<Office> findOffice(int numberOffice){
        int tmp = numberOffice;
        for(int i = 0; i < this.OfficeList.size(); i++){
            if(tmp >= this.OfficeList.get(i).size())
                tmp -= this.OfficeList.get(i).size();
            else
                return this.OfficeList.get(i).OfficeMyListIterator(tmp);
        }
        return null;
    }

    public Office getOffice(int numberOffice){
        var it = findOffice(numberOffice);
        if (it == null)
            throw new IllegalArgumentException();
        return it.get();
    }

    public boolean removeOffice(int numberOffice){
        var it = findOffice(numberOffice);
        if (it == null)
            throw new IllegalArgumentException();
        countRooms-=it.get().getCountRooms();
        square-=it.get().getSquare();
        it.remove();
        return true;
    }

    public boolean setOffice(int numberOffice, Office Office){
        var it = findOffice(numberOffice);
        if(it == null)
            throw new IllegalArgumentException();
        countRooms += Office.getCountRooms() - it.get().getCountRooms();
        square += Office.getSquare() - it.get().getSquare();
        it.set(Office);
        return true;
    }

    public OfficeFloor set(int index, OfficeFloor OfficeFloor){
        OfficeList.set(index, OfficeFloor);
        getCalculation();
        return OfficeList.get(index);
    }

    public OfficeFloor get(int index){ return OfficeList.get(index);}

    public MyLinkedList<OfficeFloor> getOfficeList() { return OfficeList; }

    public int size() { return OfficeList.size(); }

    public int getCountRooms(){ return countRooms; }

    public int getCountOffices(){
        if(this.OfficeList == null)
            throw new NoSuchElementException();
        int tmp = 0;
        for(int i = 0;i < this.OfficeList.size(); i++) {
            tmp += this.OfficeList.get(i).size();
        }
        return tmp;
    }

    public double getBestSpace() {
        if(this.OfficeList == null)
            throw new NoSuchElementException();
        double tmp = this.OfficeList.get(0).getBestSpace();
        for(int i = 0; i < OfficeList.size(); i++){
            if(tmp < OfficeList.get(i).getBestSpace())
                tmp = OfficeList.get(i).getBestSpace();
        }
        return tmp;
    }

    public double getSquare() { return square; }

    public MyLinkedList<Office> sortedOffice(){
        int countOffices = this.getCountOffices();
        MyLinkedList<Office> tmp = new MyLinkedList<>();
        for(int i = 0; i < countOffices; i++) {
            var it = this.getOffice(i);
            tmp.add(it);
        }
        Comparator<Office> comparator = Comparator.comparing(Office::getSquare);
        tmp.sort(comparator);
        return tmp;
    }
}

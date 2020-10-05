package com.buildings.Office;

import com.buildings.Container.MyLinkedList;
import com.buildings.Container.MyIterator;
import com.buildings.Container.MyListIterator;
import com.buildings.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.Exceptions.SpaceIndexOutOfBoundsException;
import com.buildings.property.Building;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Objects;

public class OfficeBuilding implements Building {
    private MyLinkedList<OfficeFloor> OfficeList;
    private static MyLinkedList<OfficeFloor> Office_LIST;
    private int countRooms = 0;
    private double square = 0.0;

    public OfficeBuilding(@NotNull MyLinkedList<Integer> countFloor){
        if(countFloor.size() == 0)
            throw new SpaceIndexOutOfBoundsException();
        OfficeList = new MyLinkedList<>();
        for(int i =0; i < countFloor.size(); i++){
            OfficeList.set(i, new OfficeFloor(countFloor.get(i)));
        }
        getCalculation();
    }

    private OfficeBuilding() {
        if(Office_LIST == null)
            throw new SpaceIndexOutOfBoundsException();
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
                return this.OfficeList.get(i).MyListIterator(tmp);
        }
        return null;
    }

    public Office getSpace(int numberOffice){
        var it = findOffice(numberOffice);
        if (it == null)
            throw new FloorIndexOutOfBoundsException();
        return it.get();
    }

    public void removeSpace(int numberOffice){
        var it = findOffice(numberOffice);
        if (it == null)
            throw new FloorIndexOutOfBoundsException();
        countRooms-=it.get().getCountRooms();
        square-=it.get().getSquare();
        it.remove();
    }

    public boolean setOffice(int numberOffice, Office Office){
        var it = findOffice(numberOffice);
        if(it == null)
            throw new FloorIndexOutOfBoundsException();
        countRooms += Office.getCountRooms() - it.get().getCountRooms();
        square += Office.getSquare() - it.get().getSquare();
        it.set(Office);
        return true;
    }

    public OfficeFloor set(int index, OfficeFloor OfficeFloor){
        if(index<0 || index>this.OfficeList.size())
            throw new FloorIndexOutOfBoundsException();
        OfficeList.set(index, OfficeFloor);
        getCalculation();
        return OfficeList.get(index);
    }

    public OfficeFloor get(int index){
        if(index<0 || index>this.OfficeList.size())
            throw new FloorIndexOutOfBoundsException();
        return OfficeList.get(index);
    }

    public MyLinkedList<OfficeFloor> getOfficeList() { return OfficeList; }

    public int size() { return OfficeList.size(); }

    public int getCountRooms(){ return countRooms; }

    public int getCountSpace(){
        if(this.OfficeList == null)
            throw new FloorIndexOutOfBoundsException();
        int tmp = 0;
        for(int i = 0;i < this.OfficeList.size(); i++) {
            tmp += this.OfficeList.get(i).size();
        }
        return tmp;
    }

    public double getBestSpace() {
        if(this.OfficeList == null)
            throw new FloorIndexOutOfBoundsException();
        double tmp = this.OfficeList.get(0).getBestSpace();
        for(int i = 0; i < OfficeList.size(); i++){
            if(tmp < OfficeList.get(i).getBestSpace())
                tmp = OfficeList.get(i).getBestSpace();
        }
        return tmp;
    }

    public double getSquare() { return square; }

    public MyLinkedList<Office> sortedOffice(){
        int countOffices = this.getCountSpace();
        MyLinkedList<Office> tmp = new MyLinkedList<>();
        for(int i = 0; i < countOffices; i++) {
            var it = this.getSpace(i);
            tmp.add(it);
        }
        Comparator<Office> comparator = Comparator.comparing(Office::getSquare);
        tmp.sort(comparator);
        return tmp;
    }
}

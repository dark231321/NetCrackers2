package com.buildings.property.Office;

import com.buildings.Container.MyLinkedList;
import com.buildings.Container.MyIterator;
import com.buildings.Container.MyListIterator;
import com.buildings.property.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.property.Exceptions.SpaceIndexOutOfBoundsException;
import com.buildings.property.Building;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Objects;

public class OfficeBuilding implements Building {
    private MyLinkedList<Floor> OfficeList;
    private static MyLinkedList<Floor> Office_LIST;
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
    public static OfficeBuilding ofOfficeBuilding(MyLinkedList<Floor> OfficeList) {
        Objects.requireNonNull(OfficeList);
        Office_LIST = OfficeList;
        return new OfficeBuilding();
    }

    private void getCalculation(){
        MyIterator<Floor> it = OfficeList.iterator();
        while (it.hasNext()){
            Floor Floor = it.next();
            countRooms += Floor.getCountRooms();
            square     += Floor.getSquare();
        }
    }

    @Nullable
    private MyListIterator<Space> findOffice(int numberOffice){
        int tmp = numberOffice;
        for(int i = 0; i < this.OfficeList.size(); i++){
            if(tmp >= this.OfficeList.get(i).size())
                tmp -= this.OfficeList.get(i).size();
            else
                return this.OfficeList.get(i).MyListIterator(tmp);
        }
        return null;
    }

    public Space getSpace(int numberOffice){
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

    public void setSpace(int numberOffice, Space Office){
        var it = findOffice(numberOffice);
        if(it == null)
            throw new FloorIndexOutOfBoundsException();
        countRooms += Office.getCountRooms() - it.get().getCountRooms();
        square += Office.getSquare() - it.get().getSquare();
        it.set((Office) Office);
    }

    private void RecalculateFloorDecr(@NotNull MyListIterator<? extends Space> iterator){
        while (iterator.hasNext()){
            Space oldSpace = iterator.next();
            this.square -= oldSpace.getSquare();
            this.countRooms -= oldSpace.getCountRooms();
        }
    }

    private void RecalculateFloorIncr(@NotNull MyListIterator<? extends Space> iterator){
        while (iterator.hasNext()){
            Space oldSpace = iterator.next();
            this.square += oldSpace.getSquare();
            this.countRooms += oldSpace.getCountRooms();
        }
    }

    public Floor set(int index, Floor Floor){
        if(index<0 || index>this.OfficeList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(OfficeList.get(index).MyListIterator(0));
        OfficeList.set(index,(Floor) Floor);
        RecalculateFloorIncr(OfficeList.get(index).MyListIterator(0));
        return OfficeList.get(index);
    }

    public Floor get(int index){
        if(index<0 || index>this.OfficeList.size())
            throw new FloorIndexOutOfBoundsException();
        return OfficeList.get(index);
    }

    public MyLinkedList<Floor> getSpaceList() { return OfficeList; }

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

    public MyLinkedList<Space> sortedSpace(){
        int countOffices = this.getCountSpace();
        MyLinkedList<Space> tmp = new MyLinkedList<>();
        for(int i = 0; i < countOffices; i++) {
            var it = this.getSpace(i);
            tmp.add(it);
        }
        Comparator<Space> comparator = Comparator.comparing(Space::getSquare);
        tmp.sort(comparator);
        return tmp;
    }

    @Override
    public String toString() {
        return "Building{" +
                "Count Floors = " + OfficeList.size() +
                ", SpaceList = " + OfficeList.toString() +
                '}';
    }
}

package com.buildings.property.Office;

import com.buildings.Container.ListIterator;
import com.buildings.Container.MyLinkedList;
import com.buildings.property.Building;
import com.buildings.property.util.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.property.util.Exceptions.SpaceIndexOutOfBoundsException;
import com.buildings.property.Floor;
import com.buildings.property.Space;

import java.util.Comparator;
import java.util.Iterator;

public class OfficeBuilding implements Building {
    private MyLinkedList<Floor> FloorList;
    private int countRooms = 0;
    private double square = 0.0;

    public OfficeBuilding(int countRooms, int... countFloor){
        if(countFloor.length == 0)
            throw new SpaceIndexOutOfBoundsException();
        FloorList = new MyLinkedList<>();
        for(int i =0; i < countRooms; i++){
            FloorList.add(new OfficeFloor(countFloor[i]));
        }
        getCalculation();
    }

    public OfficeBuilding(Floor... floors) {
        this.FloorList = new MyLinkedList<>(floors);
        getCalculation();
    }

    private void getCalculation(){
        if(FloorList == null)
            return;
        for (com.buildings.property.Floor Floor : FloorList) {
            countRooms += Floor.getCountRooms();
            square += Floor.getSquare();
        }
    }

    private ListIterator<Space> findOffice(int numberOffice){
        int tmp = numberOffice; int length;
        for(var it : this.FloorList){
            if(tmp >= (length = it.size()))
                tmp -= length;
            else
                return it.myListIterator(tmp);
        }
        return null;
    }

    @Override
    public Iterator<Floor> iterator() {
        return FloorList.iterator();
    }

    @Override
    public Space getSpace(int numberOffice){
        var it = findOffice(numberOffice);
        if (it == null)
            throw new FloorIndexOutOfBoundsException();
        return it.get();
    }

    @Override
    public void removeSpace(int numberOffice){
        var it = findOffice(numberOffice);
        if (it == null)
            throw new FloorIndexOutOfBoundsException();
        countRooms-=it.get().getCountRooms();
        square-=it.get().getSquare();
        it.remove();
    }

    @Override
    public void setSpace(int numberOffice, Space Office){
        var it = findOffice(numberOffice);
        if(it == null)
            throw new FloorIndexOutOfBoundsException();
        countRooms += Office.getCountRooms() - it.get().getCountRooms();
        square += Office.getSquare() - it.get().getSquare();
        it.set(Office);
    }

    private void RecalculateFloorDecr(Floor floor){
        for (var it: floor){
            this.square -= it.getSquare();
            this.countRooms -= it.getCountRooms();
        }
    }

    private void RecalculateFloorIncr(Floor floor){
        for (var it: floor){
            this.square += it.getSquare();
            this.countRooms += it.getCountRooms();
        }
    }

    @Override
    public Floor set(int index, Floor Floor){
        if(index<0 || index>this.FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(FloorList.get(index));
        FloorList.set(index,(Floor) Floor);
        RecalculateFloorIncr(FloorList.get(index));
        return FloorList.get(index);
    }

    @Override
    public Floor get(int index){
        if(index<0 || index>this.FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        return FloorList.get(index);
    }

    @Override
    public MyLinkedList<Floor> getFloorList() { return FloorList; }

    @Override
    public int size() { return FloorList.size(); }

    @Override
    public int getCountRooms(){ return countRooms; }

    @Override
    public int getCountSpace(){
        if(this.FloorList == null)
            throw new FloorIndexOutOfBoundsException();
        int tmp = 0;
        for(var it: this.FloorList){
            tmp += it.size();
        }
        return tmp;
    }

    @Override
    public double getBestSpace() {
        if(this.FloorList == null)
            throw new FloorIndexOutOfBoundsException();
        double tmp = this.FloorList.get(0).getBestSpace(); double bestSpace;
        for(var it: this.FloorList){
            if(tmp < (bestSpace = it.getBestSpace()))
                tmp = bestSpace;
        }
        return tmp;
    }

    @Override
    public double getSquare() { return square; }

    @Override
    public MyLinkedList<Space> sortedSpace(){
        int countOffices = this.getCountSpace();
        MyLinkedList<Space> tmp = new MyLinkedList<>();
        for(var floor: this.FloorList)
            for(var space: floor)
                tmp.add(space);
        Comparator<Space> comparator = Comparator.comparing(Space::getSquare);
        tmp.sort(comparator);
        return tmp;
    }

    @Override
    public String toString() {
        return "Building{" +
                "Count Floors = " + FloorList.size() +
                ", SpaceList = " + FloorList.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeBuilding that = (OfficeBuilding) o;
        return countRooms == that.countRooms &&
                Double.compare(that.square, square) == 0 &&
                this.FloorList.equals(that.FloorList);
    }

    @Override
    public int hashCode() {
        return this.FloorList.size() ^ this.FloorList.hashCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone()
            throws CloneNotSupportedException {
        OfficeBuilding clone = (OfficeBuilding) super.clone();
        clone.FloorList = new MyLinkedList<>();
        for(Floor floor: FloorList){
            clone.FloorList.add((Floor) floor.clone());
        }
        return clone;
    }

}

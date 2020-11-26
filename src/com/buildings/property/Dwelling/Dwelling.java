package com.buildings.property.Dwelling;

import com.buildings.Container.ListIterator;
import com.buildings.Container.MyArrayList;
import com.buildings.property.Building;
import com.buildings.property.util.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.property.util.Exceptions.SpaceIndexOutOfBoundsException;
import com.buildings.property.Floor;
import com.buildings.property.Space;

import java.util.Comparator;
import java.util.Iterator;

public class Dwelling implements Building {
    protected MyArrayList<Floor> FloorList;
    private int countRooms = 0;
    private double square = 0.0;

    protected Dwelling(int size){
        FloorList = new MyArrayList<>(size);
    }

    public Dwelling(int countFloors, int... countSpaces){
        if(countSpaces.length == 0)
            throw new IllegalArgumentException();
        FloorList = new MyArrayList<>(countSpaces.length);
        for(int i =0; i < countFloors; i++){
           FloorList.set(i, new DwellingFloor(countSpaces[i]));
        }
        getCalculation();
    }

    public Dwelling(Floor... floors) {
        this.FloorList = new MyArrayList<>(floors);
        getCalculation();
    }


    private void getCalculation(){
        Iterator<Floor> it = FloorList.iterator();
        while (it.hasNext()){
            Floor Floor = it.next();
            countRooms += Floor.getCountRooms();
            square     += Floor.getSquare();
        }
    }

    private ListIterator<Space> findSpace(int numberSpace){
        int tmp = numberSpace;
        for(var it: this.FloorList){
            if(tmp > it.size())
                tmp -= it.size();
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
    public Space getSpace(int numberSpace){
        var it = findSpace(numberSpace);
        if (it == null)
            throw new SpaceIndexOutOfBoundsException();
        return it.get();
    }

    @Override
    public void removeSpace(int numberSpace){
        var it = findSpace(numberSpace);
        if (it == null)
            throw new SpaceIndexOutOfBoundsException();
        countRooms-=it.get().getCountRooms();
        square-=it.get().getSquare();
        it.remove();
    }

    @Override
    public void setSpace(int numberSpace, Space Space){
        var it = findSpace(numberSpace);
        if(it == null)
            throw new SpaceIndexOutOfBoundsException();
        countRooms += Space.getCountRooms() - it.get().getCountRooms();
        square += Space.getSquare() - it.get().getSquare();
        it.set(Space);
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
        if(index < 0 || index >= FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(FloorList.get(index));
        FloorList.set(index, Floor);
        RecalculateFloorIncr(FloorList.get(index));
        return FloorList.get(index);
    }

    @Override
    public Floor get(int index){
        if(index < 0 || index >= FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        return FloorList.get(index);
    }

    @Override
    public MyArrayList<Floor> getFloorList() { return FloorList; }

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
        double tmp = this.FloorList.get(0).getBestSpace();
        for(var it: this.FloorList){
            if(tmp < it.getBestSpace())
                tmp = it.getBestSpace();
        }
        return tmp;
    }

    @Override
    public double getSquare() { return square; }

    @Override
    public MyArrayList<Space> sortedSpace(){
        MyArrayList<Space> tmp = new MyArrayList<>();
        for(var floor: this.FloorList)
            for(var space: floor)
                tmp.add(space);
        Comparator<Space> comparator = Comparator.comparing(Space::getSquare);
        tmp.sort(comparator);
        return tmp;
    }

    @Override
    public String toString() {
        return "Dwelling{" +
                "Count Floors = " + FloorList.size() +
                ", FloorList = " + FloorList.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dwelling dwelling = (Dwelling) o;
        return  countRooms == dwelling.countRooms &&
                Double.compare(dwelling.square, square) == 0 &&
                FloorList.equals(dwelling.FloorList);
    }

    @Override
    public int hashCode() {
        return this.FloorList.size() ^ this.FloorList.hashCode();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone()
            throws CloneNotSupportedException {
        Dwelling clone = (Dwelling) super.clone();
        clone.FloorList = new MyArrayList<>();
        for(Floor floor: FloorList){
            clone.FloorList.add((Floor)floor.clone());
        }
        return clone;
    }

}

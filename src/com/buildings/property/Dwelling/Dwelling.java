package com.buildings.property.Dwelling;

import com.buildings.Container.*;
import com.buildings.property.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.property.Exceptions.SpaceIndexOutOfBoundsException;
import com.buildings.property.Building;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

public class Dwelling implements Building {
    protected MyArrayList<Floor> FloorList;
    private int countRooms = 0;
    private double square = 0.0;

    protected Dwelling(int size){
        FloorList = new MyArrayList<>(size);
    }

    public Dwelling(int countFloors, int[] countSpaces){
        if(countSpaces.length == 0)
            throw new IllegalArgumentException();
        FloorList = new MyArrayList<>(countSpaces.length);
        for(int i =0; i < countFloors; i++){
           FloorList.set(i, new DwellingFloor(countSpaces[i]));
        }
        getCalculation();
    }

    public Dwelling(Floor[] floors) {
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

    @Nullable
    private ListIterator<Space> findSpace(int numberSpace){
        int tmp = numberSpace;
        for(int i = 0; i < this.FloorList.size(); i++){
            if(tmp >= this.FloorList.get(i).size())
                tmp -= this.FloorList.get(i).size();
            else
                return this.FloorList.get(i).myListIterator(tmp);
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
    public void setSpace(int numberSpace, @NotNull Space Space){
        var it = findSpace(numberSpace);
        if(it == null)
            throw new SpaceIndexOutOfBoundsException();
        countRooms += Space.getCountRooms() - it.get().getCountRooms();
        square += Space.getSquare() - it.get().getSquare();
        it.set(Space);
    }

    private void RecalculateFloorDecr(@NotNull ListIterator<? extends Space> iterator){
        while (iterator.hasNext()){
            Space oldSpace = iterator.next();
            this.square -= oldSpace.getSquare();
            this.countRooms -= oldSpace.getCountRooms();
        }
    }

    private void RecalculateFloorIncr(@NotNull ListIterator<? extends Space> iterator){
        while (iterator.hasNext()){
            Space oldSpace = iterator.next();
            this.square += oldSpace.getSquare();
            this.countRooms += oldSpace.getCountRooms();
        }
    }

    @Override
    public Floor set(int index,@NotNull Floor Floor){
        if(index < 0 || index >= FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(FloorList.get(index).myListIterator(0));
        FloorList.set(index, Floor);
        RecalculateFloorIncr(FloorList.get(index).myListIterator(0));
        return FloorList.get(index);
    }

    @Override
    public Floor get(int index){
        if(index < 0 || index >= FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        return FloorList.get(index);
    }

    @Override
    public MyArrayList<Floor> getSpaceList() { return FloorList; }

    @Override
    public int size() { return FloorList.size(); }

    @Override
    public int getCountRooms(){ return countRooms; }

    @Override
    public int getCountSpace(){
        if(this.FloorList == null)
            throw new FloorIndexOutOfBoundsException();
        int tmp = 0;
        for(int i = 0;i < this.FloorList.size(); i++) {
            tmp += this.FloorList.get(i).size();
        }
        return tmp;
    }

    @Override
    public double getBestSpace() {
        if(this.FloorList == null)
            throw new FloorIndexOutOfBoundsException();
        double tmp = this.FloorList.get(0).getBestSpace();
        for(int i = 0; i < FloorList.size(); i++){
            if(tmp < FloorList.get(i).getBestSpace())
                tmp = FloorList.get(i).getBestSpace();
        }
        return tmp;
    }

    @Override
    public double getSquare() { return square; }

    @Override
    public MyArrayList<Space> sortedSpace(){
        int countSpaces = this.getCountSpace();
        MyArrayList<Space> tmp = new MyArrayList<>();
        for(int i = 0; i < countSpaces; i++) {
            var it = this.getSpace(i);
            tmp.add(it);
        }
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
        for(Floor floor:
            FloorList){
            clone.FloorList.add((Floor)floor.clone());
        }
        return clone;
    }

}

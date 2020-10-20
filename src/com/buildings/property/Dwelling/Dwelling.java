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
import java.util.stream.Stream;

public class Dwelling implements Building {
    private MyArrayList<Floor> FloorList;
    private static MyArrayList<Floor> Space_LIST;
    private int countRooms = 0;
    private double square = 0.0;

    public Dwelling(@NotNull MyArrayList<Integer> countFloor){
        if(countFloor.size() < 0)
            throw new IllegalArgumentException();
        FloorList = new MyArrayList<>(countFloor.size());
        for(int i =0; i < countFloor.size(); i++){
           FloorList.set(i, new DwellingFloor(countFloor.get(i)));
        }
        getCalculation();
    }

    private Dwelling() {
        if(Space_LIST == null)
            this.FloorList = new MyArrayList<Floor>();
        else{
            this.FloorList = Space_LIST;
            Space_LIST = null;
        }
        getCalculation();
    }

    @NotNull
    public static Dwelling ofDwelling(MyArrayList<Floor> FloorList) {
        Objects.requireNonNull(FloorList);
        Space_LIST = FloorList;
        return new Dwelling();
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
                return this.FloorList.get(i).MyListIterator(tmp);
        }
        return null;
    }

    @Override
    public Iterator<Floor> iterator() {
        return FloorList.iterator();
    }

    public Space getSpace(int numberSpace){
        var it = findSpace(numberSpace);
        if (it == null)
            throw new SpaceIndexOutOfBoundsException();
        return it.get();
    }

    public void removeSpace(int numberSpace){
        var it = findSpace(numberSpace);
        if (it == null)
            throw new SpaceIndexOutOfBoundsException();
        countRooms-=it.get().getCountRooms();
        square-=it.get().getSquare();
        it.remove();
    }

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

    public Floor set(int index,@NotNull Floor Floor){
        if(index < 0 || index >= FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(FloorList.get(index).MyListIterator(0));
        FloorList.set(index, Floor);
        RecalculateFloorIncr(FloorList.get(index).MyListIterator(0));
        return FloorList.get(index);
    }

    public Floor get(int index){
        if(index < 0 || index >= FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        return FloorList.get(index);
    }

    public MyArrayList<Floor> getSpaceList() { return FloorList; }

    public int size() { return FloorList.size(); }

    public int getCountRooms(){ return countRooms; }

    public int getCountSpace(){
        if(this.FloorList == null)
            throw new FloorIndexOutOfBoundsException();
        int tmp = 0;
        for(int i = 0;i < this.FloorList.size(); i++) {
            tmp += this.FloorList.get(i).size();
        }
        return tmp;
    }

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

    public double getSquare() { return square; }

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

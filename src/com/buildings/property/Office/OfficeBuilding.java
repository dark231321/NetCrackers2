package com.buildings.property.Office;

import com.buildings.Container.MyLinkedList;
import com.buildings.Container.ListIterator;
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

public class OfficeBuilding implements Building {
    private MyLinkedList<Floor> FloorList;
    private int countRooms = 0;
    private double square = 0.0;

    public OfficeBuilding(int countRooms, int[] countFloor){
        if(countFloor.length == 0)
            throw new SpaceIndexOutOfBoundsException();
        FloorList = new MyLinkedList<>();
        for(int i =0; i < countRooms; i++){
            FloorList.add(new OfficeFloor(countFloor[i]));
        }
        getCalculation();
    }

    public OfficeBuilding(Floor[] floors) {
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

    @Nullable
    private ListIterator<Space> findOffice(int numberOffice){
        int tmp = numberOffice;
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
        it.set((Office) Office);
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
    public Floor set(int index, Floor Floor){
        if(index<0 || index>this.FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(FloorList.get(index).myListIterator(0));
        FloorList.set(index,(Floor) Floor);
        RecalculateFloorIncr(FloorList.get(index).myListIterator(0));
        return FloorList.get(index);
    }

    @Override
    public Floor get(int index){
        if(index<0 || index>this.FloorList.size())
            throw new FloorIndexOutOfBoundsException();
        return FloorList.get(index);
    }

    @Override
    public MyLinkedList<Floor> getSpaceList() { return FloorList; }

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
        for(Floor floor:
            FloorList){
            clone.FloorList.add((Floor) floor.clone());
        }
        return clone;
    }

}

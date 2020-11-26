package com.buildings.property.Office;

import com.buildings.Container.ListIterator;
import com.buildings.Container.MyLinkedList;
import com.buildings.property.util.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.property.util.Exceptions.InvalidRoomsCountException;
import com.buildings.property.util.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Floor;
import com.buildings.property.Space;

import java.util.Iterator;


public class OfficeFloor implements Floor {
    private MyLinkedList<Space> SpaceList;

    private int countRooms = 0;
    private double square = 0.0;

    public OfficeFloor(int size) {
        if(size < 0)
            throw new FloorIndexOutOfBoundsException();

        this.SpaceList = new MyLinkedList<>();
        for(int i = 0; i < size; i++)
            SpaceList.add(new Office());
        getCalculation();
    }

    @Override
    public Iterator<Space> iterator() {
        return SpaceList.iterator();
    }

    @Override
    public ListIterator<Space> myListIterator(int index){
        return SpaceList.ListIterator(index);
    }

    public OfficeFloor(Space... spaces){
        this.SpaceList = new MyLinkedList<>(spaces);
        getCalculation();
    }

    @Override
    public int size() {
        return this.SpaceList.size();
    }

    private void getCalculation(){
        for (com.buildings.property.Space Space : SpaceList) {
            countRooms += Space.getCountRooms();
            square += Space.getSquare();
        }
    }

    @Override
    public int getCountRooms() {
        if(countRooms < 0)
            throw new InvalidRoomsCountException();
        return countRooms;
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        OfficeFloor clone = (OfficeFloor) super.clone();
        clone.SpaceList = new MyLinkedList<>();
        for(Space space: SpaceList){
            clone.SpaceList.add((Space) space.clone());
        }
        return clone;
    }

    @Override
    public double getSquare(){
        if(square < 0)
            throw new InvalidSpaceAreaException();
        return square;
    }

    @Override
    public Space get(int index) {
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        return SpaceList.get(index);
    }

    @Override
    public Space set(int index, Space Space){
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        Space tmp = SpaceList.ListIterator(index).next();
        this.countRooms += Space.getCountRooms()  - tmp.getCountRooms();
        this.square += Space.getSquare() - tmp.getSquare();
        SpaceList.set(index, Space);
        return Space;
    }

    @Override
    public Space setRooms(int index, int newCountRooms) {
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        ListIterator<Space> it = SpaceList.ListIterator(index);
        it.next().setCountRooms(newCountRooms);
        return SpaceList.get(index);
    }

    @Override
    public MyLinkedList<Space> getSpaceList() {
        return SpaceList;
    }

    @Override
    public boolean remove(int index){
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        Space tmp = SpaceList.ListIterator(index).get();
        this.square -= tmp.getSquare();
        this.countRooms -= tmp.getCountRooms();
        return SpaceList.remove(index);
    }

    @Override
    public double getBestSpace(){
        double tmp = 0; double space;
        for(var it: this.SpaceList){
            if(tmp < (space = it.getSquare()))
                tmp = space;
        }
        return tmp;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "CountRooms= " + countRooms +
                ", SpaceList=" + SpaceList.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfficeFloor that = (OfficeFloor) o;
        return countRooms == that.countRooms &&
                Double.compare(that.square, square) == 0 &&
                SpaceList.equals(that.SpaceList);
    }

    @Override
    public int hashCode() {
        return countRooms ^ this.SpaceList.hashCode();
    }

    @Override
    public int compareTo(Floor spaces) {
        return Integer.compare(this.size(), spaces.size());
    }
}

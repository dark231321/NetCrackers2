package com.buildings.property.Dwelling;

import com.buildings.Container.ListIterator;
import com.buildings.Container.MyArrayList;
import com.buildings.property.util.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.property.util.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.util.Exceptions.SpaceIndexOutOfBoundsException;
import com.buildings.property.Floor;
import com.buildings.property.Space;

import java.util.Iterator;

public class DwellingFloor implements Floor {
    private MyArrayList<Space> SpaceList;
    private int countRooms = 0;
    private double square = 0.0;

    public DwellingFloor(int size) {
        if(size < 0)
            throw new FloorIndexOutOfBoundsException();

        this.SpaceList = new MyArrayList<>(size);
        for(int i = 0; i < size; i++)
            SpaceList.add(new Flat());
        getCalculation();
    }

    @Override
    public Iterator<Space> iterator() {
        return SpaceList.iterator();
    }

    @Override
    public ListIterator<Space> myListIterator(int index){
        if(index < 0 || index >= this.SpaceList.size())
            throw new SpaceIndexOutOfBoundsException();
        return SpaceList.listIterator(index);
    }

    public DwellingFloor(Space... spaces){
        this.SpaceList = new MyArrayList<>(spaces);
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
            throw new SpaceIndexOutOfBoundsException();
        return countRooms;
    }

    @Override
    public Object clone()
            throws CloneNotSupportedException {
        DwellingFloor clone = (DwellingFloor)super.clone();
        clone.SpaceList = new MyArrayList<>();
        for (Space space: this.SpaceList){
            clone.SpaceList.add((Space)space.clone());
        }
        return clone;
    }

    @Override
    public double getSquare(){
        if(countRooms < 0)
            throw new InvalidSpaceAreaException();
        return square;
    }

    @Override
    public Space get(int index) {
        if(index < 0 || index >= this.SpaceList.size())
            throw new SpaceIndexOutOfBoundsException();
        return SpaceList.get(index);
    }

    @Override
    public Space set(int index, Space Space){
        if(index < 0 || index >= this.SpaceList.size())
            throw new SpaceIndexOutOfBoundsException();
        Space tmp = SpaceList.listIterator(index).next();
        this.countRooms += Space.getCountRooms()  - tmp.getCountRooms();
        this.square += Space.getSquare() - tmp.getSquare();
        SpaceList.set(index, Space);
        return Space;
    }

    @Override
    public Space setRooms(int index, int newCountRooms) {
        if(index < 0 || index >= this.SpaceList.size())
            throw new SpaceIndexOutOfBoundsException();
        ListIterator<Space> it = SpaceList.listIterator(index);
        it.next().setCountRooms(newCountRooms);
        return SpaceList.get(index);
    }

    @Override
    public MyArrayList<Space> getSpaceList() {
        return SpaceList;
    }

    @Override
    public boolean remove(int index){
        if(index < 0 || index >= this.SpaceList.size())
            throw new SpaceIndexOutOfBoundsException();
        Space tmp = SpaceList.listIterator(index).next();
        this.square -= tmp.getSquare();
        this.countRooms -= tmp.getCountRooms();
        return SpaceList.remove(index);
    }

    @Override
    public double getBestSpace(){
        Iterator<Space> it = SpaceList.iterator();
        double tmp = 0;
        while (it.hasNext()){
            Space Space = it.next();
            if(tmp < Space.getSquare())
                tmp = Space.getSquare();
        }
        return tmp;
    }

    @Override
    public String toString() {
        return "DwellingFloor{" +
                "CountRooms= " + countRooms +
                ", SpaceList=" + SpaceList.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DwellingFloor that = (DwellingFloor) o;
        return  countRooms == that.countRooms &&
                Double.compare(that.square, square) == 0 &&
                SpaceList.equals(that.SpaceList);
    }

    @Override
    public int hashCode() {
        return countRooms ^ this.SpaceList.hashCode();
    }

    @Override
    public int compareTo(Floor spaces) {
        return Integer.compare(spaces.size(), this.size());
    }
}

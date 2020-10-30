package com.buildings.property.Office;

import com.buildings.Container.MyLinkedList;
import com.buildings.Container.ListIterator;
import com.buildings.property.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.property.Exceptions.InvalidRoomsCountException;
import com.buildings.property.Exceptions.InvalidSpaceAreaException;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;

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

    public OfficeFloor(){}

    @Override
    public Iterator<Space> iterator() {
        return SpaceList.iterator();
    }

    public ListIterator<Space> myListIterator(int index){
        return SpaceList.ListIterator(index);
    }

    public OfficeFloor(MyLinkedList<Space> SpaceList){
        this.SpaceList = SpaceList;
        getCalculation();
    }

    public int size() {
        return this.SpaceList.size();
    }

    private void getCalculation(){
        Iterator<Space> it = SpaceList.iterator();
        while (it.hasNext()){
            Space Space = it.next();
            countRooms += Space.getCountRooms();
            square     += Space.getSquare();
        }
    }

    public int getCountRooms() {
        if(countRooms < 0)
            throw new InvalidRoomsCountException();
        return countRooms;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object clone()
            throws CloneNotSupportedException {
        OfficeFloor clone = (OfficeFloor) super.clone();
        clone.SpaceList = new MyLinkedList<>();
        for(Space space:
                SpaceList){
            clone.SpaceList.add((Space) space.clone());
        }
        return clone;
    }


    public double getSquare(){
        if(square < 0)
            throw new InvalidSpaceAreaException();
        return square;
    }

    public Space get(int index) {
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        return SpaceList.get(index);
    }

    public Space set(int index, @NotNull Space Space){
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        Space tmp = SpaceList.ListIterator(index).next();
        this.countRooms += Space.getCountRooms()  - tmp.getCountRooms();
        this.square += Space.getSquare() - tmp.getSquare();
        SpaceList.set(index, Space);
        return Space;
    }

    public Space setRooms(int index, int newCountRooms) {
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        ListIterator<Space> it = SpaceList.ListIterator(index);
        it.next().setCountRooms(newCountRooms);
        return SpaceList.get(index);
    }

    public MyLinkedList<Space> getSpaceList() {
        return SpaceList;
    }

    public boolean remove(int index){
        if(index < 0 || index >= this.SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        Space tmp = SpaceList.ListIterator(index).get();
        this.square -= tmp.getSquare();
        this.countRooms -= tmp.getCountRooms();
        return SpaceList.remove(index);
    }

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
    public int compareTo(@NotNull Floor spaces) {
        return Integer.compare(this.size(), spaces.size());
    }
}

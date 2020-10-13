package com.buildings.property.build;

import com.buildings.Container.MyArrayList;
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

public class Dwelling implements Building {
    private MyArrayList<Floor> SpaceList;
    private static MyArrayList<Floor> Space_LIST;
    private int countRooms = 0;
    private double square = 0.0;

    public Dwelling(@NotNull MyArrayList<Integer> countFloor){
        if(countFloor.size() < 0)
            throw new IllegalArgumentException();
        SpaceList = new MyArrayList<>(countFloor.size());
        for(int i =0; i < countFloor.size(); i++){
           SpaceList.set(i, new DwellingFloor(countFloor.get(i)));
        }
        getCalculation();
    }

    private Dwelling() {
        if(Space_LIST == null)
            throw new IllegalArgumentException();
        this.SpaceList = Space_LIST;
        Space_LIST = null;
        getCalculation();
    }

    @NotNull
    public static Dwelling ofDwelling(MyArrayList<Floor> SpaceList) {
        Objects.requireNonNull(SpaceList);
        Space_LIST = SpaceList;
        return new Dwelling();
    }

    private void getCalculation(){
        MyIterator<Floor> it = SpaceList.iterator();
        while (it.hasNext()){
            Floor Floor = it.next();
            countRooms += Floor.getCountRooms();
            square     += Floor.getSquare();
        }
    }

    @Nullable
    private MyListIterator<Space> findSpace(int numberSpace){
        int tmp = numberSpace;
        for(int i = 0; i < this.SpaceList.size(); i++){
            if(tmp >= this.SpaceList.get(i).size())
                tmp -= this.SpaceList.get(i).size();
            else
                return this.SpaceList.get(i).MyListIterator(tmp);
        }
        return null;
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
        it.set((Space) Space);
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

    public Floor set(int index,@NotNull Floor Floor){
        if(index < 0 || index >= SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(SpaceList.get(index).MyListIterator(0));
        SpaceList.set(index,(Floor) Floor);
        RecalculateFloorIncr(SpaceList.get(index).MyListIterator(0));
        return SpaceList.get(index);
    }

    public Floor get(int index){
        if(index < 0 || index >= SpaceList.size())
            throw new FloorIndexOutOfBoundsException();
        return SpaceList.get(index);
    }

    public MyArrayList<Floor> getSpaceList() { return SpaceList; }

    public int size() { return SpaceList.size(); }

    public int getCountRooms(){ return countRooms; }

    public int getCountSpace(){
        if(this.SpaceList == null)
            throw new FloorIndexOutOfBoundsException();
        int tmp = 0;
        for(int i = 0;i < this.SpaceList.size(); i++) {
            tmp += this.SpaceList.get(i).size();
        }
        return tmp;
    }

    public double getBestSpace() {
        if(this.SpaceList == null)
            throw new FloorIndexOutOfBoundsException();
        double tmp = this.SpaceList.get(0).getBestSpace();
        for(int i = 0; i < SpaceList.size(); i++){
            if(tmp < SpaceList.get(i).getBestSpace())
                tmp = SpaceList.get(i).getBestSpace();
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
                "Count Floors = " + SpaceList.size() +
                ", SpaceList = " + SpaceList.toString() +
                '}';
    }
}

package com.buildings.build;

import com.buildings.Container.MyArrayList;
import com.buildings.Container.MyIterator;
import com.buildings.Container.MyListIterator;
import com.buildings.Exceptions.FloorIndexOutOfBoundsException;
import com.buildings.Exceptions.SpaceIndexOutOfBoundsException;
import com.buildings.property.Building;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.Objects;

public class Dwelling implements Building {
    private MyArrayList<DwellingFloor> flatList;
    private static MyArrayList<DwellingFloor> FLAT_LIST;
    private int countRooms = 0;
    private double square = 0.0;

    public Dwelling(@NotNull MyArrayList<Integer> countFloor){
        if(countFloor.size() < 0)
            throw new IllegalArgumentException();
        flatList = new MyArrayList<>(countFloor.size());
        for(int i =0; i < countFloor.size(); i++){
           flatList.set(i, new DwellingFloor(countFloor.get(i)));
        }
        getCalculation();
    }

    private Dwelling() {
        if(FLAT_LIST == null)
            throw new IllegalArgumentException();
        this.flatList = FLAT_LIST;
        FLAT_LIST = null;
        getCalculation();
    }

    @NotNull
    public static Dwelling ofDwelling(MyArrayList<DwellingFloor> flatList) {
        Objects.requireNonNull(flatList);
        FLAT_LIST = flatList;
        return new Dwelling();
    }

    private void getCalculation(){
        MyIterator<DwellingFloor> it = flatList.iterator();
        while (it.hasNext()){
            DwellingFloor dwellingFloor = it.next();
            countRooms += dwellingFloor.getCountRooms();
            square     += dwellingFloor.getSquare();
        }
    }

    @Nullable
    private MyListIterator<Flat> findFlat(int numberFlat){
        int tmp = numberFlat;
        for(int i = 0; i < this.flatList.size(); i++){
            if(tmp >= this.flatList.get(i).size())
                tmp -= this.flatList.get(i).size();
            else
                return this.flatList.get(i).MyListIterator(tmp);
        }
        return null;
    }

    public Flat getSpace(int numberFlat){
        var it = findFlat(numberFlat);
        if (it == null)
            throw new SpaceIndexOutOfBoundsException();
        return it.get();
    }

    public void removeSpace(int numberFlat){
        var it = findFlat(numberFlat);
        if (it == null)
            throw new SpaceIndexOutOfBoundsException();
        countRooms-=it.get().getCountRooms();
        square-=it.get().getSquare();
        it.remove();
    }

    public void setSpace(int numberFlat, @NotNull Space flat){
        var it = findFlat(numberFlat);
        if(it == null)
            throw new SpaceIndexOutOfBoundsException();
        countRooms += flat.getCountRooms() - it.get().getCountRooms();
        square += flat.getSquare() - it.get().getSquare();
        it.set((Flat) flat);
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

    public DwellingFloor set(int index,@NotNull Floor dwellingFloor){
        if(index < 0 || index >= flatList.size())
            throw new FloorIndexOutOfBoundsException();
        RecalculateFloorDecr(flatList.get(index).MyListIterator(0));
        flatList.set(index,(DwellingFloor) dwellingFloor);
        RecalculateFloorIncr(flatList.get(index).MyListIterator(0));
        return flatList.get(index);
    }

    public DwellingFloor get(int index){
        if(index < 0 || index >= flatList.size())
            throw new FloorIndexOutOfBoundsException();
        return flatList.get(index);
    }

    public MyArrayList<DwellingFloor> getSpaceList() { return flatList; }

    public int size() { return flatList.size(); }

    public int getCountRooms(){ return countRooms; }

    public int getCountSpace(){
        if(this.flatList == null)
            throw new FloorIndexOutOfBoundsException();
        int tmp = 0;
        for(int i = 0;i < this.flatList.size(); i++) {
            tmp += this.flatList.get(i).size();
        }
        return tmp;
    }

    public double getBestSpace() {
        if(this.flatList == null)
            throw new FloorIndexOutOfBoundsException();
        double tmp = this.flatList.get(0).getBestSpace();
        for(int i = 0; i < flatList.size(); i++){
            if(tmp < flatList.get(i).getBestSpace())
                tmp = flatList.get(i).getBestSpace();
        }
        return tmp;
    }

    public double getSquare() { return square; }

    public MyArrayList<Flat> sortedFlat(){
        int countFlats = this.getCountSpace();
        MyArrayList<Flat> tmp = new MyArrayList<>();
        for(int i = 0; i < countFlats; i++) {
            var it = this.getSpace(i);
            tmp.add(it);
        }
        Comparator<Flat> comparator = Comparator.comparing(Flat::getSquare);
        tmp.sort(comparator);
        return tmp;
    }
}

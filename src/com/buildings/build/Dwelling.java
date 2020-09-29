package com.buildings.build;

import com.buildings.Container.MyArrayList;
import com.buildings.Container.MyIterator;
import com.buildings.Container.MyListIterator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Dwelling {
    private MyArrayList<DwellingFloor> flatList;
    private static MyArrayList<DwellingFloor> FLAT_LIST;
    private int countRooms = 0;
    private double square = 0.0;

    public Dwelling(MyArrayList<Integer> countFloor){
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
                return this.flatList.get(i).flatMyListIterator(tmp);
        }
        return null;
    }

    public Flat getFlat(int numberFlat){
        var it = findFlat(numberFlat);
        if (it == null)
            throw new IllegalArgumentException();
        return it.get();
    }

    public boolean removeFlat(int numberFlat){
        var it = findFlat(numberFlat);
        if (it == null)
            throw new IllegalArgumentException();
        countRooms-=it.get().getCountRooms();
        square-=it.get().getSquare();
        it.remove();
        return true;
    }

    public boolean setFlat(int numberFlat, Flat flat){
        var it = findFlat(numberFlat);
        if(it == null)
            throw new IllegalArgumentException();
        countRooms += flat.getCountRooms() - it.get().getCountRooms();
        square += flat.getSquare() - it.get().getSquare();
        it.set(flat);
        return true;
    }

    public DwellingFloor set(int index, DwellingFloor dwellingFloor){
        flatList.set(index, dwellingFloor);
        getCalculation();
        return flatList.get(index);
    }

    public DwellingFloor get(int index){ return flatList.get(index);}

    public MyArrayList<DwellingFloor> getFlatList() { return flatList; }

    public int size() { return flatList.size(); }

    public int getCountRooms(){ return countRooms; }

    public int getCountFlats(){
        if(this.flatList == null)
            throw new NoSuchElementException();
        int tmp = 0;
        for(int i = 0;i < this.flatList.size(); i++) {
            tmp += this.flatList.get(i).size();
        }
        return tmp;
    }

    public double getBestSpace() {
        if(this.flatList == null)
            throw new NoSuchElementException();
        double tmp = this.flatList.get(0).getBestSpace();
        for(int i = 0; i < flatList.size(); i++){
            if(tmp < flatList.get(i).getBestSpace())
                tmp = flatList.get(i).getBestSpace();
        }
        return tmp;
    }

    public double getSquare() { return square; }

    public MyArrayList<Flat> sortedFlat(){
        int countFlats = this.getCountFlats();
        MyArrayList<Flat> tmp = new MyArrayList<>(countFlats);
        for(int i = 0; i < countFlats; i++) {
            var it = this.getFlat(i);
            tmp.add(it);
        }
        Comparator<Flat> comparator = Comparator.comparing(Flat::getSquare);
        tmp.sort(comparator);
        return tmp;
    }
}

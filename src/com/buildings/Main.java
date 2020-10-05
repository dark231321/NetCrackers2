package com.buildings;

import com.buildings.Container.MyArrayList;
import com.buildings.Container.MyLinkedList;
import com.buildings.Container.MyListIterator;
import com.buildings.build.Dwelling;
import com.buildings.build.DwellingFloor;
import com.buildings.build.Flat;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for(int i = -200;i <= 0; i++){
            list.add(i);
        }
        list.sort((integer, t1) -> t1);
        for(int i = 0;i < 200; i++){
            System.out.print(list.get(i));
            System.out.print(" ");
        }


        list.clear();
        LinkedList<Integer> g;
        boolean a = false;
        boolean b = true;
        MyArrayList<DwellingFloor> myArrayList = new MyArrayList<>();
        MyListIterator<DwellingFloor> it1 = myArrayList.iterator();
        for(int j=0;j<2;j++) {
            MyArrayList<Flat> flatList = new MyArrayList<>();
            for (int i = 0; i < 4; i++) {
                if(i == 0)
                    flatList.add(Flat.of());
                else
                    flatList.add(Flat.of(i, i * 10));
            }
            myArrayList.set(j,new DwellingFloor(flatList));
        }

        Dwelling dwelling = Dwelling.ofDwelling(myArrayList);

        dwelling.removeFlat(1);
        dwelling.setFlat(2,Flat.of());
        var res = dwelling.sortedFlat();
        for(int i=0;i < res.size(); i++) {
            System.out.print(res.get(i).getSquare() + " ");
        }
    }

}

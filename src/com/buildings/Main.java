package com.buildings;

import com.buildings.Container.MyArrayList;
import com.buildings.Container.MyLinkedList;
import com.buildings.Container.MyListIterator;
import com.buildings.build.Dwelling;
import com.buildings.build.DwellingFloor;
import com.buildings.build.Flat;

public class Main {

    public static void main(String[] args) {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        for(int i = 0;i <= 10; i++){
            list.add(i);
        }
        System.out.println(list.size() / 2);
        list.add(4,100);
        MyListIterator<Integer> it = list.iterator();
        while (it.hasNext())
            System.out.println(it.next());
        for(int i = 0;i < 100; i++){
            var tmp = list.find(i);
            System.out.println(tmp);
        }

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

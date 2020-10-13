package com.buildings;
import com.buildings.Container.MyArrayList;
import com.buildings.Test.BuildTest;
import com.buildings.Test.OfficeTest;

public class Main {
    public static void main(String[] args) {
        MyArrayList<MyArrayList<Integer>> myArrayList = new MyArrayList<>(10);
        MyArrayList<MyArrayList<Integer>> myArrayList1 = new MyArrayList<>(10);
        for(int i = 0; i<3 ;i++){
            MyArrayList<Integer> tmp = new MyArrayList<>();
            MyArrayList<Integer> tmp1 = new MyArrayList<>();
            for(int j = 0; j<3 ;j++){
                tmp.add(j);
                tmp1.add(j*2);
            }
            myArrayList.add(tmp);
            myArrayList1.add(tmp1);
        }
        var tmp1 = myArrayList.equals(myArrayList1);
        System.out.println(myArrayList.equals(myArrayList1));
        for(int i = 0; i<3 ;i++){
            for(int j = 0; j<3 ;j++){
                System.out.print(myArrayList.get(i).get(j) + " ");
            }
            System.out.println();
        }
        for(int i = 0; i<3 ;i++){
            for(int j = 0; j<3 ;j++){
                System.out.print(myArrayList1.get(i).get(j) + " ");
            }
            System.out.println();
        }
        //System.out.println(80>>>16);
        //BuildTest.start();
        //OfficeTest.start();
    }
}

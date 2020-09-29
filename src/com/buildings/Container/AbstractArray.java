package com.buildings.Container;

import java.util.Objects;

public abstract class AbstractArray<T> implements MyCollection<T> {
    //положительные числа int (2^32)
    private static final int MAX_SIZE_ARRAY = 2147483639;


    public abstract MyIterator<T> iterator();

    public boolean isEmpty() {
        return this.size()==0;
    }

    public void clear() {
        MyIterator<T> it = iterator();

        while (it.hasNext()) {
            it.remove();
            it.next();
        }
    }

    public abstract int size();

    public boolean add(T e) {
        throw new UnsupportedOperationException();
    }

    public boolean contains(T value) {
        MyIterator<T> it = this.iterator();
        while (it.hasNext()){
            if(Objects.equals(it.next(), value))
                return true;
        }
        return false;
    }

    public boolean clear(T value) {
       MyIterator<T> it = iterator();
       while(it.hasNext()) {
           if(it.next() == value) {
               it.remove();
               return true;
           }
       }
       return false;
    }
}

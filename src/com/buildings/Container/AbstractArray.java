package com.buildings.Container;
import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

public abstract class AbstractArray<T> implements MyCollection<T> {

    @NotNull
    public abstract Iterator<T> iterator();

    public boolean isEmpty() {
        return this.size()==0;
    }

    public void clear() {
        Iterator<T> it = iterator();

        while (it.hasNext()) {
            it.remove();
            it.next();
        }
    }

    public abstract void sort(Comparator<? super T> pred);

    public abstract int size();

    public boolean add(T e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(T value) {
        for (T t : this) {
            if (Objects.equals(t, value))
                return true;
        }
        return false;
    }

    public boolean clear(T value) {
       Iterator<T> it = iterator();
       while(it.hasNext()) {
           if(it.next() == value) {
               it.remove();
               return true;
           }
       }
       return false;
    }
}

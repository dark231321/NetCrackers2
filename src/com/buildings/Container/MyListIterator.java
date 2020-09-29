package com.buildings.Container;

public interface MyListIterator<T> extends MyIterator<T> {
    boolean hasNext();

    boolean hasPrevious();

    T next();
    T get();
    T previous();

    void remove();
    void set(T var1);
    void add(T var2);
}

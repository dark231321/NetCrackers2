package com.buildings.Container;

import java.util.Iterator;

public interface ListIterator<T> extends Iterator<T> {
    boolean hasNext();

    boolean hasPrevious();

    T next();
    T get();
    T previous();

    void remove();
    void set(T var1);
    void add(T var2);
}

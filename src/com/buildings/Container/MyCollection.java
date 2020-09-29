package com.buildings.Container;

public interface MyCollection<T> {
    //итератор в контейнерах
    MyIterator<T> iterator();

    boolean add(T value);
    boolean remove(T value);
    boolean contains(T value);
    int size();
    boolean isEmpty();
    void clear();

}

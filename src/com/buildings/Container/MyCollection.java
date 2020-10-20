package com.buildings.Container;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public interface MyCollection<T> extends Iterable<T> {

    @NotNull Iterator<T> iterator();

    boolean add(T value);

    boolean remove(T value);

    boolean contains(T value);

    int size();

    boolean isEmpty();

    void clear();

}

package com.buildings.Container;

public interface MyIterator<E> {

    boolean hasNext();

    E next();

    default void remove(){
        throw new UnsupportedOperationException("remove");
    }
}

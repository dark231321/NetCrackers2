package com.buildings.Container;

public interface MyIterator<E> {

    //имеет ли контейнер следующий элемен
    boolean hasNext();

    //возвращает следуюший элемент в контейнере
    E next();

    //удалеяет элемент
    default void remove(){
        throw new UnsupportedOperationException("remove");
    }
}

package com.buildings.Container;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> extends AbstractArray<T> {

    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;

    private int size;

    public MyLinkedList() { this.size = 0; }

    @Override
    public MyListIterator<T> iterator() {
        return new LnkItr();
    }


    public boolean add(int index, T e){
        try {
            LnkItr it = new LnkItr(index);
            addAfter(it.getNode(), e);
        } catch (Exception ex) {
            throw new ConcurrentModificationException();
        }
        return true;
    }

    @Nullable
    @Contract(pure = true)
    private MyLinkedList.Node<T> search(T element){
        for(var it = new MyLinkedList<T>.LnkItr(); it.hasNext(); it.next()){
            if(Objects.equals(element, it.get()))
                return it.getNode();
        }
        return null;
    }

    public boolean find(T element){
        return search(element) != null;
    }

    @NotNull
    private MyLinkedList.Node<T> addAfter(MyLinkedList.Node<T> element, T value){
        if(element == first) {
            addFirst(value);
            return first;
        }

        if (element == last) {
            addLast(value);
            return last;
        }

        MyLinkedList.Node<T> tmp = new MyLinkedList.Node<T>(element.next, element, value);
        element.next = tmp;
        element.next.prev = tmp;
        this.size++;
        return tmp;
    }

    public MyLinkedList.Node<T> addAfter(T element, T value){
        MyLinkedList.Node<T> tmp = search(element);
        if(tmp == null)
            if(this.first == null) {
                addFirst(value);
                return this.first;
            }
            else
                return null;
        else
            return addAfter(tmp, value);
    }

    private void addFirst(T e){
        MyLinkedList.Node<T> tmp = new MyLinkedList.Node<>(null, first, e);
        if(first == null)
            last = tmp;
        else
            first.next = tmp;
        first = tmp;
        this.size++;
    }

    private void addLast(T e){
        MyLinkedList.Node<T> tmp = new MyLinkedList.Node<>(last, null, e);
        if(last == null)
            first = tmp;
        else
            last.prev = tmp;
        last = tmp;
        this.size++;
    }
    @Override
    public boolean add(T e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean remove(T value) {
        return false;
    }

    @Override
    public int size() { return size; }

    private static class Node<T> {
        MyLinkedList.Node<T> next;
        MyLinkedList.Node<T> prev;
        T value;

        Node(MyLinkedList.Node<T> next,
             MyLinkedList.Node<T> prev,
             T value){
            this.next = next;
            this.prev = prev;
            this.value = value;
        }
    }

    private class LnkItr implements MyListIterator<T> {
        int currentIndex;
        MyLinkedList.Node<T> currentElement;

        LnkItr(int index) {
            if(MyLinkedList.this.size <= index)
                throw new NoSuchElementException();
            if(index == 0)
                this.currentElement = MyLinkedList.this.last;
            else
                if (index == MyLinkedList.this.size() - 1)
                    this.currentElement = MyLinkedList.this.first;
                else
                    move(index - 1);
        }

        private void move(int index){
            if(MyLinkedList.this.size / 2 < index)
                moveBack(index, MyLinkedList.this.first);
            else
                moveForward(index, MyLinkedList.this.last);
        }

        private void moveBack(int index, MyLinkedList.Node<T> position) {
            this.currentElement = position;
            this.currentIndex = MyLinkedList.this.size;
            while(currentIndex != index)
                this.previous();

        }

        private void moveForward(int index, MyLinkedList.Node<T> position) {
            this.currentElement = position;
            while(currentIndex != index)
                this.next();
        }
        LnkItr(){
            currentIndex = 0;
            currentElement = MyLinkedList.this.last;
        }

        @Override
        public boolean hasNext() {
            return currentElement != null;
        }

        @Override
        public T next() {
            if(!this.hasNext())
                throw new NoSuchElementException();
            T val = currentElement.value;
            currentElement = currentElement.next;
            this.currentIndex++;
            return val;
        }

        @Override
        public T get() {
            return currentElement.value;
        }

        private MyLinkedList.Node<T> getNode(){
            return currentElement;
        }
        @Override
        public boolean hasPrevious() {
            return currentElement != null;
        }

        @Override
        public T previous() {
            if(!this.hasPrevious())
                throw new NoSuchElementException();
            T val = currentElement.value;
            currentElement = currentElement.prev;
            this.currentIndex--;
            return val;
        }

        @Override
        public void remove() {
            try{
                //toDo remove
            }catch (Exception ex) {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void set(T t) {
            currentElement.value = t;
        }

        @Override
        public void add(T t) {
            try {
                MyLinkedList.this.addAfter(currentElement, t);
            } catch (Exception ex) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

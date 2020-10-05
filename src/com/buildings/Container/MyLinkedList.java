package com.buildings.Container;
import com.buildings.Container.Alghorithms.ArraysMethods;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<T> extends AbstractArray<T> {

    private MyLinkedList.Node<T> first;
    private MyLinkedList.Node<T> last;
    private int size;

    public MyLinkedList() { this.size = 0; }

    public void set(int index, T value){
        var it = new LnkItr(index);
        it.set(value);
    }

    private void removeLast(){
        if(this.size == 1)
            last = first = null;
        else{
            last = last.next;
            last.prev = null;
        }
            this.size--;
    }

    private void removeFirst(){
        if(this.size == 1)
            last = first = null;
        else{
            first = first.prev;
            first.next = null;
        }
            this.size--;
    }

    private void remove(MyLinkedList.Node<T> element){
        if(element == last)
            removeLast();
        else if(element == first)
                removeFirst();
             else{
                 element.prev.next = element.next;
                 element.next.prev = element.prev;
                 this.size--;
            }
    }

    public T get(int index){
        var it = new LnkItr(index);
        return it.get();
    }

    public boolean remove(int index){
        var it = new LnkItr(index);
        remove(it.getNode());
        return true;
    }
    @Override
    public MyListIterator<T> iterator() {
        return new LnkItr();
    }

    public MyListIterator<T> iterator(int index){
        return new LnkItr(index);
    }

    public void add(int index, T e){
        try {
            LnkItr it = new LnkItr(index);
            addAfter(it.getNode(), e);
        } catch (Exception ex) {
            throw new ConcurrentModificationException();
        }
    }

    public void clear() {
        MyListIterator<T> it = new LnkItr(0);
        while(it.hasNext()){
            it.remove();
        }
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
        var tmp = search(value);
        if(tmp == null)
            return false;
        remove(tmp);
        return true;
    }

    @Override
    public int size() { return size; }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> pred){
        var it = new LnkItr();
        int size = this.size();
        T[] tmp = (T[]) new Object[this.size()];
        for(int i = 0; i< size; i++){
            tmp[i] = it.next();
        }
        ArraysMethods.sort(tmp,0, size-1, pred);
        this.clear();
        for(int i = 0; i< size; i++){
            this.add(tmp[i]);
        }
    }


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
                    move(index);
        }

        private void move(int index){
            if(MyLinkedList.this.size / 2 < index)
                moveBack(index, MyLinkedList.this.first);
            else
                moveForward(index, MyLinkedList.this.last);
        }

        private void moveBack(int index, MyLinkedList.Node<T> position) {
            this.currentElement = position;
            this.currentIndex = MyLinkedList.this.size - 1;
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
                var tmp = this.currentElement;
                if(hasNext())
                    currentElement = currentElement.next;
                else if(hasPrevious())
                        this.previous();
                MyLinkedList.this.remove(tmp);
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

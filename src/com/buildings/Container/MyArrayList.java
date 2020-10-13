package com.buildings.Container;

import com.buildings.Container.Alghorithms.ArraysMethods;
import jdk.internal.vm.annotation.ForceInline;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Comparator;
import java.util.RandomAccess;

public class MyArrayList<T> extends AbstractArray<T> implements RandomAccess {

    private final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_DATA = new Object[0];
    private static final int MAX_SIZE = 2147483639;
    private Object[] data;
    private int size;
    private int capacity;

    public MyArrayList(int capacity) {
        if (capacity > 0) {
            data = new Object[this.capacity = Math.max(capacity * 2, DEFAULT_CAPACITY)];
        } else {
            if (capacity != 0)
                throw new IllegalArgumentException("Capacity negative" + capacity);
            this.data = EMPTY_DATA;
        }
        this.size = 0;
    }

    public MyArrayList() {
        this.size = 0;
        this.capacity = DEFAULT_CAPACITY;
        this.data = new Object[DEFAULT_CAPACITY];
    }
    // но я же могу вызвать метод ремув например дааааа но я могу прямо из коллекции что-то удалить через метод коллекции и там в библиотеках конкарент что-то там я видел и экспешны это вроде в атомиках где-то там
    public boolean set(int index, T value) {
        if (index < 0 || index > this.size())
            throw new IllegalArgumentException();
        if (index >= this.size())
            this.size++;
        this.data[index] = value;
        return true;
    }

    public boolean add(T value) {
        if (this.capacity() == this.size())
            realloc(this.capacity(), this.data);
        this.data[this.size++] = value;
        return true;
    }

    public boolean remove(T value) {
        int i = 0;
        while (true) {
            if (i >= size()) return false;
            if (Objects.equals(this.data[i], value))
                break;
            i++;
        }
        return remove(i);
    }

    public boolean remove(int index) {
        System.arraycopy(this.data, index + 1, this.data, index, size() - 1 - index);
        this.size -= 1;
        this.data[this.size] = null;
        return true;
    }

    @Override
    public String toString() {
        return ArraysMethods.toString(this.data, 0, this.size() - 1);
    }

    public MyListIterator<T> iterator() {
        return new MyArrayList<T>.ListItr(0);
    }

    public MyListIterator<T> iterator(int index) {
        return new MyArrayList<T>.ListItr(index);
    }

    @ForceInline
    public int size() {
        return size;
    }

    @ForceInline
    public int capacity() {
        return capacity;
    }

    private void realloc(int capacity, Object[] data) {
        if (capacity * 2 < MAX_SIZE) {
            this.data = copyOf(data);
            this.capacity *= 2;
        } else
            throw new OutOfMemoryError();
    }

    @NotNull
    private Object[] copyOf(Object[] data) {
        Object[] tmp = new Object[capacity * 2];
        if (size() >= 0) System.arraycopy(data, 0, tmp, 0, size());
        return tmp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArrayList<?> that = (MyArrayList<?>) o;
        return  size == that.size &&
                capacity == that.capacity &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(DEFAULT_CAPACITY, size, capacity);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (this.size() <= index)
            throw new NoSuchElementException();
        else
            return (T) this.data[index];
    }

    @SuppressWarnings("unchecked")
    public void sort(Comparator<? super T> pred){
        ArraysMethods.sort((T[])this.data,0, this.size() - 1, pred);
    }

    private class ListItr extends MyArrayList<T>.Itr implements MyListIterator<T> {
        ListItr(int index) {
            super(index);
        }


        public boolean hasNext() {
            return super.hasNext();
        }


        public boolean hasPrevious() {
            return this.current != 0;
        }

        @SuppressWarnings("unchecked")
        public T previous() {
            int i = this.current - 1;
            if (i < 0)
                throw new NoSuchElementException();
            else
                return (T) MyArrayList.this.data[this.current--];
        }

        @SuppressWarnings("unchecked")
        public T get(){
            return (T) MyArrayList.this.data[this.current];
        }

        public void set(T value) {
            MyArrayList.this.set(this.current, value);
        }

        public void add(T value) {
            MyArrayList.this.add(value);
        }
    }

    private class Itr implements MyIterator<T> {
        int current;
        final int last = -1;

        public Itr(int index) {
            this.current = index;
        }


        public boolean hasNext() {
            return this.current != MyArrayList.this.size();
        }

        @Override
        public void remove() {
            if (current > MyArrayList.this.size())
                throw new NoSuchElementException();
            MyArrayList.this.remove(current);
        }

        @SuppressWarnings("unchecked")
        public T next() {
            if (current > MyArrayList.this.size())
                throw new NoSuchElementException();
            return (T) MyArrayList.this.data[current++];
        }

    }
}

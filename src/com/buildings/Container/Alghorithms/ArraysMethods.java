package com.buildings.Container.Alghorithms;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Comparator;
import java.util.Objects;

public class ArraysMethods {
    private static void RangeCheck(int length, int fromIndex, int toIndex) {
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        if (length < toIndex)
            throw new IllegalArgumentException("length(" + length + ") < toIndex(" + toIndex + ")");
        if (toIndex < 0)
            throw new IllegalArgumentException("length(" + toIndex + ") " + " < 0");
        if (fromIndex < 0)
            throw new IllegalArgumentException("length(" + fromIndex + ") " +" < 0");
    }

    public static <T> void sort(@NotNull T[] a, int fromIndex, int toIndex, Comparator<? super T> c) {
        RangeCheck(a.length, fromIndex, toIndex);
        if(c == null){
            mergeSort(a,fromIndex, toIndex);
        } else {
            mergeSort(a,fromIndex, toIndex, c);
        }
    }

    public static<T> void mergeSort(T[] array, int low, int high){
        if (high <= low) return;

        int mid = (low+high)/2;
        mergeSort(array, low, mid);
        mergeSort(array, mid+1, high);
        merge(array, low, mid, high);
    }

    @SuppressWarnings("unchecked")
    public static void merge(Object[] array, int low, int mid, int high) {
        Object[] leftArray = new Object[mid - low + 1];
        Object[] rightArray = new Object[high - mid];

        for (int i = 0; i < leftArray.length; i++)
            leftArray[i] = array[low + i];

        for (int i = 0; i < rightArray.length; i++)
            rightArray[i] = array[mid + i + 1];

        int leftIndex = 0;
        int rightIndex = 0;
        for (int i = low; i < high + 1; i++) {
            if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
                if (((Comparable)leftArray[leftIndex]).compareTo(rightArray[rightIndex]) > 0) {
                    array[i] = leftArray[leftIndex++];
                } else {
                    array[i] = rightArray[rightIndex++];
                }
            } else if (leftIndex < leftArray.length) {
                array[i] = leftArray[leftIndex++];
            } else if (rightIndex < rightArray.length) {
                array[i] = rightArray[rightIndex++];
            }
        }
    }

    public static<T> void mergeSort(T[] array, int low, int high, Comparator<? super T> c) {
        if (high <= low) return;

        int mid = (low+high)/2;
        mergeSort(array, low, mid, c);
        mergeSort(array, mid+1, high, c);
        merge(array, low, mid, high, c);
    }

    @SuppressWarnings("unchecked")
    public static<T> void merge(T[] array, int low, int mid, int high, Comparator<T> c) {
        T[] leftArray = (T[]) new Object[mid - low + 1];
        T[] rightArray = (T[]) new Object[high - mid];

        for (int i = 0; i < leftArray.length; i++)
            leftArray[i] = array[low + i];

        for (int i = 0; i < rightArray.length; i++)
            rightArray[i] = array[mid + i + 1];

        int leftIndex = 0;
        int rightIndex = 0;

        for (int i = low; i < high + 1; i++) {
            if (leftIndex < leftArray.length && rightIndex < rightArray.length) {
                if (c.compare(leftArray[leftIndex], rightArray[rightIndex]) > 0) {
                    array[i] = leftArray[leftIndex++];
                } else {
                    array[i] = rightArray[rightIndex++];
                }
            } else if (leftIndex < leftArray.length) {
                array[i] = leftArray[leftIndex++];
            } else if (rightIndex < rightArray.length) {
                array[i] = rightArray[rightIndex++];
            }
        }

    }

    @NotNull public static String toString(@Nullable Object[] array, int low, int high){
        if(array == null)
            return "Null";
        if(array.length == 0)
            return "[]";

        RangeCheck(array.length, low, high);
        StringBuilder b = new StringBuilder();
        b.append("[");
        for(int i = 0;;i++){
            b.append(Objects.toString(array[i]));
            if(i == high)
                return b.append("]").toString();
            b.append(", ");
        }
    }

    static public boolean equals(Object[] o1, Object[] o2) {
        if (o1 != o2) {
            if (o1.length != o2.length)
                return false;
            for (int i = 0; i < o1.length; i++) {
                if (!o1[i].equals(o2[i]))
                    return false;
            }
        }
        return true;
    }

    static public int hashCode(Object[] o, int size) {
        if(o == null){
            return 0;
        } else {
            int result = 1;
            int s = o.length;
            for (int i = 0; i < size; i++) {
                Object object = o[i];
                result = result * 31 + (object == null ? 0 : object.hashCode());
            }
            return result;
        }
    }

    static public Object[] deepCopy(Object[] o1, int size){
        Object[] var = new Object[size];
        for(int i = 0; i < size; i ++){
            Object object = o1[i];
            var[i] = (object == null ? null :
                    object instanceof MyCloneable ? ((MyCloneable) object).clone() : object);
        }
        return var;
    }
}

package com.buildings.Container.Alghorithms;

import org.jetbrains.annotations.NotNull;
import java.util.Comparator;


public class ArraysMethods {
    private static void RangeCheck(int length, int fromIndex, int toIndex) {
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        if (length < toIndex)
            throw new IllegalArgumentException("length(" + length + ") < toIndex(" + toIndex + ")");
        if (toIndex < 0)
            throw new IllegalArgumentException("length(" + toIndex + ") " +" < 0");
        if (fromIndex < 0)
            throw new IllegalArgumentException("length(" + fromIndex + ") " +" < 0");
    }

    public static <T> void sort(@NotNull T[] a, int fromIndex, int toIndex, Comparator<? super T> c) {
        RangeCheck(a.length, fromIndex, toIndex);
        if(c == null){
            throw new IllegalArgumentException();
        } else {
            mergeSort(a,fromIndex, toIndex, c);
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
    public static<T> void merge(T[] array, int low, int mid, int high, Comparator c) {
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
}

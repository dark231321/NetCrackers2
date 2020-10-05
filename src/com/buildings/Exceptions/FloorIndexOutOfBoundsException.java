package com.buildings.Exceptions;

public class FloorIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public FloorIndexOutOfBoundsException() {

    }
    public FloorIndexOutOfBoundsException(String s){
        super(s);
    }

    public FloorIndexOutOfBoundsException(int index){
        super("Floor index out of range: " + index);
    }
}

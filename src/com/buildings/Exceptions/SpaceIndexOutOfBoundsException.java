package com.buildings.Exceptions;

public class SpaceIndexOutOfBoundsException extends IndexOutOfBoundsException {
    public SpaceIndexOutOfBoundsException() {

    }
    public SpaceIndexOutOfBoundsException(String s){
        super(s);
    }

    public SpaceIndexOutOfBoundsException(int index){
        super("Space index out of range: " + index);
    }
}

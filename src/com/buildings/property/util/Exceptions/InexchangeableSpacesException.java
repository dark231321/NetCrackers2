package com.buildings.property.util.Exceptions;

import com.buildings.property.Space;

public class InexchangeableSpacesException extends IllegalArgumentException{
    public InexchangeableSpacesException(){
        super("Incomparable spaces");
    }
    public InexchangeableSpacesException(Space first, Space second){
        super("Incomparable spaces: " +
                first.getCountRooms() + " "+ second.getCountRooms() +
                first.getSquare() + " " + second.getSquare());
    }
}

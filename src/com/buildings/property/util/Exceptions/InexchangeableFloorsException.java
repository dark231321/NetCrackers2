package com.buildings.property.util.Exceptions;

import com.buildings.property.Floor;

public class InexchangeableFloorsException extends IllegalArgumentException {

    public InexchangeableFloorsException(){
        super("Incomparable Floors");
    }

    public InexchangeableFloorsException( Floor first, Floor second){
        super("Incomparable Floors: " +
                first.getCountRooms() + " " + second.getCountRooms() +
                first.getSquare() + " " + second.getSquare());
    }
}

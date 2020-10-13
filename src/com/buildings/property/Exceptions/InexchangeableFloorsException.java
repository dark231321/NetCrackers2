package com.buildings.property.Exceptions;

import com.buildings.property.Floor;
import org.jetbrains.annotations.NotNull;

public class InexchangeableFloorsException extends IllegalArgumentException {

    public InexchangeableFloorsException(){
        super("Incomparable Floors");
    }

    public InexchangeableFloorsException(@NotNull Floor first, @NotNull Floor second){
        super("Incomparable Floors: " +
                first.getCountRooms() + " " + second.getCountRooms() +
                first.getSquare() + " " + second.getSquare());
    }
}

package com.buildings.property.Exceptions;

import com.buildings.property.Space;
import org.jetbrains.annotations.NotNull;

public class InexchangeableSpacesException extends IllegalArgumentException{
    public InexchangeableSpacesException(){
        super("Incomparable spaces");
    }
    public InexchangeableSpacesException(@NotNull Space first, @NotNull Space second){
        super("Incomparable spaces: " +
                first.getCountRooms() + " "+ second.getCountRooms() +
                first.getSquare() + " " + second.getSquare());
    }
}

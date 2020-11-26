package com.buildings.property.util.Exceptions;

public class InvalidRoomsCountException extends IllegalArgumentException {
    public InvalidRoomsCountException() {
        super("Invalid Rooms Count ");
    }

    public InvalidRoomsCountException(Throwable cause) {
        super("Invalid Rooms Count ", cause);
    }
}

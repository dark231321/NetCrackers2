package com.buildings.property.Exceptions;

public class InvalidRoomsCountException extends IllegalArgumentException {
    public InvalidRoomsCountException() {
        super("Invalid Rooms Count ");
    }

    public InvalidRoomsCountException(Throwable cause) {
        super("Invalid Rooms Count ", cause);
    }
}

package com.buildings.property.util.Exceptions;

public class InvalidSpaceAreaException extends IllegalArgumentException {
    public InvalidSpaceAreaException() {
        super("Invalid Space Area ");
    }

    public InvalidSpaceAreaException(Throwable cause) {
        super("Invalid Space Area ", cause);
    }
}

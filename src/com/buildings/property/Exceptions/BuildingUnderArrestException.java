package com.buildings.property.Exceptions;

public class BuildingUnderArrestException
        extends IllegalArgumentException {

    public BuildingUnderArrestException() {
        super("Invalid Build under arrest ");
    }

    public BuildingUnderArrestException(Throwable cause) {
        super("Invalid build under arrest ", cause);
    }
}

package com.buildings.property.util.Exceptions;

public class BuildingUnderArrestException
        extends IllegalArgumentException {

    public BuildingUnderArrestException() {
        super("Invalid Build under arrest ");
    }

    public BuildingUnderArrestException(Throwable cause) {
        super("Invalid build under arrest ", cause);
    }
}

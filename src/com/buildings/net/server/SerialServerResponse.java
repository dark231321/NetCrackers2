package com.buildings.net.server;

import com.buildings.property.Building;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Dwelling.Hotel.Hotel;
import com.buildings.property.util.Exceptions.BuildingUnderArrestException;
import com.buildings.property.Office.Office;

import java.io.*;
import java.net.Socket;

public class SerialServerResponse extends AbstractResponse {

    private ObjectOutputStream writer = null;
    private ObjectInputStream reader = null;

    public SerialServerResponse(Socket socket) throws IOException {
        super(socket);
        this.writer = new ObjectOutputStream(socket.getOutputStream());
        this.reader = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void count() throws IOException, ClassNotFoundException, InterruptedException {
        Thread.sleep(2500);
        Building building;
        while ((building = (Building) reader.readObject()) != null) {
            String price = "";
            try {
                System.out.println(building);
                checkArrest();
                if (building instanceof Office)
                    price = building.getCountSpace() * 1500 + "$";
                else if (building instanceof Hotel)
                    price = building.getCountSpace() * 2000 + "$";
                else if (building instanceof Dwelling)
                    price = building.getCountSpace() * 1000 + "$";
            } catch (BuildingUnderArrestException ex) {
                System.out.println("Building under arrest");
                price = "Building under arrest";
            }
            writer.writeObject(price);
            writer.flush();
        }
    }
}

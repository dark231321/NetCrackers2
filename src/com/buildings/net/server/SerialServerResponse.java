package com.buildings.net.server;

import com.buildings.property.Building;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Dwelling.Hotel.Hotel;
import com.buildings.property.Exceptions.BuildingUnderArrestException;
import com.buildings.property.Office.Office;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SerialServerResponse extends AbstractResponse {

    private PrintWriter writer = null;
    private ObjectInputStream reader = null;

    public SerialServerResponse(Socket socket) throws IOException {
        super(socket);
        this.writer = new PrintWriter(socket.getOutputStream());
        this.reader = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void count() throws IOException {
        try {
            ArrayList<Building> buildings = (ArrayList<Building>) reader.readObject();
            for (Building building: buildings) {
                try{
                    System.out.println(building);
                    checkArrest();
                    if(building instanceof Office)
                        writer.write(building.getCountSpace()* 1500 + "$\n");
                    else if(building instanceof Hotel)
                        writer.write(building.getCountSpace()* 2000 + "$\n");
                    else if(building instanceof Dwelling)
                        writer.write(building.getCountSpace()* 1000 + "$\n");
                } catch (BuildingUnderArrestException ex) {
                    System.out.println("Building under arrest");
                    writer.write("Building under arrest\n");
                }
            }
            writer.flush();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            this.disconnect();
        }
    }
}

package com.buildings.net.server;

import com.buildings.property.Algorithms.Buildings;
import com.buildings.property.Building;
import com.buildings.property.Exceptions.BuildingUnderArrestException;
import com.buildings.property.Factorys.DwellingFactory;
import com.buildings.property.Factorys.HotelFactory;
import com.buildings.property.Factorys.OfficeFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ServerResponse extends AbstractResponse {
    private Scanner reader = null;
    private PrintWriter writer = null;

    public ServerResponse(Socket socket)
            throws IOException {
        super(socket);
        this.reader = new Scanner(socket.getInputStream());
        this.writer = new PrintWriter(socket.getOutputStream());
    }

    @Override
    public void count(){
        Building building = null;
        String name;
        while (this.reader.hasNextLine()){
            try {
                if((name = this.reader.nextLine()).equals("exit"))
                    break;
                System.out.println(name);
                int price = 0;
                switch (name){
                    case "Dwelling":
                        Buildings.setAbstractFactory(new DwellingFactory());
                        building = Buildings.readBuilding(this.reader);
                        price = building.getCountSpace() * 1000;
                        break;
                    case "Office":
                        Buildings.setAbstractFactory(new OfficeFactory());
                        building = Buildings.readBuilding(this.reader);
                        price = building.getCountSpace() * 1500;
                        break;
                    case "Hotel":
                        Buildings.setAbstractFactory(new HotelFactory());
                        building = Buildings.readBuilding(this.reader);
                        price = building.getCountSpace() * 2000;
                        break;
                }
                checkArrest();
                System.out.println(building.toString());
                this.writer.write(price + "$\n");
                this.writer.flush();
            } catch (BuildingUnderArrestException io) {
                System.out.println("Building Under Arrest");
                this.writer.write("Building Under Arrest\n");
                this.writer.flush();
            }
        }
    }
}

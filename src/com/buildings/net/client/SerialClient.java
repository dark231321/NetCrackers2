package com.buildings.net.client;

import com.buildings.net.server.parallel.SerialServer;
import com.buildings.property.util.Buildings;
import com.buildings.property.Building;
import com.buildings.property.util.Factorys.DwellingFactory;
import com.buildings.property.util.Factorys.HotelFactory;
import com.buildings.property.util.Factorys.OfficeFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SerialClient {
    private static final String IP_ADDR = SerialServer.IP_ADD;
    private static final int PORT = SerialServer.PORT;
    private static ObjectInputStream fromServer;
    private static Scanner readerBuildings;
    private static BufferedReader readerName;
    private static ObjectOutputStream writer;
    private static BufferedWriter propertyPrice;

    static {
        try {
            propertyPrice = new BufferedWriter(new FileWriter("resources/PropertyPrice.txt"));
            readerBuildings = new Scanner(new FileReader("resources/MyBuilding.txt"));
            readerName = new BufferedReader(new FileReader("resources/NameBuildings.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fromServer()
            throws IOException, ClassNotFoundException {
        System.out.println("From server: ");
        String property = (String) fromServer.readObject();
        System.out.println(property);
        propertyPrice.write(property + "\n");
    }

    private static void request() throws IOException, ClassNotFoundException {
        Building building = null;
        String word = null;
        while (!(word = readerName.readLine()).equals("exit")) {
            System.out.println(word);
            switch (word)  {
                case "Dwelling":
                    Buildings.setAbstractFactory(new DwellingFactory());
                    break;
                case "Hotel":
                    Buildings.setAbstractFactory(new HotelFactory());
                    break;
                case "Office":
                    Buildings.setAbstractFactory(new OfficeFactory());
                    break;
            }
            building = Buildings.readBuilding(readerBuildings);
            System.out.println(building.toString());
            writer.writeObject(building);
            writer.flush();
            fromServer();
        }
        writer.writeObject(null);
        writer.flush();
        propertyPrice.flush();
    }

    public static void main(String[] args){
        try(Socket clientSocket = new Socket(IP_ADDR, PORT)) {
            System.out.println("Client Connected");
            writer = new ObjectOutputStream(clientSocket.getOutputStream());
            fromServer = new ObjectInputStream(clientSocket.getInputStream());
            request();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Client exception: " + e);
        }
    }

}

package com.buildings.net.client;

import com.buildings.net.server.sequental.SerialServer;
import com.buildings.property.Algorithms.Buildings;
import com.buildings.property.Building;
import com.buildings.property.Factorys.DwellingFactory;
import com.buildings.property.Factorys.HotelFactory;
import com.buildings.property.Factorys.OfficeFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class SerialClient {
    private static final String IP_ADDR = SerialServer.IP_ADD;
    private static final int PORT = SerialServer.PORT;
    private static Scanner fromServer;
    private static Scanner readerBuildings;
    private static BufferedReader readerName;
    private static ObjectOutputStream writer = null;

    static {
        try {
            readerBuildings = new Scanner(new FileReader("Building.txt"));
            readerName = new BufferedReader(new FileReader("NameBuildings.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void fromServer() throws IOException {
        BufferedWriter propertyPrice = new BufferedWriter(new FileWriter("PropertyPrice.txt"));
        System.out.println("From server: ");
        String property = fromServer.nextLine();
        System.out.println(property);
        propertyPrice.write(property + "\n");
        propertyPrice.flush();
    }

    private static void request() throws IOException {
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
    }

    public static void main(String[] args){
        try(Socket clientSocket = new Socket(IP_ADDR, PORT)) {
            System.out.println("Client Connected");
            writer = new ObjectOutputStream(clientSocket.getOutputStream());
            fromServer = new Scanner(clientSocket.getInputStream());
            request();
        } catch (IOException e){
            System.out.println("Client exception: " + e);
        }
    }

}

package com.buildings.net.client;

import com.buildings.net.server.sequental.BinaryServer;
import com.buildings.property.Algorithms.Buildings;
import com.buildings.property.Building;
import com.buildings.property.Factorys.DwellingFactory;
import com.buildings.property.Factorys.HotelFactory;
import com.buildings.property.Factorys.OfficeFactory;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BinaryClient {
    private static final String IP_ADDR = BinaryServer.IP_ADD;
    private static final int PORT = BinaryServer.PORT;
    private static Scanner fromServer;
    private static Scanner readerBuildings;
    private static BufferedReader readerName;
    private static PrintWriter writer = null;

    static {
        try {
            readerBuildings = new Scanner(new FileReader("Building.txt"));
            readerName = new BufferedReader(new FileReader("NameBuildings.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void toServer() throws IOException {
        Building building = null;
        String word = null;
        while (!(word = readerName.readLine()).equals("exit")) {
            System.out.println(word);
            switch (word)  {
                case "Dwelling":
                    Buildings.setAbstractFactory(new DwellingFactory());
                    writer.write("Dwelling\n");
                    break;
                case "Hotel":
                    Buildings.setAbstractFactory(new HotelFactory());
                    writer.write("Hotel\n");
                    break;
                case "Office":
                    Buildings.setAbstractFactory(new OfficeFactory());
                    writer.write("Office\n");
                    break;
            }
            building = Buildings.readBuilding(readerBuildings);
            System.out.println(building.toString());
            Buildings.writeBuildingFormat(building, writer);
            writer.write("\n");
        }
        System.out.println("exit");
        writer.write("exit"+"\n");
        writer.flush();
    }

    private static void fromServer() throws IOException {
        BufferedWriter propertyPrice = new BufferedWriter(new FileWriter("PropertyPrice.txt"));
        System.out.println("From server: ");
        while (fromServer.hasNextLine()){
            String property;
            if((property = fromServer.nextLine()).equals("exit"))
                break;
            System.out.println(property);
            propertyPrice.write(property + "\n");
        }
        propertyPrice.flush();
    }

    public static void main(String[] args){
        try(Socket clientSocket = new Socket(IP_ADDR, PORT)) {
            System.out.println("Client Connected");
            writer = new PrintWriter(clientSocket.getOutputStream());
            fromServer = new Scanner(clientSocket.getInputStream());
            toServer();
            fromServer();
        } catch (IOException e){
            System.out.println("Client exception: " + e);
        }

    }
}

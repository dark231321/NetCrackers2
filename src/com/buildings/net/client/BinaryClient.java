package com.buildings.net.client;

import com.buildings.net.server.parallel.BinaryServer;
import com.buildings.property.util.Buildings;
import com.buildings.property.Building;
import com.buildings.property.util.Factorys.DwellingFactory;
import com.buildings.property.util.Factorys.HotelFactory;
import com.buildings.property.util.Factorys.OfficeFactory;

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

    private static void fromServer() throws IOException {
        System.out.println("From server: ");
        String property = fromServer.nextLine();
        propertyPrice.write(property + "\n");
        System.out.println(property);
    }

    private static void request() throws IOException{
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
            writer.flush();
            fromServer();
        }
        propertyPrice.flush();
        System.out.println("exit");
        writer.write("exit"+"\n");
        writer.flush();
        propertyPrice.flush();
    }

    public static void main(String[] args){
        try(Socket clientSocket = new Socket(IP_ADDR, PORT)) {
            System.out.println("Client Connected");
            writer = new PrintWriter(clientSocket.getOutputStream());
            fromServer = new Scanner(clientSocket.getInputStream());
            request();
        } catch (IOException e) {
            System.out.println("Client exception: " + e);
        }
    }
}

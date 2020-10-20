package com.buildings.property.Algorithms;

import com.buildings.Container.AbstractArray;
import com.buildings.Container.MyArrayList;
import com.buildings.property.Building;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import com.buildings.property.Dwelling.Dwelling;
import com.buildings.property.Dwelling.DwellingFloor;
import com.buildings.property.Dwelling.Flat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.util.Objects;
import java.util.Scanner;

public class Buildings {
    public static void sortBuilding(Building building){
        Objects.requireNonNull(building);
        AbstractArray<Floor> buildingSpaceList = building.getSpaceList();
        buildingSpaceList.sort(null);
        for (Floor floor:
                buildingSpaceList) {
            floor.getSpaceList().sort(null);
        }
    }

    public static Building inputBuilding (InputStream in) {
        Building building = null;
        try {
            DataInputStream dataInputStream = new DataInputStream(in);
            MyArrayList<Floor> floorList = new MyArrayList<>();
            for (int i = dataInputStream.readInt() ; i > 0; i--){
                MyArrayList<Space> SpaceList = new MyArrayList<>();
                for(int sizeSpaces = dataInputStream.readInt(); sizeSpaces > 0; sizeSpaces--){
                    Space space = new Flat(dataInputStream.readInt(), dataInputStream.readDouble());
                    SpaceList.add(space);
                }
                floorList.add(new DwellingFloor(SpaceList));
            }
            building = Dwelling.ofDwelling(floorList);
        }catch (IOException o){
            System.out.println("Error");
        }
        return building;
    }


    public static void outputBuilding (Building building,
                                       OutputStream out) {
        try {
            DataOutputStream dataOutputStream = new
                    DataOutputStream(out);
            dataOutputStream.writeInt(building.size());
            for(int i = 0; i < building.size(); i++) {
                Floor floor = building.get(i);
                dataOutputStream.writeInt(floor.size());
                for(int j = 0; j < floor.size(); j++){
                    Space space = floor.get(j);
                    dataOutputStream.writeInt(space.getCountRooms() );
                    dataOutputStream.writeDouble(space.getSquare());
                }
            }
            out.close();
        }
        catch(IOException e) {
            System.out.println("Some error occurred!");
        }
    }

    public static void writeBuilding (Building building,
                                      Writer out) {
        PrintWriter printWriter = new PrintWriter(new
                BufferedWriter(out));
        StringBuilder stringBuffer = new StringBuilder();
        //printWriter.print(building.size() + " ");
        stringBuffer.append(building.size()).append(" ");
        for(int i = 0; i < building.size(); i++) {
            Floor floor = building.get(i);
            stringBuffer.append(floor.size()).append(" ");
            //printWriter.print(floor.size() + " ");
            for(int j = 0; j < floor.size(); j++){
                Space space = floor.get(j);
                stringBuffer.append(space.getCountRooms()).append(" ");
                //printWriter.print(space.getCountRooms() + " ");
                stringBuffer.append(space.getSquare()).append(" ");
                //printWriter.print(space.getSquare() + " ");
            }
        }
        printWriter.print(stringBuffer.toString());
        printWriter.close();
    }

    public static Building readBuilding (Reader in) {
        Building building = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(in);
            StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
            if(streamTokenizer.nextToken() != StreamTokenizer.TT_EOF){
                MyArrayList<Floor> floorList = new MyArrayList<>();
                for (int sizeFloors = (int) streamTokenizer.nval; sizeFloors > 0; --sizeFloors){

                    MyArrayList<Space> SpaceList = new MyArrayList<>();
                    if (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {

                        for (int sizeSpaces = (int)streamTokenizer.nval; sizeSpaces > 0; sizeSpaces--){
                            double square = 0;
                            int countRooms = 0;
                            if(streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) countRooms = (int) streamTokenizer.nval;
                            if(streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) square = streamTokenizer.nval;
                            Space space = new Flat(countRooms, square);
                            SpaceList.add(space);
                        }
                        floorList.add(new DwellingFloor(SpaceList));
                    }
                    building = Dwelling.ofDwelling(floorList);
                }
            }
            bufferedReader.close();
        }
        catch(IOException e) {
            System.out.println("Some error occurred!");
        }
        return building;
    }

    public static void serializeBuilding (Building building,
                                          OutputStream out){
        try {
            ObjectOutputStream objectOutputStream =
                    new ObjectOutputStream(out);
            objectOutputStream.writeObject(building);
        }catch (IOException ex){
            System.out.println("Error");
        }
    }

    public static Building deserializeBuilding (InputStream in){
        try {
            ObjectInputStream objectInputStream =
                    new ObjectInputStream(in);
            return (Building) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void readBuilding(Scanner scanner){

    }
    

    public static void writeBuildingFormat (Building building,
                                            Writer out) {
        try {
            writeBuilding(building, out);
            Scanner scanner = new Scanner(new FileReader(String.valueOf(out)));
            readBuilding(scanner);
        }catch (IOException io){
            System.out.println("Error");
        }
    }
}

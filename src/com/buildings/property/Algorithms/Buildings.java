package com.buildings.property.Algorithms;

import com.buildings.property.Building;
import com.buildings.property.Decorators.SynchronizedFloor;
import com.buildings.property.Factorys.BuildingFactory;
import com.buildings.property.Factorys.DwellingFactory;
import com.buildings.property.Floor;
import com.buildings.property.Space;
import org.jetbrains.annotations.Nullable;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
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
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

public class Buildings {

    private static BuildingFactory abstractFactory = new DwellingFactory();

    public static Building ofBuilding(int countFloors, int[] sizeFloors){
        return abstractFactory.createBuilding(countFloors, sizeFloors);
    }

    public static Building ofBuilding(Floor[] floors){
        return abstractFactory.createBuilding(floors);
    }

    public static Floor ofFloor(Space[] spaces){
        return abstractFactory.createFloor(spaces);
    }

    public static Floor ofFloor(int spaceCount){
        return abstractFactory.createFloor(spaceCount);
    }

    public static Space ofSpace(int roomsCount, double area){
        return abstractFactory.createSpace(roomsCount, area);
    }

    public static Space ofSpace(double area){
        return abstractFactory.createSpace(area);
    }

    public static void setAbstractFactory(BuildingFactory dwellingFactory) {
        Buildings.abstractFactory = dwellingFactory;
    }

    public static void sortFloor(Floor floor,
                                 @Nullable Comparator<? super Space> spaceComparator){
        floor.getSpaceList().sort(spaceComparator);
    }

    public static void sortFloors(Building building,
                                 @Nullable Comparator<? super Floor> floorComparator) {
        building.getSpaceList().sort(floorComparator);
    }

    public static void sortBuilding(Building building,
                                    @Nullable Comparator<? super Floor> floorComparator,
                                    @Nullable Comparator<? super Space> spaceComparator){
        Objects.requireNonNull(building);
        sortFloors(building, floorComparator);
        for (Floor floor:
             building) {
            sortFloor(floor, spaceComparator);
        }
    }

    public static Building inputBuilding (InputStream in) {
        Building building = null;
        try {
            DataInputStream dataInputStream = new DataInputStream(in);

            int floorSize = dataInputStream.readInt();
            Floor[] floorList = new Floor[floorSize];

            for (int i = 0 ; i < floorSize; i++){

                int spaceSize =  dataInputStream.readInt();
                Space[] SpaceList = new Space[spaceSize];

                for(int j = 0; j < spaceSize; j++){
                    SpaceList[j] = abstractFactory.createSpace(dataInputStream.readInt(), dataInputStream.readDouble());
                }

                floorList[i] = abstractFactory.createFloor(SpaceList);
            }
            building = abstractFactory.createBuilding(floorList);
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
        stringBuffer.append(building.size()).append(" ");
        for(int i = 0; i < building.size(); i++) {
            Floor floor = building.get(i);
            stringBuffer.append(floor.size()).append(" ");
            for(int j = 0; j < floor.size(); j++){
                Space space = floor.get(j);
                stringBuffer.append(space.getCountRooms()).append(" ");
                stringBuffer.append(space.getSquare()).append(" ");
            }
        }
        printWriter.print(stringBuffer.toString());
    }

    public static Building readBuilding (Reader in) {
        Building building = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(in);
            StreamTokenizer streamTokenizer = new StreamTokenizer(bufferedReader);
            if(streamTokenizer.nextToken() != StreamTokenizer.TT_EOF){

                int sizeFloors = (int) streamTokenizer.nval;
                Floor[] floorList = new Floor[sizeFloors];

                for (int i = 0; i < sizeFloors; i++){
                    if (streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) {

                        int sizeSpaces = (int) streamTokenizer.nval;
                        Space[] SpaceList = new Space[sizeSpaces];
                        for (int j = 0; j < sizeSpaces; j++){
                            double square = 0;
                            int countRooms = 0;
                            if(streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) countRooms = (int) streamTokenizer.nval;
                            if(streamTokenizer.nextToken() != StreamTokenizer.TT_EOF) square = streamTokenizer.nval;
                            SpaceList[j] = abstractFactory.createSpace(countRooms, square);
                        }
                        floorList[i] = abstractFactory.createFloor(SpaceList);
                    }
                }
                building = abstractFactory.createBuilding(floorList);
            }
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
            System.out.println("Some error occurred!");
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

    }

    public SynchronizedFloor synchronizedFloor(Floor floor){
        return new SynchronizedFloor(floor);
    }
}

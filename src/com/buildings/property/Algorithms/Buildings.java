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
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.Scanner;

public class Buildings {

    private static BuildingFactory abstractFactory = new DwellingFactory();

    public static Building ofBuilding(int countFloors, int[] sizeFloors){ return abstractFactory.createBuilding(countFloors, sizeFloors); }

    public static Building ofBuilding(Floor[] floors){ return abstractFactory.createBuilding(floors); }

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

    @SuppressWarnings("unchecked")
    public static Building ofBuilding(int countFloors, int[] sizeFloors, Class buildingClass) {
        try {
            return (Building) buildingClass.getConstructor(int.class, int[].class).newInstance(countFloors, sizeFloors);
        }catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    @SuppressWarnings("unchecked")
    public static Building ofBuilding(Floor[] floors, Class buildingClass){
        try {
            return (Building) buildingClass.getConstructor(Floor[].class).newInstance((Object) floors);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    @SuppressWarnings("unchecked")
    public static Floor ofFloor(Space[] spaces, Class floorClass){
        try {
            return (Floor) floorClass.getConstructor(Space[].class).newInstance((Object) spaces);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    @SuppressWarnings("unchecked")
    public static Floor ofFloor(int spaceCount, Class floorClass) {
        try {
            return (Floor) floorClass.getConstructor(int.class).newInstance(spaceCount);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    @SuppressWarnings("unchecked")
    public static Space ofSpace(int roomsCount, double area, Class spaceClass) {
        try {
            return (Space) spaceClass.getConstructor(int.class, double.class).newInstance(roomsCount, area);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
    }

    @SuppressWarnings("unchecked")
    public static Space ofSpace(double area, Class spaceClass) {
        try {
            return (Space) spaceClass.getConstructor(double.class).newInstance(area);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalArgumentException();
        }
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


    public static Building inputBuilding (InputStream in, Class<Building> buildingClass,
                                          Class<Floor> floorClass, Class<Space> spaceClass)
            throws IOException{
        Building building = null;
        try {
            DataInputStream dataInputStream = new DataInputStream(in);
            int floorSize = dataInputStream.readInt();
            Floor[] floorList = new Floor[floorSize];
            for (int i = 0 ; i < floorSize; i++){
                int spaceSize =  dataInputStream.readInt();
                Space[] SpaceList = new Space[spaceSize];
                for(int j = 0; j < spaceSize; j++)
                    SpaceList[j] = spaceClass.getConstructor(int.class, double.class).
                            newInstance(dataInputStream.readInt(), dataInputStream.readDouble());
                floorList[i] = floorClass.getConstructor(Space[].class).newInstance((Object) SpaceList);
            }
            building = buildingClass.getConstructor(Floor[].class).newInstance((Object) floorList);
        }catch (IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException o) {
            System.out.println("Error");
            throw new IllegalArgumentException();
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
        printWriter.print(stringBuffer.toString() + "\n");
        printWriter.flush();
    }

    public static Building readBuilding (Reader in, Class<Building> buildingClass,
                                         Class<Floor> floorClass, Class<Space> spaceClass) {
        Building building = null;
        try {
            StreamTokenizer streamTokenizer = new StreamTokenizer(new BufferedReader(in));
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
                            SpaceList[j] = spaceClass.getConstructor(int.class, double.class).newInstance(countRooms, square);
                        }
                        floorList[i] = floorClass.getConstructor(Space[].class).newInstance((Object) SpaceList);
                    }
                }
                building = buildingClass.getConstructor(Floor[].class).newInstance((Object) floorList);
            }
        }
        catch(IOException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Some error occurred!");
            throw new IllegalArgumentException();
        }
        return building;
    }

    public static Building readBuilding (Reader in) {
        Building building = null;
        try {
            StreamTokenizer streamTokenizer = new StreamTokenizer(new BufferedReader(in));
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
            throw new IllegalArgumentException();
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
            ObjectInputStream objectInputStream = new ObjectInputStream(in);
            return (Building) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Building readBuilding(Scanner scnr) {
        int floorAmount = scnr.nextInt();
        Floor[] bufFloor = new Floor[floorAmount];
        for(int i = 0; i < floorAmount; ++i) {
            int flatOnFloor = scnr.nextInt();
            Space[] flats = new Space[flatOnFloor];
            for(int j = 0; j < flatOnFloor; ++j) {
                int countRooms = scnr.nextInt();
                double area = Double.parseDouble(scnr.next());
                flats[j] = ofSpace(countRooms, area);
            }
            bufFloor[i] = ofFloor(flats);
        }
        if(scnr.hasNextLine())
            System.out.println(scnr.nextLine());
        return ofBuilding(bufFloor);
    }

    public static Building readBuilding(Scanner scnr,Class<Building> buildingClass,
                                        Class<Floor> floorClass, Class<Space> spaceClass) {
        Building building = null;
        try {
            int floorAmount = scnr.nextInt();
            Floor[] buffFloor = new Floor[floorAmount];
            for(int i = 0; i < floorAmount; ++i) {
                int flatOnFloor = scnr.nextInt();
                Space[] flats = new Space[flatOnFloor];
                for(int j = 0; j < flatOnFloor; ++j) {
                    int countRooms = scnr.nextInt();
                    double area = Double.parseDouble(scnr.next());
                    flats[j] = spaceClass.getConstructor(int.class, double.class).newInstance(countRooms, area);
                }
                buffFloor[i] = floorClass.getConstructor(Space[].class).newInstance((Object) flats);
            }
            if(scnr.hasNextLine())
                System.out.println(scnr.nextLine());
            return ofBuilding(buffFloor);
        }catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException i){
            System.out.println("Error");
            throw new IllegalArgumentException();
        }
    }


    public static void writeBuildingFormat (Building building,
                                            Writer out)
            throws IOException {
        String bufString = building.size() + " ";
        out.write(bufString);
        for (int i = 0; i < building.size(); i++) {
            Floor floor = building.get(i);
            bufString = floor.size() + " ";
            out.append(bufString);
            for (int j = 0; j < floor.size(); j++){
                bufString = floor.get(j).getCountRooms() + " " + floor.get(j).getSquare() + " ";
                if(i == building.size() - 1 &&
                        j == floor.size() - 1)
                    bufString = bufString.substring(0, bufString.length() - 1);
                out.append(bufString);
            }
        }
    }

    public SynchronizedFloor synchronizedFloor(Floor floor){
        return new SynchronizedFloor(floor);
    }
}

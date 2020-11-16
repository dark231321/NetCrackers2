package com.buildings.net.server;

import com.buildings.property.Exceptions.BuildingUnderArrestException;

import java.io.IOException;
import java.net.Socket;

public abstract class AbstractResponse {

    private Socket clientSocket = null;

    public AbstractResponse(Socket socket){
        System.out.printf("Connect socket:\n To port: %d\n This InetAddress: %s%n\n",
                socket.getPort(), socket.getInetAddress());
        this.clientSocket = socket;
    }

    public abstract void count() throws IOException, ClassNotFoundException;

    public void checkArrest(){
        int randInt = (int)(Math.random() * 100);
        System.out.println(randInt);
        if(randInt <= 10)
            throw new BuildingUnderArrestException();
    }

    public void disconnect() {
        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

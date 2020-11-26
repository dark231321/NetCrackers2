package com.buildings.net.server;

import com.buildings.property.util.Exceptions.BuildingUnderArrestException;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public abstract class AbstractResponse {

    protected Socket clientSocket = null;

    public AbstractResponse(Socket socket){
        System.out.printf("Connect socket: To port: %d This InetAddress: %s At the time: %s%n",
                socket.getPort(), socket.getInetAddress(), new Date());
        this.clientSocket = socket;
    }

    public abstract void count() throws IOException, ClassNotFoundException, InterruptedException;

    protected void checkArrest(){
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

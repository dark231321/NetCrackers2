package com.buildings.net.server.sequental;

import com.buildings.net.server.SerialServerResponse;

import java.io.IOException;
import java.net.ServerSocket;

public class SerialServer {
    public static final int PORT = 6666;
    public static final String IP_ADD = "127.0.0.1";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server start: ");
            while(true){
                try {
                    SerialServerResponse serverResponse = new SerialServerResponse(serverSocket.accept());
                    serverResponse.count();
                    serverResponse.disconnect();
                } catch (IOException | ClassNotFoundException io) {
                    System.out.println("Client socket connect exception: " + io);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException io){
            throw new RuntimeException();
        }
    }
}

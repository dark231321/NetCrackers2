package com.buildings.net.server.sequental;


import com.buildings.net.server.ServerResponse;

import java.io.IOException;
import java.net.ServerSocket;

public class BinaryServer {
    public static final int PORT = 5555;
    public static final String IP_ADD = "127.0.0.1";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server start: ");
            while(true){
                try {
                    ServerResponse serverResponse = new ServerResponse(serverSocket.accept());
                    serverResponse.count();
                    //serverResponse.disconnect();
                } catch (IOException io) {
                    System.out.println("Client socket connect exception: " + io);
                }
            }
        } catch (IOException io){
            throw new RuntimeException();
        }
    }
}

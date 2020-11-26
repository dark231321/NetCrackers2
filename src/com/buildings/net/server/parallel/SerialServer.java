package com.buildings.net.server.parallel;

import com.buildings.net.server.SerialServerResponse;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SerialServer {
    public static final int PORT = 8088;
    public static final String IP_ADD = "127.0.0.1";

    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Parallel server start");
            while (true){
                try{
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        try {
                            SerialServerResponse serverResponse = new SerialServerResponse(socket);
                            serverResponse.count();
                            serverResponse.disconnect();
                        } catch (IOException | ClassNotFoundException | InterruptedException io) {
                            io.printStackTrace();
                        }
                    }).start();
                } catch (IOException io){
                    System.out.println("Client socket connect exception: " + io);
                }
            }
        }catch (IOException io){
            throw new RuntimeException();
        }
    }

}

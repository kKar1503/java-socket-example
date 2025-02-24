package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Change to any number you like but this has to be the same for Client
    static final int port = 5055;

    public static void main(String[] args) {
        // Creates an instance of a socket server
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.printf("Server is listening on port %d%n", port);

            // In essence, all network servers are just infinite while(true) loops that listens for connections
            while (true) {
                System.out.println("waiting for the next connection...");
                // accept() basically handles the TCP handshake for you and tells client:
                // "OK buddy, please come in"
                // Reference: https://www.geeksforgeeks.org/tcp-3-way-handshake-process/
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");

                // Since we want to allow other clients to come in, you can think that each connection
                // will have its own thread.
                // But yeah... here's where the complexity bumps for implementation... Jiayous...
                new Thread(new ConnectionHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

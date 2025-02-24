package server;

import common.Card;
import common.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5055)) {
            System.out.println("Server is listening on port 5055");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            // Read message from client
            Card received = (Card) in.readObject();
            System.out.println("Received: " + received);

            // Send response
            Card response = new Card(0, Color.YELLOW);
            out.writeObject(response);

            in.close();
            out.close();
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

package client;

import common.Card;
import common.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 5055)) {
            System.out.println("Connected to server");

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Send message to server
            Card card = new Card(5, Color.BLACK);
            out.writeObject(card);

            // Read response
            Card response = (Card) in.readObject();
            System.out.println("Server says: " + response);

            in.close();
            out.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

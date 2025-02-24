package server;

import common.Card;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionHandler implements Runnable /* In order for this be runnable in the thread,
    we need to implement Runnable, under the hood Thread calls the run() method */ {
    private final Socket socket;

    public ConnectionHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handleConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // handleConnection() allows the us to handle the connection that we receives using the accept()
    // method in main()
    public void handleConnection() throws IOException, ClassNotFoundException {
        try (
                // We want to declare an in stream and an out stream, this allows us to send information
                // out and also receives information in.
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())
        ) {
            // readObject() allows us to read data from the inputStream and interpret it as an Object, where
            // you can then cast it into something that the other side sent. Of course, you should do proper
            // validation here with things like instanceof before casting.
            Card received = (Card) in.readObject();
            System.out.println("Received: " + received);

            Card response = new Card(received.getNumber() + 1 % 11, received.getColor());
            // writeObject(Object obj) method allows us to send data through the outputStream. The obj that we
            // pass in here should be a Serializable. This sends the response obj back to the client.
            out.writeObject(response);
            System.out.println("Sent: " + response);
        } finally {
            socket.close();
        }
    }
}

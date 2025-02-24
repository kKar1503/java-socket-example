package client;

import common.Card;
import common.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    // Change to any number you like but this has to be the same for Server
    static final int port = 5055;
    // This should be localhost if you are not deploying
    static final String host = "localhost";

    public static void main(String[] args) {
        // Creates an instance of a socket
        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connected to server");

            try (
                    // The in and out are the inputStream and outputStream respectively that allows the Client
                    // to send information to the connection socketServer.
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
            ) {
                int cardNumber = getCardNumber();
                Color cardColor = getCardColor();
                Card card = new Card(cardNumber, cardColor);

                // writeObject(Object obj) allows us to send the information to the socketServer, the obj has
                // to implement Serializable in order for it to be sent across.
                out.writeObject(card);
                System.out.println("Send to server: " + card);

                // readObject() receives the information from the other end of the socket, which in this case
                // the socketServer. The received obj will be in Object, so we need to cast it to the class we
                // want. But of course, we should do proper validation with instanceof before casting.
                Card response = (Card) in.readObject();
                System.out.println("Server says: " + response);
            } catch (IOException | ClassNotFoundException | RuntimeException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int getCardNumber() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter card number (between 0 and 10)> ");
        int cardNumber = sc.nextInt();
        sc.nextLine();
        if (cardNumber < 0 || cardNumber > 10) {
            throw new RuntimeException("Card number is only from 0 to 10");
        }
        return cardNumber;
    }

    public static Color getCardColor() {
        Scanner sc = new Scanner(System.in);
        System.out.print("""
                Select card color
                1. Blue
                2. Red
                3. Yellow
                4. Black
                5. Green
                6. Purple
                Select between 1 and 6 only>\s""");
        int cardColorIndex = sc.nextInt();
        return switch (cardColorIndex) {
            case 1 -> Color.BLUE;
            case 2 -> Color.RED;
            case 3 -> Color.YELLOW;
            case 4 -> Color.BLACK;
            case 5 -> Color.GREEN;
            case 6 -> Color.PURPLE;
            default -> throw new RuntimeException("Invalid colour index, only 1 to 6 is supported");
        };
    }
}

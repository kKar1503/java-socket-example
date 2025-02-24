## Basic Implementation of Java Sockets

This is a basic implementation of Java Sockets. It is a simple client-server application
that sends a Card objet from the client to the server and the server responds with another
Card object with the same `Color` and a `CardNumber` that is incremented by 1 back to the
client.

## Card Class

To allow the `Card` class to be sent over the network, it implements the `Serializable` interface.
Since it also uses an `Enum` for the `Color`, they also implement the `Serializable` interface.

By implementing the `Serializable` interface, the `Card` class can be sent over the network by the
sockets.

## Server

The server listens for incoming connections on a given port, in this case, I've declared it as `5055`.
When a connection is established, we have to first use the `accept()` method to accept the connection
and this is where the TCP handshake is being handled. After the connection is established, we can
then read the incoming object from the client using the `readObject()` method. We then process the
incoming object _(in this example, I just cast it directly to `Card` but you should do proper validation
in a proper implementation)_ and respond with a new `Card` object using the `writeObject(Object obj)`
method.

## Client

The client connects to the server using the server's host and port number, which has to be the same as the
server, which in this case, `5055`. It then sends a `Card` object to the server using the
`writeObject(Object obj)` method. It then reads the response from the server using the `readObject()` method
which we also cast to a `Card` object.

## Running the Application

To run the application, you will need to compile both the `Server.java` and `Client.java` files. You can do
this by running the following commands:

```bash
javac -cp src -d out src/server/Server.java
javac -cp src -d out src/client/Client.java
```

After compiling the files, you can then run the server and client using the following commands:

```bash
# Terminal 1
java -cp out server.Server # This will start the server

# Terminal 2
java -cp out client.Client # This will start the client
```

You should see the client sending a `Card` object to the server and the server responding with a
new `Card` object with the same `Color` and an incremented card number.

## Folder Structure

The folder structure is as follows:

```
.
└── src
    ├── client
    │   └── Client.java    # Client implementation
    ├── common
    │   ├── Card.java      # Card class
    │   └── Color.java     # Color enum
    └── server
        └── Server.java          # Server implementation
```
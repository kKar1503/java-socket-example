## Basic Implementation of Java Sockets with Threads

This is a slightly more complex implementation of Java Sockets. It is a simple client-server
application that sends a Card objet from the client to the server and the server responds with
another Card object with the same `Color` and a `CardNumber` that is incremented by 1 back to the
client.

On top of the basic implementation of Java Sockets, this implementation also introduces the use of
`Thread` to handle multiple clients. This now allows the server to accept more than one connection
at a time. So this means that each connection are no longer _blocking_.

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

However, one of the shortcomings of the previous implementation is that the server can only handle one
connection at each time and the server literally dies right after responding. So in this example, we
actually improved it by setting the accepting of socket connections in an infinite loop and creating a
new `Thread` for each connection. This allows the server to handle multiple connections at the same time.

### Problem with Threads

While it will be way out of scope to implement something other than `Thread` for this project. But if you
want to consider much deeper about the implementation here, you should reconsider the use of `Thread` and
use something else.

_Why?_ `Thread` in Java is actually extremely expensive, as they're essentially a direct wrapper of Operating
System's threads. This means that creating a new `Thread` is actually very expensive and can be very slow and
it has to deal with the Operating System's thread scheduling and context switching.

_What's the alternative?_ You can consider looking the green threading options, which are much more performant
and lightweight than `Thread`. Some of the options are `Fiber` from Project Loom, `Quasar` from Parallel Universe
and `Kotlin Coroutines`. These are much more lightweight and performant than `Thread`.

## Client

The client connects to the server using the server's host and port number, which has to be the same as the
server, which in this case, `5055`. It then sends a `Card` object to the server using the
`writeObject(Object obj)` method. It then reads the response from the server using the `readObject()` method
which we also cast to a `Card` object.

The client in this branch also has one minor added improvement where it will now take user inputs for the card
number and the `Color`. This is so that it provides you the ability to test different card numbers and colors
when sending the `Card` object to the server.

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

# Terminal 3
java -cp out client.Client # This will start another client
```

With two different clients running, you can see that the server can handle multiple connections at the
same time. From here, you can also try things like:

1. Terminal 2: Start client
2. Terminal 3: Start another client
3. Terminal 3: Sends the request first
4. Terminal 2: Sends the request

With the implementation of `Thread`, you can see that the server can handle multiple connections at the
same time and also asynchronously.

## Folder Structure

The folder structure is as follows:

```
.
└── src
    ├── client
    │   └── Client.java             # Client implementation
    ├── common
    │   ├── Card.java               # Card class
    │   └── Color.java              # Color enum
    └── server
        ├── ConnectionHandler.java        # ConnectionHandler class
        └── Server.java                   # Server implementation
```

## What's Next?

This is a very basic implementation of Java Sockets. Next thing, you will have to think about, since you
have multiple clients connecting to the server, you will have to think about how to _(now with the clients
on different `Thread`)_ handle the synchronization of the data. This is where you will have to think
about using `synchronized` blocks or methods to handle the synchronization of the data, or you can also
consider using `Locks` from the `java.util.concurrent.locks` package.
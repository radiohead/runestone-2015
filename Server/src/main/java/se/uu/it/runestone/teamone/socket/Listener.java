package se.uu.it.runestone.teamone.socket;

import javax.net.ServerSocketFactory;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Initiates and listens on a socket.
 *
 * @author Åke Lagercrantz
 */
public class Listener {
    private ServerSocket serverSocket;
    private Socket socket;

    private ListenerDelegate delegate;
    private Integer port;

    public Listener(Integer port, ListenerDelegate delegate) throws IOException {
        this.port = port;
        this.delegate = delegate;
    }

    public void listen() throws IOException {
        this.serverSocket = ServerSocketFactory.getDefault().createServerSocket(this.port);
        Boolean exit = false;

        while (!exit) {
            System.out.println("Listener - Waiting for connection on port " + this.port.toString() + ".");
            this.socket = serverSocket.accept();
            System.out.println("Listener - Connected on port " + this.port.toString());
            new ListenerHandler(socket, delegate).start();
            System.out.println("Listener - Connection closed.");
        }
    }
}

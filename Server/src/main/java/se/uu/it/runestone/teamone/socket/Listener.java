package se.uu.it.runestone.teamone.socket;

import javax.net.ServerSocketFactory;
import java.net.ServerSocket;

/**
 * Initiates and listens on a socket.
 *
 * @author Åke Lagercrantz
 */
public class Listener {

    private ServerSocket serverSocket;

    public Listener() {
        ServerSocketFactory factory = ServerSocketFactory.getDefault();

        try {
            this.serverSocket = factory.createServerSocket();
        } catch (Exception e) { };
    }
}

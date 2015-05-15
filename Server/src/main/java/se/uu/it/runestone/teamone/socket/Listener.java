package se.uu.it.runestone.teamone.socket;

import javax.net.ServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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

    private PrintWriter out;
    private BufferedReader in;

    private ListenerDelegate delegate;
    private Integer port;

    public Listener(Integer port, ListenerDelegate delegate) throws IOException {
        this.port = port;
        this.delegate = delegate;
    }

    public void listen() throws IOException {
        String input;

        this.serverSocket = ServerSocketFactory.getDefault().createServerSocket(this.port);

        Boolean exit = false;
        while (!exit) {
            System.out.println("Waiting for connection on port " + this.port.toString());
            this.socket = serverSocket.accept();
            System.out.println("Connected on port " + this.port.toString());

            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((input = in.readLine()) != null) {
                System.out.println("Received command: " + input);

                if (input.equals("Quit")) {
                    exit = true;
                    break;
                } else if (input.equals("map")) { // map
                    Integer width = this.delegate.warehouseWidth();
                    Integer height = this.delegate.warehouseHeight();
                    out.println(width.toString() + "," + height.toString()); // <x>,<y>
                } else if (input.startsWith("goto,")) { // "goto,<x>,<y>"
                    String[] parts = input.split(",");
                    Integer x = Integer.valueOf(parts[1]);
                    Integer y = Integer.valueOf(parts[2]);

                    this.delegate.goTo(x,y);

                    out.println("On my way!");
                }
            }

            System.out.println("Connection closed.");
        }
    }
}

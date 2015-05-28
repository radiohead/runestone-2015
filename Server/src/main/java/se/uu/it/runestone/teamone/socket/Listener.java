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
            System.out.println("Listener - Waiting for connection on port " + this.port.toString() + ".");
            this.socket = serverSocket.accept();
            System.out.println("Listener - Connected on port " + this.port.toString());

            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((input = in.readLine()) != null) {
                System.out.println("Listener - Received command \"" + input + "\"");

                if (input.equals("map")) { // map
                    Integer width = this.delegate.warehouseWidth();
                    Integer height = this.delegate.warehouseHeight();

                    // TODO: send obstacles positions as well
                    out.println("{\"size_x\": " + width.toString() +", \"size_y\": " + height.toString() +"}"); // <x>,<y>
                } else if (input.startsWith("goto,")) {
                    String[] parts = input.split(",");

                    if (parts.length == 3) {  // "goto,<x>,<y>"
                        Integer x = Integer.valueOf(parts[1]);
                        Integer y = Integer.valueOf(parts[2]);

                        this.delegate.goTo(x,y);
                        out.println("{\"status\": \"processing\"}");
                    } else if (parts.length == 4) {
                        out.println("{\"status\": \"processing\"}");
                    } else {
                        out.println("{\"error\": \"not_available\"}");
                    }

                } else if (input.equals("robot")) {
                    out.println("[{\"id\": 1, \"name\": \"taikaviitta\", \"direction\": \"east\", \"position_x\": 0, \"position_y\":0}]");
                } else if (input.equals("release")) {
                    out.println("{\"success\": true}");
                } else if (input.equals("sensor")) {
                    out.println("[{\"id\": 1, \"name\": \"arduino-1\", \"light\": \"1\", \"temperature\": 20, \"position_x\": 2, \"position_y\": 2}]");
                } else {
                    out.println("{\"error\": \"not_available\"}");
                }
            }

            System.out.println("Listener - Connection closed.");
        }
    }
}

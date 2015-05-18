import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Lelli on 15/05/15.
 */

/** Sets up a socket connection with host and waits
 *  for a string to be returned */
public class SocketConnection {
    public static String fetch(String message) {
        /** Define a host server and port */
        String host = "127.0.0.1";
        int port = 4444;

        StringBuffer instr = new StringBuffer();
        System.out.println("SocketClient initialized");
        try {
            /** Establish a socket connetion */
            Socket connection = new Socket(host, port);
            /** Instantiate a PrintWriter object */
            PrintWriter pw = new PrintWriter(connection.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            /** Write across the socket connection*/
            pw.println(message);

            /** Recieve from Socket and return something*/
            String input;
            while ((input = br.readLine()) != null) {
                connection.close();
                return input;
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return message;
    }
}

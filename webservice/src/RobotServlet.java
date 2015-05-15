import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/*  The java.net package contains the basics needed for network operations. */
import java.net.*;
/* The java.io package contains the basics needed for IO operations. */
import java.io.*;

/**
 * Created by Lelli on 15/05/15.
 */
public class RobotServlet extends HttpServlet {
    /** Define a host server and port */
    String host = "130.243.197.197";
    int port = 4444;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("Hello, this is robot!!!!!" + fetchFromServer("map"));
    }

    /** Setup a socket connection and send room*/
    public String fetchFromServer(String message) {
        StringBuffer instr = new StringBuffer();
        String TimeStamp;
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
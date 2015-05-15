import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.*;

/**
 * Created by Lelli on 15/05/15.
 */
public class RoomServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("Hello, this is room!!!!!");
    }

    public String fetchFromServer(String room){
        //Setup a socket connection and send room

        //Recieve something from Socket and return something
        return room;
    }
}
import javax.servlet.http.*;
import javax.servlet.ServletException;
import java.io.*;

/**
 * Created by Lelli on 15/05/15.
 */
public class RoomServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println(SocketConnection.fetch("map"));
    }
}
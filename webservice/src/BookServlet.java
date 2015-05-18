import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Lelli on 15/05/15.
 */
public class BookServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        out.println("{\"id\": 3, \"fname\": Jakob, \"sname\": Sennerby, \"requestType\": csgobooking, \"nullable\":false}");
    }
}
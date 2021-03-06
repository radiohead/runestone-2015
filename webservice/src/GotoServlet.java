import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Lelli on 15/05/15.
 */
public class GotoServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        PrintWriter out = response.getWriter();

        if(!(isNumeric(x) && isNumeric(y))){
            out.println("false");
        }
        else{
            out.println(SocketConnection.fetch("goto," + x +"," + y));
        }
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            int i = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
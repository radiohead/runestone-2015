import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Lelli on 15/05/15.
 */
public class ScheduleServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        String temp = request.getParameter("temp");
        String light = request.getParameter("light");
        String size = request.getParameter("size");
        //Check that the temp/light inputs are doubles
        if(!(isNumeric(temp) && isNumeric(light) && isNumeric(size))){
            out.println("false");
        }
        else{
            out.println(SocketConnection.fetch("goto," + temp +"," + light +"," + size));
        }
    }
    public static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
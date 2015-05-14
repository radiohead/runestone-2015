package se.uu.it.runestone.teamone;

/**
 * The main application. Starts a server as well as a Spring instance.
 *
 * @author Åke Lagercrantz
 */
public class Application {
    public static void main(String[] args) {
        Thread thread = new Thread(new Server());
        System.out.println("Spawning server thread");
        thread.start();
    }
}

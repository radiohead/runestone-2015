package se.uu.it.runestone.teamone.robotcontrol;

/**
 * Represents a real world robot moving around the warehouse floor.
 *
 * @author Åke Lagercrantz
 */
public class Robot extends Thread {

    private Communicator communicator;
    private Thread communicatorThread;

    /**
     * The designated initializer.
     *
     * TODO: Add communication info parameters.
     */
    public Robot() {
        this.communicator = new Communicator();
    }

    // TODO: Add methods for inter-thread communication with the dispatch.
    // The robot need to wait for commands from dispatch, and return the
    // success status asynchronously.
}

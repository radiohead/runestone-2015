package se.uu.it.runestone.teamone.robotcontrol;

/**
 * Represents a real world robot moving around the warehouse floor.
 *
 * @author Åke Lagercrantz
 */
public class Robot {

    private Communicator communicator;
    private Thread communicatorThread;

    /**
     * The designated initializer.
     *
     * TODO: Add communication info parameters.
     */
    public Robot() {
        this.communicator = new Communicator();
        this.communicatorThread = new Thread(this.communicator);
    }

    /**
     * Turns the robot 90 degrees to the right.
     *
     * This method is synchronous and will not return until
     * the physical robot has responded with whether the
     * action was successfull or not.
     *
     * @return Whether the action was successful or not.
     */
    public Boolean turnRight() {
        // TODO: Send command to robot to turn right. <akelagercrantz>
        return false;
    }

    /**
     * Turns the robot 90 degrees to the left.
     *
     * This method is synchronous and will not return until
     * the physical robot has responded with whether the
     * action was successfull or not.
     *
     * @return Whether the action was successful or not.
     */
    public Boolean turnLeft() {
        // TODO: Send command to robot to turn left. <akelagercrantz>
        return false;
    }

    /**
     * Moves the robot forward one grid space.
     *
     * This method is synchronous and will not return until
     * the physical robot has responded with whether the
     * action was successfull or not.
     *
     * @return Whether the action was successful or not.
     */
    public Boolean navigateForward() {
        // TODO: Send command to robot to nagivate forward. <akelagercrantz>
        return false;
    }
}

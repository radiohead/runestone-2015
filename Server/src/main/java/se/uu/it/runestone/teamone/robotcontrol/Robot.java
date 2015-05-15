package se.uu.it.runestone.teamone.robotcontrol;

import se.uu.it.runestone.teamone.map.Node;
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.robotcontrol.command.Command;

/**
 * Represents a real world robot moving around the warehouse floor.
 *
 * @author Åke Lagercrantz
 */
public class Robot implements Runnable {

    private Communicator communicator;
    private Thread communicatorThread;

    private Node currentPosition;
    private Room.Direction currentDirection;

    private Command currentCommand;

    /**
     * The designated initializer.
     *
     * TODO: Add communication info parameters.
     */
    public Robot() {
        this.communicator = new Communicator();
    }

    @Override
    public void run() {
        while (true) {
            Command command = this.getCurrentCommand();
            if (command == null) {
                try {
                    Thread.sleep(200);
                } catch (Exception e) { }
            } else {
                this.communicator.sendCommand(command);
                this.setCurrentCommand(null);
            }
        }
    }

    /**
     * Gets the current position.
     *
     * Thread safe.
     *
     * @return The current position of the robot.
     */
    public synchronized Node getCurrentPosition() {
        return this.currentPosition;
    }

    /**
     * Sets the current position of the robot.
     *
     * Thread safe.
     *
     * @param currentPosition The new position of the robot.
     */
    public synchronized void setCurrentPosition(Node currentPosition) {
        this.currentPosition = currentPosition;
    }

    /**
     * Gets the current direction of the robot.
     *
     * Thread safe.
     *
     * @return The current direction of the robot.
     */
    public synchronized Room.Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Sets the current direction of the robot.
     *
     * @param currentDirection The new direction of the robot.
     */
    public synchronized void setCurrentDirection(Room.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    /**
     * Gets the current command being executed on the robot.
     *
     * @return The current executing command.
     */
    public synchronized Command getCurrentCommand() {
        return currentCommand;
    }

    /**
     * Sets a new command to be executed on the robot.
     *
     * Note: Will only set a new command if the current command is null.
     *
     * @param currentCommand The new command to be executed.
     */
    public Boolean setCurrentCommand(Command currentCommand) {
        if (this.currentCommand == null) {
            this.currentCommand = currentCommand;
            return true;
        } else {
            return false;
        }
    }

    // TODO: Add methods for inter-thread communication with the dispatch.
    // The robot need to wait for commands from dispatch, and return the
    // success status asynchronously.
}

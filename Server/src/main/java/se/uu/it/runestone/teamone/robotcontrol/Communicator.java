package se.uu.it.runestone.teamone.robotcontrol;

import se.uu.it.runestone.teamone.robotcontrol.command.Command;

/**
 * Communicates with a physical robot.
 *
 * @author Åke Lagercrantz
 */
public class Communicator {

    /**
     * The designated initializer. Creates a new communicator and
     * establishes a connection with a physical robot.
     *
     * TODO: Add communication setting parameters needed to initialize communications.
     */
    public Communicator() {

    }

    /**
     * Sends a command to the robot and waits for execution.
     *
     * Note that this call is blocking until a reply has been received
     * or the call has timed out.
     *
     * @param command The command to send.
     *
     * @return Whether the command was executed successfully.
     */
    public Boolean sendCommand(Command command) {
        return false;
    }
}

package se.uu.it.runestone.teamone.robotcontrol.command;

import se.uu.it.runestone.teamone.map.Room;

import javax.validation.constraints.NotNull;

/**
 * Represents a turn command that can be sent to the robot.
 *
 * @author Åke Lagercrantz
 */
public abstract class TurnCommand extends Command {

    /**
     * Returns the direction a robot would have after the turn
     * command has been executed.
     *
     * @param initialDirection The direction the robot has prior to
     *                         execution.
     *
     * @return The direction after execution.
     */
    @NotNull
    public abstract Room.Direction directionAfterExecution(Room.Direction initialDirection);

}

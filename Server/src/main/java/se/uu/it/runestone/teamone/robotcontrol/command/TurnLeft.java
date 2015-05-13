package se.uu.it.runestone.teamone.robotcontrol.command;

import se.uu.it.runestone.teamone.map.Room;

import javax.validation.constraints.NotNull;

/**
 * Represents a command that tells the robot to turn left 90 degrees.
 *
 * @author Åke Lagercrantz
 */
public class TurnLeft extends TurnCommand {

    @NotNull
    @Override
    public Room.Direction directionAfterExecution(Room.Direction initialDirection) {
        switch (initialDirection) {
            case NORTH:
                return Room.Direction.WEST;
            case EAST:
                return Room.Direction.NORTH;
            case SOUTH:
                return Room.Direction.EAST;
            case WEST:
                return Room.Direction.SOUTH;
            case NONE:
            default:
                return Room.Direction.NONE;
        }
    }

}

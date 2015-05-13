package se.uu.it.runestone.teamone.robotcontrol.command;

import se.uu.it.runestone.teamone.map.Room;

import javax.validation.constraints.NotNull;

/**
 * Represents a command that tells the robot to turn right 90 degrees.
 *
 * @author Åke Lagercrantz
 */
public class TurnRight extends TurnCommand {

    @NotNull
    @Override
    public Room.Direction directionAfterExecution(Room.Direction initialDirection) {
        switch (initialDirection) {
            case NORTH:
                return Room.Direction.EAST;
            case EAST:
                return Room.Direction.SOUTH;
            case SOUTH:
                return Room.Direction.WEST;
            case WEST:
                return Room.Direction.NORTH;
            case NONE:
            default:
                return Room.Direction.NONE;
        }
    }

}

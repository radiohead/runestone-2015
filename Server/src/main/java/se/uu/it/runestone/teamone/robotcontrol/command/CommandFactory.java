package se.uu.it.runestone.teamone.robotcontrol.command;

import se.uu.it.runestone.teamone.map.Node;
import se.uu.it.runestone.teamone.map.Room;

import java.util.ArrayList;

/**
 * A factory that creates commands from node paths.
 *
 * @author Åke Lagercrantz
 */
public class CommandFactory {

    /**
     * Converts a path (array of nodes) into a series of commands.
     *
     * @param path             The path to convert.
     * @param initialDirection The direction the robot is facing prior to command
     *                         execution.
     *
     * @return The commands to be executed serially for the robot to navigate the path.
     */
    public static ArrayList<Command> commandsFromPath(ArrayList<Node> path, Room.Direction initialDirection) {
        return CommandFactory.commandsFromPath(path, initialDirection, new ArrayList<Command>());
    }

    private static ArrayList<Command> commandsFromPath(ArrayList<Node> remainingPath, Room.Direction currentDirection, ArrayList<Command> accumulator) {
        if (remainingPath.size() < 2) { // Finished
            return accumulator;
        }

        Node from = remainingPath.get(0);
        Node to = remainingPath.get(1);

        if (Room.direction(from, to) == currentDirection) { // Move forward
            remainingPath.remove(from);
            MoveForward moveForwardCommand = new MoveForward();
            accumulator.add(moveForwardCommand);
            return commandsFromPath(remainingPath, currentDirection, accumulator);
        } else { // Rotate
            TurnCommand turnCommand = rotation(from, to, currentDirection);
            Room.Direction newDirection = turnCommand.directionAfterExecution(currentDirection);
            accumulator.add(turnCommand);
            return commandsFromPath(remainingPath, newDirection, accumulator);
        }
    }

    /**
     * Creates a new turn command in the required direction.
     *
     * @note The created command will only turn 90 degrees. If a
     *       180 degree turn is required, two commands must be created
     *       in series.
     *
     * @param from             The current node.
     * @param to               The node to navigate to.
     * @param currentDirection The current direction of the robot.
     *
     * @return The turn command.
     */
    private static TurnCommand rotation(Node from, Node to, Room.Direction currentDirection) {
        Room.Direction requiredDirection = Room.direction(from, to);

        switch (requiredDirection) {
            case NORTH:
                if (currentDirection == Room.Direction.WEST) {
                    return new TurnRight();
                } else {
                    return new TurnLeft();
                }
            case WEST:
                if (currentDirection == Room.Direction.SOUTH) {
                    return new TurnRight();
                } else {
                    return new TurnLeft();
                }
            case SOUTH:
                if (currentDirection == Room.Direction.EAST) {
                    return new TurnRight();
                } else {
                    return new TurnLeft();
                }
            case EAST:
                if (currentDirection == Room.Direction.NORTH) {
                    return new TurnRight();
                } else {
                    return new TurnLeft();
                }
            case NONE:
            default:
                return null;
        }
    }
}

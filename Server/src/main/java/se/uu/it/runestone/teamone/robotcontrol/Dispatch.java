package se.uu.it.runestone.teamone.robotcontrol;

import se.uu.it.runestone.teamone.map.Node;
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.pathfinding.PathFinder;
import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;
import se.uu.it.runestone.teamone.robotcontrol.command.Command;
import se.uu.it.runestone.teamone.robotcontrol.command.CommandFactory;
import se.uu.it.runestone.teamone.scheduler.Job;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The dispatch handles control over robots on the warehouse floor. It is given
 * charge over a robot and a given job, and uses the pathfinder & map to navigate
 * the robot to it's end destination.
 *
 * @author Åke Lagercrantz
 */
public class Dispatch {

    private Room room;
    private PathFinder pathFinder;

    private HashMap<Robot, Node> robotPositions;

    private HashMap<Robot, ArrayList<Command>> operations;

    /**
     * The designated initializer. Creates a new dispatch.
     *
     * @param room       The warehouse to navigate when executing a job.
     * @param pathFinder The pathfinder to use when executing a job.
     */
    public Dispatch(Room room, PathFinder pathFinder) {
        this.room = room;
        this.pathFinder = pathFinder;
    }

    /**
     * Dispatches a robot on a job. Will remain in charge of the robot
     * until the job is done, when the robot is returned. No other
     * dispatch should communicate with the robot during this time.
     *
     * @param robot The robot to dispatch.
     * @param job   The job to execute.
     *
     * @return The robot when the job is executed.
     */
    public Robot dispatch(Robot robot, Job job, Node currentPosition, Room.Direction currentDirection) {
        @SuppressWarnings({"unchecked"}) // We know the return type will be ArrayList<Node> since we supply the nodes ourselves.
        ArrayList<Node> path = (ArrayList<Node>) this.pathFinder.shortestPathToNodeMatchingRequirements(currentPosition, job.goods.getRequirements(), this.room);

        ArrayList<Command> commands = CommandFactory.commandsFromPath(path, currentDirection);
        this.operations.put(robot, commands);

        // TODO: Execute operations one by one asynchronously and wait for completion.

        return robot;
    }

}

package se.uu.it.runestone.teamone.robotcontrol;

import se.uu.it.runestone.teamone.map.Node;
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.pathfinding.PathFinder;
import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;
import se.uu.it.runestone.teamone.robotcontrol.command.Command;
import se.uu.it.runestone.teamone.robotcontrol.command.CommandFactory;
import se.uu.it.runestone.teamone.scheduler.Job;
import se.uu.it.runestone.teamone.scheduler.Scheduler;

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
public class Dispatch implements Runnable {

    private Room room;
    private PathFinder pathFinder;
    private Scheduler scheduler;
    private Robot robot;

    private Boolean manualMode;
    private Node manualDestination;

    /**
     * The designated initializer. Creates a new dispatch.
     *
     * @param room       The warehouse to navigate when executing a job.
     * @param pathFinder The pathfinder to use when executing a job.
     */
    public Dispatch(Robot robot, Room room, PathFinder pathFinder, Scheduler scheduler) {
        this.room = room;
        this.pathFinder = pathFinder;
        this.scheduler = scheduler;
        this.robot = robot;
    }

    @Override
    public void run() {
        while (true) {
            Job job;
            if (!this.manualMode && (job = scheduler.nextJob()) != null) {
                this.dispatch(this.robot, job, this.robot.getCurrentPosition(), this.robot.getCurrentDirection());
            } else {
                try {
                    Thread.sleep(200);
                } catch (Exception e) { }
            }
        }
    }

    /**
     * Sets the robot in manual mode and navigates to
     * destination node.
     *
     * @param destination The destination to navigate to.
     */
    public void setManualMode(Node destination) {
        System.out.println("Setting manual mode");
        this.manualMode = true;
        this.dispatch(this.robot, destination, this.robot.getCurrentPosition(), this.robot.getCurrentDirection());
    }

    /**
     * Dispatches a robot on a job. Will remain in charge of the robot
     * until the job is done, when the robot is returned. No other
     * dispatch should communicate with the robot during this time.
     *
     * @param robot The robot to dispatch.
     * @param job   The job to execute.
     */
    private void dispatch(Robot robot, Job job, Node currentPosition, Room.Direction currentDirection) {
        System.out.println("Going to node matching reqs.");

        @SuppressWarnings({"unchecked"}) // We know the return type will be ArrayList<Node> since we supply the nodes ourselves.
        ArrayList<Node> path = (ArrayList<Node>) this.pathFinder.shortestPathToNodeMatchingRequirements(currentPosition, job.goods.getRequirements(), this.room);

        ArrayList<Command> commands = CommandFactory.commandsFromPath(path, currentDirection);

        this.executeCommands(robot, commands);
    }

    /**
     * Dispatches a robot to navigate to a destination.
     *
     * @param robot             The robot to dispatch.
     * @param destination       The destination to navigate to.
     * @param currentPosition   The robots current position.
     * @param currentDirection  The robots current direction.
     */
    private void dispatch(Robot robot, Node destination, Node currentPosition, Room.Direction currentDirection) {
        System.out.println("Going to " + destination.getX().toString() + ", " + destination.getY().toString());

        @SuppressWarnings({"unchecked"}) // We know the return type will be ArrayList<Node> since we supply the nodes ourselves.
        ArrayList<Node> path = (ArrayList<Node>) this.pathFinder.shortestPath(currentPosition, destination, this.room);

        ArrayList<Command> commands = CommandFactory.commandsFromPath(path, currentDirection);

        this.executeCommands(robot, commands);
    }

    /**
     * Executes commands on the robot synchronously.
     *
     * Note: Blocking.
     *
     * @param robot     The robot to execute on.
     * @param commands  The commands to execute.
     */
    private void executeCommands(Robot robot, ArrayList<Command> commands) {
        for (int i = 0; i < commands.size(); i++) {
            if (this.manualMode) {
                break;
            }

            while (true) {
                Command command = commands.remove(0);
                if (!this.robot.setCurrentCommand(command)) {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {}
                }
            }
        }
    }

}

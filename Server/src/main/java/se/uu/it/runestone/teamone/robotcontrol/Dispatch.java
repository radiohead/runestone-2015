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

    private Boolean abortCurrentJob;
    private Boolean executing;

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
        this.executing = false;
    }

    @Override
    public void run() {
        Job job;

        while (true) {
            if (this.manualMode) {
                this.dispatch(this.robot, this.manualDestination);
            } else if ((job = scheduler.nextJob()) != null) {
                this.dispatch(this.robot, job);
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
        this.manualDestination = destination;
        this.abortCurrentJob = true;
    }

    /**
     * Dispatches a robot on a job. Will remain in charge of the robot
     * until the job is done, when the robot is returned. No other
     * dispatch should communicate with the robot during this time.
     *
     * @param robot The robot to dispatch.
     * @param job   The job to execute.
     */
    private void dispatch(Robot robot, Job job) {
        System.out.println("Going to node matching reqs.");

        @SuppressWarnings({"unchecked"}) // We know the return type will be ArrayList<Node> since we supply the nodes ourselves.
        ArrayList<Node> path = (ArrayList<Node>) this.pathFinder.shortestPathToNodeMatchingRequirements(robot.getCurrentPosition(), job.goods.getRequirements(), this.room);

        ArrayList<Command> commands = CommandFactory.commandsFromPath(path, robot.getCurrentDirection());

        this.executeCommands(robot, commands);
    }

    /**
     * Dispatches a robot to navigate to a destination.
     *
     * @param robot         The robot to dispatch.
     * @param destination   The destination to navigate to.
     */
    private void dispatch(Robot robot, Node destination) {
        System.out.println("Going to " + destination.getX().toString() + ", " + destination.getY().toString());

        @SuppressWarnings({"unchecked"}) // We know the return type will be ArrayList<Node> since we supply the nodes ourselves.
        ArrayList<Node> path = (ArrayList<Node>) this.pathFinder.shortestPath(robot.getCurrentPosition(), destination, this.room);

        ArrayList<Command> commands = CommandFactory.commandsFromPath(path, robot.getCurrentDirection());

        this.executeCommands(robot, commands);
    }

    /**
     * Executes commands on the robot synchronously.
     *
     * Note: Blocking.
     *
     * @param robot     The robot to execute on.
     * @param commands  The commands to execute.
     *
     * @return Whether the commands were executed successfully.
     */
    private Boolean executeCommands(Robot robot, ArrayList<Command> commands) {
        if (this.executing) {
            System.out.println("Dispatch - already executing. Not taking on new commands.");
            return false;
        }

        this.executing = true;
        System.out.println("Dispatch - executing commands..");

        for (int i = 0; i < commands.size(); i++) {
            if (this.abortCurrentJob) {
                System.out.println("Dispatch - aborting current job.");
                this.abortCurrentJob = false;
                this.executing = false;
                return false;
            }

            Command command = commands.remove(0);
            while (true) {
                if (this.robot.setCurrentCommand(command)) {
                    System.out.println("Dispatch - executing command " + command.toString());
                } else {
                    try {
                        Thread.sleep(200);
                    } catch (Exception e) {}
                }
            }
        }

        this.executing = false;
        System.out.println("Dispatch - execution complete.");

        return true;
    }

}

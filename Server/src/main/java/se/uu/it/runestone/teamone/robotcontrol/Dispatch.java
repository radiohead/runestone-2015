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
        this.abortCurrentJob = false;
        this.manualMode = false;
        this.manualDestination = null;
    }

    @Override
    public void run() {
        Job job;

        System.out.println("Dispatch - Running main loop.");

        while (true) {
            if (this.manualMode && this.manualDestination != null) {
                System.out.println("Dispatch - Starting manual job with destination (" +
                        this.manualDestination.getX().toString() + ", " + this.manualDestination.getY().toString() + ").");

                this.dispatch(this.robot, this.manualDestination);
            } else if (!this.manualMode && (job = scheduler.nextJob()) != null) {
                System.out.println("Dispatch - Starting next job in queue.");

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
        System.out.println("Dispatch - Entering manual mode.");
        this.manualMode = true;
        this.manualDestination = destination;
        this.abortCurrentJob = true;
    }

    public void releaseManualMode() {
        System.out.println("Dispatch - Releasing manual mode.");
        this.manualMode = false;
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
        @SuppressWarnings({"unchecked"}) // We know the return type will be ArrayList<Node> since we supply the nodes ourselves.
        ArrayList<Node> path = (ArrayList<Node>) this.pathFinder.shortestPathToNodeMatchingRequirements(robot.getCurrentPosition(), job.goods.getRequirements(), this.room);
        ArrayList<Command> commands = CommandFactory.commandsFromPath(path, robot.getCurrentDirection());
        this.executeCommands(commands);
    }

    /**
     * Dispatches a robot to navigate to a destination.
     *
     * @param robot         The robot to dispatch.
     * @param destination   The destination to navigate to.
     */
    private void dispatch(Robot robot, Node destination) {
        @SuppressWarnings({"unchecked"}) // We know the return type will be ArrayList<Node> since we supply the nodes ourselves.
        ArrayList<Node> path = (ArrayList<Node>) this.pathFinder.shortestPath(robot.getCurrentPosition(), destination, this.room);
        ArrayList<Command> commands = CommandFactory.commandsFromPath(path, robot.getCurrentDirection());
        this.executeCommands(commands);
        System.out.println(String.format("Robot is in: %s, %s", this.robot.getCurrentPosition().getX(), this.robot.getCurrentPosition().getY()));
    }

    /**
     * Executes commands on the robot synchronously.
     *
     * Note: Blocking.
     *
     * @param commands  The commands to execute.
     *
     * @return Whether the commands were executed successfully.
     */
    private Boolean executeCommands(ArrayList<Command> commands) {
        if (this.executing) {
            System.out.println("Dispatch - Already executing. Not taking on new commands.");
            return false;
        }

        this.executing = true;
        this.abortCurrentJob = false;

        System.out.println("Dispatch - Preparing to execute " + commands.size() + " commands.");

        if (this.abortCurrentJob) {
            System.out.println("Dispatch - aborting current job.");
            this.abortCurrentJob = false;
            this.executing = false;
            return false;
        }

        for (Command command : commands) {
            boolean executed = this.robot.setCurrentCommand(command);
            while(!executed) {
                executed = this.robot.setCurrentCommand(command);
            }
            while(this.robot.getCurrentCommand() != null);
            System.out.print("Dispatch - Executed command " + command.toString());
        }

        this.executing = false;
        this.manualDestination = null;
        System.out.println("Dispatch - Execution complete.");

        return true;
    }
}

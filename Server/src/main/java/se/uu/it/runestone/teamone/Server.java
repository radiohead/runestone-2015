package se.uu.it.runestone.teamone;

import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.pathfinding.PathFinder;
import se.uu.it.runestone.teamone.robotcontrol.Dispatch;
import se.uu.it.runestone.teamone.robotcontrol.Robot;
import se.uu.it.runestone.teamone.scheduler.Job;
import se.uu.it.runestone.teamone.scheduler.Scheduler;

import java.util.ArrayList;

/**
 * The main server that hooks into robot control,
 * the map, the pathfinder and communicates with the sensors.
 *
 * Note: This class is not yet thread safe.
 *
 * @author Åke Lagercrantz
 */
public class Server implements Runnable {

    private Room map;
    private PathFinder pathFinder;
    private Scheduler scheduler;
    private Dispatch dispatch;

    private ArrayList<Robot> robots;

    /**
     * The designated initializer. Creates a new server.
     */
    public Server() {
        this.map = new Room(10,10); // TODO: Add sensors <akelagercrantz>
        this.pathFinder = new PathFinder();
        this.scheduler = new Scheduler();

        this.robots = new ArrayList<Robot>(1);
        this.robots.add(new Robot()); // TODO: Give robot coms info. <akelagercrantz>

        this.dispatch = new Dispatch(map, pathFinder);
    }

    @Override
    public void run() {
        while (true) {
            // if (this.robotControl.hasAvailableRobot &&
            //     this.scheduler.hasJobsWaiting()) {
            //     Job job = this.scheduler.nextJob();
            //     this.robotControl.handleJob(job);
            // }
            // TODO: Uncomment above and modify to fit robot control methods. <akelagercrantz>
        }
    }
}

package se.uu.it.runestone.teamone;

import se.uu.it.runestone.teamone.map.Map;
import se.uu.it.runestone.teamone.pathfinding.PathFinder;
import se.uu.it.runestone.teamone.scheduler.Job;
import se.uu.it.runestone.teamone.scheduler.Scheduler;

import java.util.Queue;

/**
 * The main server that hooks into robot control,
 * the map, the pathfinder and communicates with the sensors.
 *
 * @author Åke Lagercrantz
 */
public class Server implements Runnable {

    private Map map;
    private PathFinder pathFinder;
    private Scheduler scheduler;

    /**
     * The designated initializer. Creates a new server.
     */
    public Server() {
        this.map = new Map(10,10,null); // TODO: Add sensors <akelagercrantz>
        this.pathFinder = new PathFinder();
        this.scheduler = new Scheduler();

        // TODO: Create robot control, and robot. <akelagercrantz>
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

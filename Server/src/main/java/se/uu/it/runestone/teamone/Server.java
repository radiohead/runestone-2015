package se.uu.it.runestone.teamone;

import se.uu.it.runestone.teamone.map.Node;
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.pathfinding.PathFinder;
import se.uu.it.runestone.teamone.robotcontrol.Dispatch;
import se.uu.it.runestone.teamone.robotcontrol.Robot;
import se.uu.it.runestone.teamone.scheduler.Scheduler;
import se.uu.it.runestone.teamone.socket.Listener;
import se.uu.it.runestone.teamone.socket.ListenerDelegate;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main server that hooks into robot control,
 * the map, the pathfinder and communicates with the sensors.
 *
 * Note: This class is not yet thread safe.
 *
 * @author Åke Lagercrantz
 */
public class Server extends Thread implements Runnable, ListenerDelegate {

    private Room map;
    private PathFinder pathFinder;
    private Scheduler scheduler;
    private Dispatch dispatch;
    private Listener listener;

    private Robot robot;

    /**
     * The designated initializer. Creates a new server.
     */
    public Server() {
        System.out.println("Server - Initializing server.");

        System.out.println("Server - Creating map.");
        this.map = new Room(10,10); // TODO: Addc sensors <akelagercrantz>
        System.out.println("Server - Creating pathfinder.");
        this.pathFinder = new PathFinder();
        System.out.println("Server - Creating scheduler.");
        this.scheduler = new Scheduler();

        System.out.println("Server - Creating robot.");
        this.robot = new Robot(); // TODO: Give robot coms info. <akelagercrantz>
        this.robot.setCurrentPosition(this.map.nodeFromCoordinates(0, 0));
        this.robot.setCurrentDirection(Room.Direction.EAST);
        Thread robotThread = new Thread(this.robot);
        System.out.println("Server - Spawning robot thread.");
        robotThread.start();

        System.out.println("Server - Creating dispatch.");
        this.dispatch = new Dispatch(this.robot, this.map, this.pathFinder, this.scheduler);
        Thread dispatchThread = new Thread(this.dispatch);
        System.out.println("Server - Spawning dispatch thread.");
        dispatchThread.start();

        System.out.println("Server - Creating api listener.");
        try {
            this.listener = new Listener(4444, this);
        } catch (IOException e) {
            System.out.println("Server - Could not create api listener.");
        }

        System.out.println("Server - Initialization complete.");
    }

    @Override
    public void run() {
        try {
            System.out.println("Server - Listening for api comms.");
            this.listener.listen();
        } catch (IOException e) {
            System.out.println("IOException in api listener");
        }
    }

    // Listener delegate
    @Override
    public Integer warehouseHeight() {
        return this.map.getDimY();
    }

    @Override
    public Integer warehouseWidth() {
        return this.map.getDimX();
    }

    @Override
    public void goTo(Integer x, Integer y) {
        Node node = this.map.nodeFromCoordinates(x, y);
        this.dispatch.setManualMode(node);
    }
}

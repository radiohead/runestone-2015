package se.uu.it.runestone.teamone;

import se.uu.it.runestone.teamone.map.Map;

/**
 * The main server that hooks into robot control,
 * the map, the pathfinder and communicates with the sensors.
 */
public class Server {

    /// The main map of the warehouse.
    private Map map;

    /**
     * The designated initializer. Creates a new server.
     */
    public Server() {
        this.map = new Map(10,10,null); // TODO: Add sensors <akelagercrantz>
    }

    /**
     * Dispatches a new thread where the server is run.
     */
    public void run() {
        // TODO: Dispatch thread, start communicating with sensors.
    }
}

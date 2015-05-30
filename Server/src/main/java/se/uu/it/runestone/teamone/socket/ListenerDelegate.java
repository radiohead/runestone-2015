package se.uu.it.runestone.teamone.socket;

import se.uu.it.runestone.teamone.climate.Sensor;
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.robotcontrol.Robot;

import java.util.ArrayList;

/**
 * The delegate of the listener. Supplies data
 * the listener needs for api comms.
 *
 * @author Åke Lagercrantz
 */
public interface ListenerDelegate {

    /**
     * Returns the width of the warehouse.
     *
     * @return The width of the warehosue.
     */
    Integer warehouseWidth();

    /**
     * Returns the height of the warehouse.
     *
     * @return The height of the warehouse.
     */
    Integer warehouseHeight();

    /**
     * Manual navigation of robot to x,y coordinate.
     *
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    void goTo(Integer x, Integer y);

    Robot getRobotInstance(int robotId);

    ArrayList<Sensor> getSensors();

    Room getRoom();
}

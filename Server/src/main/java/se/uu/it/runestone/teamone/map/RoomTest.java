package se.uu.it.runestone.teamone.map;

import se.uu.it.runestone.teamone.climate.Sensor;
import se.uu.it.runestone.teamone.pathfinding.PathFindingGraph;
import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;
import se.uu.it.runestone.teamone.pathfinding.PathFindingRequirements;

import java.util.ArrayList;

/**
 * A class implementing map representation of the storage facility.
 * 
 * @author Daniel Eliassen
 */

public class RoomTest {
	private ArrayList<Node> node;
	private ArrayList<Sensor> sensorList;
	private int dimX;
	private int dimY;

	public RoomTest(int xs, int ys){
		this.dimX=xs;
		this.dimY=ys;
        sensorList = new ArrayList<Sensor>();
        node = new ArrayList<Node>();
	}

	public int getDimY() {
		return dimY;
	}
	public int getDimX() {
		return dimX;
	}


}

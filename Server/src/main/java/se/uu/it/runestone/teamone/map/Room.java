package se.uu.it.runestone.teamone.map;

import java.util.ArrayList;

import se.uu.it.runestone.teamone.pathfinding.PathFindingGraph;
import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;
import se.uu.it.runestone.teamone.pathfinding.PathFindingRequirements;
import se.uu.it.runestone.teamone.climate.Sensor;

/**
 * A class implementing map representation of the storage facility.
 * 
 * @author Daniel Eliassen
 */

public class Room implements PathFindingGraph {

	/**
	 * Represents a direction the robot can face. Does not
	 * map to actual direction, but rather the orientation of
	 * the warehouse, where north is up and south is down
	 * on the grid map.
	 *
	 * @author Åke Lagercrantz
	 */
	public enum Direction {
		NORTH,
		WEST,
		SOUTH,
		EAST,
		NONE
	}

	private ArrayList<Node> node;
	private ArrayList<Sensor> sensorList;
    private ArrayList<ArrayList<Node>> quadrant;
	private int dimX;
	private int dimY;
	private int id;
	public int getIdentity() {
        return this.id;
    }

    public void setIdentity(int id) {
        this.id = id;
    }

	public Room(int xs, int ys/*, ArrayList<Sensor> sensorList*/){
        Sensor sensor1 = new Sensor("201412120332",new Coordinate(0,0));
        Sensor sensor2 = new Sensor("344DF7A812A0",new Coordinate(0,this.getDimY()));
        Sensor sensor3 = new Sensor("344DF7A812A0",new Coordinate(this.getDimX(), this.getDimY()));
        sensorList.add(sensor1);
        sensorList.add(sensor2);
        sensorList.add(sensor3);

        if(!(xs >= 0 && ys >= 0 ))
            System.out.println("Room cannot be this small.");
        if(sensorList != null){
            for(Sensor sensor : sensorList){
                if(!(sensor.getX() <= xs && sensor.getY() <=ys))
                    System.out.println("Sensor outside grid.");
            }
            this.sensorList = sensorList;
        }

		this.dimX=xs;
		this.dimY=ys;
        if(this.assignNodes()){
            System.out.println("Room - Sensor assignment completed..");
        } else {
            System.out.println("Room - No sensors, aborting assignment of nodes.");
        }
        //sensorList = new ArrayList<Sensor>();
        node = new ArrayList<Node>();
        Node temp = null;
		for(int i=0;i<ys;i++){
			for(int j=0;j<xs;j++){
                if ((j == 2 && i != this.getDimY() % 8 ) || ( j == 6 && i != this.getDimY()-4) ){
                    System.out.println("Room - Setting ["+j+","+i+"] to obstructed");
                    temp = new Node(new Coordinate(j,i),true);
                } else {
                    temp = new Node(new Coordinate(j,i),false);
                }
                this.node.add(temp);
            }
		}
	}
    public Boolean assignNodes(){
        if(this.sensorList == null){
            return false;
        }
        int midx = this.getDimX() % 2;
        int midy = this.getDimY() % 2;

        ArrayList<Node> quadrant1 = new ArrayList<Node>();
        ArrayList<Node> quadrant2 = new ArrayList<Node>();
        ArrayList<Node> quadrant3 = new ArrayList<Node>();
        ArrayList<Node> quadrant4 = new ArrayList<Node>();

        for(Node n : this.node){
            if(n.getX() <= midx && n.getX() > 0){
                // Left-side
                if(n.getY() <= midy){
                    quadrant1.add(n);
                } else {
                    quadrant2.add(n);
                }
            } else if(n.getX() < this.getDimX() && n.getX() > midx){
                // Right-side
                if(n.getY() <= midy){
                    quadrant4.add(n);
                } else {
                    quadrant3.add(n);
                }
            }
        }
        this.quadrant.add(1,quadrant1);
        this.quadrant.add(2,quadrant2);
        this.quadrant.add(3,quadrant3);
        this.quadrant.add(4,quadrant4);
        return true;
    }

	public int getDimY() {
		return dimY;
	}
	public int getDimX() {
		return dimX;
	}
	public void setX(int newX){
		this.dimX = newX;
	}
    public ArrayList<Node> getNode(){return node;}
    public ArrayList<Sensor> getSensorList(){return sensorList;}


    public Node nodeFromCoordinates(Integer x, Integer y) {
        return node.get(this.getDimY()*y + x);
    }

    /*@Override
	public ArrayList<PathFindingNode> neighbours(PathFindingNode node) {
		// TODO Auto-generated method stub
		int index = this.node.indexOf(node);
		Node left = this.node.get(index-1);
		Node right= this.node.get(index+1);
		Node top = this.node.get(index - dimX);
		Node bottom = this.node.get(index + dimX);
		ArrayList<PathFindingNode> result = new ArrayList<PathFindingNode>();
		result.add(top);
		result.add(left);
		result.add(bottom);
		result.add(right);
		return result;
	}*/

	/**
	 * Returns the navigational direction in the warehouse when
	 * navigating between two nodes.
	 *
	 * @author Åke Lagercrantz
	 *
	 * @param from  The node to start from.
	 * @param to	The node to navigate to, which determines the direction.
	 *
	 * @return The direction required to navigate between the nodes.
	 */
	public static Direction direction(Node from, Node to) {

		if (from.getX() > to.getX()) {
			return Direction.WEST;
		} else if (from.getX() < to.getX()) {
			return Direction.EAST;
		} else if (from.getY() > to.getY()) {
			return Direction.NORTH;
		} else if (from.getY() > to.getY()) {
			return Direction.SOUTH;
		} else {
			return Direction.NONE;
		}
	}

    @Override
    public ArrayList<PathFindingNode> neighbours(PathFindingNode node) {
        Integer x = ((Node) node).getX();
        Integer y = ((Node) node).getY();

        ArrayList<PathFindingNode> neighbours = new ArrayList<PathFindingNode>();

        // Top
        if (y > 0) {
            neighbours.add(this.nodeFromCoordinates(x, y-1));
        }
        // TurnLeft
        if (x > 0) {
            neighbours.add(this.nodeFromCoordinates(x-1, y));
        }
        // Bottom
        if (y < this.getDimY() - 1) {
            neighbours.add(this.nodeFromCoordinates(x, y+1));
        }
        // TurnRight
        if (x < this.getDimX() - 1) {
            neighbours.add(this.nodeFromCoordinates(x+1, y));
        }

        return neighbours;
    }

	@Override
	public synchronized Integer cost(PathFindingNode node, PathFindingNode neighbour) {
	ArrayList<PathFindingNode> neighbours = neighbours(node);
		if(neighbours.contains(neighbour) && neighbour.getObstructed() == false){
			return 1;
		} else	{
			return -1;
		}
	}

	@Override
	public synchronized Integer distance(PathFindingNode a, PathFindingNode b) {
		return Math.abs(a.getX() - b.getX())+Math.abs(a.getY() - b.getY());
	}

	@Override
	public synchronized Boolean nodeMeetsRequirements(PathFindingNode node,	PathFindingRequirements requirements) {
		// TODO Auto-generated method stub
        return ((Node)node).getX() == 9 && ((Node) node).getY() == 2;
	}

    @Override
    public synchronized void didVisitNode(PathFindingNode node) {
        ((Node)node).setVisited(true);
        //System.out.println(this);
    }

	@Override
	public String toString() {
		return this.toString(null);
	}

	public String toString(ArrayList<? extends PathFindingNode> path) {
		String map = "";

		for (Integer i = 0; i < this.getDimX(); i++) {
			map += "---";
		}
		map += "\n";

		for (Integer y = 0; y < this.getDimY(); y++) {
			for (Integer x = 0; x < this.getDimX(); x++) {
				Node node = (Node)this.nodeFromCoordinates(x,y);
				map += node.toString(path);
			}
			map += "\n";
		}

		for (Integer i = 0; i < this.getDimX(); i++) {
			map += "---";
		}

		return map;
	}

    /**
     * Call this method to force climate update
     * along all nodes in the grid.
     * @param propagationDrop
     */
    public void propagateClimate(Double propagationDrop){
        if(propagationDrop >= 1 && propagationDrop <= 0.01)
            return; // Diminishing return must be less then 100% and more 1% for each tile
        /* Somehow we need to know what nodes
           a sensor is responsible for updating.
           Perhaps a method assigns nodes to a
           sensor when room is initiated.

           Then this method only runs through
           sensor.nodes and attaches a new
           climate value that is sensor-value
           * distance * propagationDrop.
           Maybe we could use the visited flag
           to iterate through the nodes making
           and making sure they have been assigned
           to a sensor. Not sure this is time-efficient
           but at least it seems feasible.
         */
        double avgT=0, avgL=0, avgH=0;
        for(Sensor s : sensorList){
            avgT += s.getTemperature();
            avgH += s.getHumidity();
            avgL += s.getLight();
        }
        avgT = avgT/3;
        avgL = avgL/3;
        avgH = avgH/3;
        // Fourth quadarant without sensor, all nodes set to average.
        for(Node n : quadrant.get(sensorList.size()+1)){
            n.update(avgH,avgL,avgT);
        }

        // Other nodes updated dynamically to average conditions.
        for(int i = 1;i<=sensorList.size();i++){
            for(Node node : quadrant.get(i)){
                updateClimate(sensorList.get(i),node, avgH, avgL, avgT, propagationDrop);
            }
        }

    }

    /**
     * Will update temperature of Node in relation to average temperature and distance to sensor.
     * @param sensor
     * @param node
     * @param refHumidity
     * @param refLight
     * @param refTemperature
     * @param factor
     */
	public void updateClimate(Sensor sensor, Node node, Double refHumidity, Double refLight, Double refTemperature, Double factor){
        Double sHumidity = sensor.getHumidity();
        Double sLight = sensor.getLight();
        Double sTemp = sensor.getTemperature();
        int distance  = this.distance(this.nodeFromCoordinates(sensor.getX(), sensor.getY()),node);
        if(distance == 0) { node.humidity= sHumidity; node.light = sLight;node.temp = sTemp; return;}
        node.humidity -= (sHumidity - refHumidity)*factor;
        node.light -= (sLight - refLight)*factor;
        node.temp -= (sTemp - refTemperature)*factor;
        return;
	}
}

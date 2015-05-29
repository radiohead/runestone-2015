package se.uu.it.runestone.teamone.map;

import java.util.ArrayList;

import se.uu.it.runestone.teamone.pathfinding.PathFindingGraph;
import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;
import se.uu.it.runestone.teamone.pathfinding.PathFindingRequirements;
import se.uu.it.runestone.teamone.climate.Sensor;

/**
 * A class implementing map representation of the storage
 * facility. The facility will be of a square or rectangular
 * shape with the dimensions x and y.
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
    private ArrayList<Node> quadrant1;
    private ArrayList<Node> quadrant2;
    private ArrayList<Node> quadrant3;
    private ArrayList<Node> quadrant4;
    private ArrayList<ArrayList<Node>> quadrant = new ArrayList<ArrayList<Node>>();
    private int dimX;
	private int dimY;
	private int identity;

	public int getIdentity() {
        return this.identity;
    }
    public void setIdentity(int id) {
        this.identity = id;
    }

    /**
     * Constructor for the Room-class, requires the dimensions
     * for the room upon instantiation.
     * @param xs Dimension of the room on the x-axis.
     * @param ys Dimension of the room on the y-axis.
     */
	public Room(int xs, int ys/*, ArrayList<Sensor> sensorList*/){
        final Sensor sensor1 = new Sensor(1,"/dev/tty.HC-06-DevB",new Coordinate(0,0));
        final Sensor sensor2 = new Sensor(2,"/dev/tty.HC-06-DevB-1",new Coordinate(0,this.getDimY()));
        final Sensor sensor3 = new Sensor(3,"/dev/tty.HC-06-DevB-2",new Coordinate(this.getDimX(), this.getDimY()));

        Thread thread = new Thread(){
            public void run(){
                System.out.println("Thread Running");
                Thread t1 = new Thread(sensor1);
                Thread t2 = new Thread(sensor2);
                Thread t3 = new Thread(sensor3);
                t1.start();
                t2.start();
                t3.start();
                try {
                    Thread.sleep(15000);
                } catch(Exception e){e.printStackTrace();}
            }
        };

        thread.start();

        sensorList = new ArrayList<Sensor>();
        sensorList.add(sensor1);
        sensorList.add(sensor2);
        sensorList.add(sensor3);
        this.quadrant.add(this.quadrant1);
        this.quadrant.add(this.quadrant2);
        this.quadrant.add(this.quadrant3);
        this.quadrant.add(this.quadrant4);

        if(!(xs >= 0 && ys >= 0 ))
            System.out.println("Room cannot be this small.");
        if(sensorList != null){
            for(Sensor sensor : sensorList){
                if(!(sensor.getX() <= xs && sensor.getY() <=ys))
                    System.out.println("Sensor outside grid.");
            }
        }

		this.dimX=xs;
		this.dimY=ys;

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
        if(this.sensorList != null){
            this.assignNodes();
            System.out.println("Room - Sensor assignment completed..");
        } else {
            System.out.println("Room - No sensors, aborting assignment of nodes.");
        }
	}

    /**
     * Divides all nodes into four quadrants by splitting
     * the matrix/grid/room in half on both the x- and
     * y-axis.
     */
    public void assignNodes(){

        int midx = this.getDimX() % 2;
        int midy = this.getDimY() % 2;

        this.quadrant1 = new ArrayList<Node>();
        this.quadrant2 = new ArrayList<Node>();
        this.quadrant3 = new ArrayList<Node>();
        this.quadrant4 = new ArrayList<Node>();

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
        return;
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

    /**
     * Retreive a node with given coordinates x and y.
     *
     * @author Daniel Eliassen
     *
     * @param x Location on the x-axis of the node.
     * @param y Location on the y-axis of the node.
     *
     * @return The Node if found null otherwise.
     */
    public Node nodeFromCoordinates(Integer x, Integer y) {
        if( (0 <= x && x <= this.getDimX()) && (0 <= y && y <= this.getDimY()) ) {
            return node.get(this.getDimY() * y + x);
        } else { return null; }
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
		} else if (from.getY() < to.getY()) {
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

    /**
     * Calculates the cost for moving from a node to its neighbour where
     * no node is more costly than any other.
     *
     * @param node        The node from which the movement takes place.
     * @param neighbour   The neighbour to which the movement takes place.
     *
     * @return Cost The actual cost as an integer value.
     */
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
     * @param propagationDrop The rate of which the climate dissipates from @link se.uu.it.runestone.teamone.map.Node to Node.
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
        for(Node n : this.quadrant4){
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
     * Updates the climate of all nodes based off climate in the
     * Master sensor and reference climates. The scaling factor
     * is used to calculate the climate drop between Nodes.
     *
     * @author Daniel Eliassen
     *
     * @param sensor Master sensor from which climate is taken.
     * @param node List of Nodes to update.
     * @param refHumidity Average humidity based on all sensors.
     * @param refLight Average lighting based on all sensors.
     * @param refTemperature Average temperature based on all
     *                       sensors.
     * @param factor Scaling factor which climate dissipates
     *               from one Node to another.
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

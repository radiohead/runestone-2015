package se.uu.it.runestone.teamone.map;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import se.uu.it.runestone.teamone.pathfinding.PathFindingGraph;
import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;
import se.uu.it.runestone.teamone.pathfinding.PathFindingRequirements;
import se.uu.it.runestone.teamone.climate.*;

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
	private int dimX;
	private int dimY;

	public Room(int xs, int ys){
		this.dimX=xs;
		this.dimY=ys;
        sensorList = new ArrayList<Sensor>();
        node = new ArrayList<Node>();
        Node temp = null;
		for(int i=0;i<ys;i++){
			for(int j=0;j<xs;j++){
                if ((j == 2 && i != this.getDimY() % 8 ) || ( j == 6 && i != this.getDimY()-4) ){
                    temp = new Node(j,i,true);
                    System.out.println("Setting ["+j+","+i+"] to obstructed");
                } else {
                    temp = new Node(j,i,false);
                }
                this.node.add(temp);
            }
		}
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
	public Integer cost(PathFindingNode node, PathFindingNode neighbour) {
	ArrayList<PathFindingNode> neighbours = neighbours(node);
		if(neighbours.contains(neighbour) && neighbour.getObstructed() == false){
			return 1;
		} else	{
			return -1;
		}
	}

	@Override
	public Integer distance(PathFindingNode a, PathFindingNode b) {
		return Math.abs(a.getX() - b.getX())+Math.abs(a.getY() - b.getY());
	}

	@Override
	public Boolean nodeMeetsRequirements(PathFindingNode node,	PathFindingRequirements requirements) {
		// TODO Auto-generated method stub
        return ((Node)node).getX() == 9 && ((Node) node).getY() == 2;
	}

    @Override
    public void didVisitNode(PathFindingNode node) {
        ((Node)node).setVisited(true);
        //System.out.println(this);
    }

	//// Printing ////

	@Override
	public String toString() {
		return this.toString(null);
	}

	public String toString(ArrayList<PathFindingNode> path) {
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


	private void updateTemp(){
		for(Sensor sensor : this.sensorList){
			// Used to retrieve data form sensor.
		}
	}

}

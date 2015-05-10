package se.uu.it.runestone.teamone.map;

import java.util.ArrayList;
import java.util.logging.Logger;

import se.uu.it.runestone.teamone.pathfinding.*;
import se.uu.it.runestone.teamone.climate.*;

/**
 * A class implementing map representation of the storage facility.
 * 
 * @author Daniel Eliassen
 */
public class Map extends Thread implements PathFindingGraph {
	private ArrayList<Node> node;
	private ArrayList<Sensor> sensorList;
	private int dimX;
	private int dimY;
	private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	public Map(int xs, int ys, ArrayList<Sensor> sensor){
		this.dimX=xs;
		this.dimY=ys;
		for(int i=0;i<xs;i++){
			for(int j=0;j<ys;j++){
				this.node.add(new Node(i,j,null));
			}
		}
	}
	
	public int getDimY() {
		return dimY;
	}
	public int getDimX() {
		return dimX;
	}

	@Override
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
	}

	@Override
	public int cost(PathFindingNode node, PathFindingNode neighbour) {
	ArrayList<PathFindingNode> neighbours = neighbours(node);
		if(neighbours.contains(neighbour) && neighbour.getItemsStored() != 8){
			return 1;
		} else	{
			return -1;
		}
	}

	@Override
	public int distance(PathFindingNode a, PathFindingNode b) {
		return Math.abs(a.getX() - b.getX())+Math.abs(a.getY() - b.getY());
	}

	@Override
	public Boolean nodeMeetsRequirements(PathFindingNode node,	PathFindingRequirements requirements) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private void updateTemp(){
		for(Sensor sensor : this.sensorList){
			sensor.update();
		}
	}
	
	public void run(){
		this.log.log(null,"[ROOM"+this.getName()+"]: " + this.getDimX() + "x"+ this.getDimY() + "-room created.");
		while(true){
				try {
					Thread.sleep(15000);
					log.log(null,"[ROOM"+this.getName()+"]: Updating temperature from sensors.");
					this.updateTemp();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					this.log.log(null,"[ROOM"+this.getName()+"]: Exited, InterruptedException.");	
					e.printStackTrace();
				}
		}
	}
	
}

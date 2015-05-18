package se.uu.it.runestone.teamone.map;
import se.uu.it.runestone.teamone.pathfinding.*;

import java.util.ArrayList;

/**
 * A class implementing representation of coordinate in the Map.
 * 
 * @author Daniel Eliassen
 */

public class Node implements PathFindingNode{
	private int x = -1, y = -1;
    private Coordinate placement=null;
	private Boolean visited;
	private Boolean obstructed;

    public double temp = -1;
    public double light = -1;
    public double humidity = -1;

	
	public Node (Coordinate placement, Boolean obstructed) {
	        this.placement  = placement;
            this.setObstructed(obstructed);
            this.setVisited(false);
		}
	@Override public Integer getX(){
		return this.placement.getX();
	}
	@Override public Integer getY() { return this.placement.getY(); }

	public Boolean equals(PathFindingNode otherPosition){
		return (otherPosition.getX() == this.getX() && otherPosition.getY() == this.getY());
	}
    @Override public Boolean getVisited(){
		return this.visited;
	}
    @Override public Boolean getObstructed(){
		return this.obstructed;
	}
    public void setVisited(Boolean bool){
        this.visited = bool;
    }
    public void setObstructed(Boolean bool){
        this.obstructed = bool;
    }

    @Override
    public String toString() {
        return this.toString(null);
    }
    @Override
    public String toString(ArrayList<? extends PathFindingNode> path) {
        if (path != null && path.contains(this)) {
            return " s ";
        } else if (this.getVisited()) {
            return " - ";
        } else if (this.getObstructed()) {
            return " o ";
        } else {
            return "   ";
        }
    }

    public void update(Double humidity, Double light, Double temperature) {
		this.humidity = humidity;
		this.light = light;
		this.temp = temperature;
	}

}
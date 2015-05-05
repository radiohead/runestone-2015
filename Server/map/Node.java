package se.uu.it.runestone.teamone.map;
import se.uu.it.runestone.teamone.climate.Sensor;
import se.uu.it.runestone.teamone.pathfinding.*;

/**
 * A class implementing representation of coordinate in the Map.
 * 
 * @author Daniel Eliassen
 */
public class Node implements PathFindingNode{
	private int x=-1,y=-1;
	private int stored=0;
	private double temp=-1;
	private double light=-1;
	private Sensor sensor=null;
	
	
	public Node (int x, int y, Sensor sensor) throws IllegalArgumentException{
		if(x>=0 && y>=0){
			this.x = x;
			this.y = y;
			this.setSensor(sensor);
		}else{
			throw new IllegalArgumentException();
			}
		}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public boolean equalsTo(Node otherPosition){
		if(otherPosition.getX() == this.getX() && otherPosition.getY() == this.getY()){
		return true;
		} else {
		return false;
		}
	}
	public int getItemsStored() {
		return stored;
	}
	public void setItemsStored(int stored) {
		this.stored = stored;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public double getLight() {
		return light;
	}
	public void setLight(double light) {
		this.light = light;
	}
	public Sensor getSensor() {
		return sensor;
	}
	private void setSensor(Sensor sensor) {
		this.sensor = sensor;
	}
}
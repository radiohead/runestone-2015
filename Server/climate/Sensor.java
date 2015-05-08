package se.uu.it.runestone.teamone.climate;
import java.util.Random;

/**
 * A class implementing representation of climate sensor.
 * 
 * @author Daniel Eliassen
 */
public class Sensor {
	int id;
	String address;
	public Sensor(int id, String address){
		this.id = id;
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	public double getTemp(){
		return new Random().nextDouble();
	}
	public void update(){};

}

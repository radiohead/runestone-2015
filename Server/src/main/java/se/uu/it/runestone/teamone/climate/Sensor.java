package se.uu.it.runestone.teamone.climate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import se.uu.it.runestone.teamone.map.Coordinate;
import se.uu.it.runestone.teamone.map.Node;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * Sensor implementation class. Describes a sensor and connects via serial bluetooth
 * from the server to it's physical counterpart to relay updated sensor data to the node
 * it has been assigned.
 * @author Daniel Eliassen
 *
 */

public class Sensor extends Thread implements SerialPortEventListener{


	private static final int TIME_OUT = 2000;
	private static final int BAUD_RATE = 9600;

	String sensorName = null;
	SerialPort connection = null;
	Node node = null;
	private BufferedReader input = null;
	private OutputStream output = null;
	private Coordinate placement;
	private Double humidity=(double) 666;
	private Double light=(double) 666;
	private Double temperature=(double) 666;

	public Double getTemperature() {
		return temperature;
	}

	public Double getLight() {
		return light;
	}

	public Double getHumidity() {
		return humidity;
	}

	public Sensor(String sensor, Coordinate placement){
		this.sensorName = sensor;
		this.placement = placement;
	}

	private Boolean init(){
		try{
			CommPortIdentifier port = CommPortIdentifier.getPortIdentifier(sensorName);
			SerialPort handle = (SerialPort) port.open("whyBother?",TIME_OUT);
			if(handle != null){
				this.connection = handle;
			}
			this.connection.setSerialPortParams(BAUD_RATE,SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
			this.connection.addEventListener(this);
			this.connection.notifyOnDataAvailable(true);
			System.out.println("Sensor - Waiting for connection.");
			Thread.sleep(2000);
		} catch(Exception e){
			System.out.println("Sensor - Failed to open connection to " + this.sensorName + ", " + e.getMessage());
		}
		return !(this.connection == null);
	}

	public void requestData(){
		try {
			if(output == null){
				output = connection.getOutputStream();
			}
			output.write("a".getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void run(){
		System.out.println("Sensor - initating.");
		init();
	}

	public void close(){
		this.connection.close();
	}

	@Override
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		ArrayList<Double> values = new ArrayList<Double>();
		//System.out.println("Event received: " + oEvent.toString());
		try {
			switch (oEvent.getEventType() ) {
				case SerialPortEvent.DATA_AVAILABLE:
					if ( input == null ) {
						input = new BufferedReader(
								new InputStreamReader(
										connection.getInputStream()));
					}
					String inputLine = input.readLine();
					System.out.println("Sensor - Incoming <- " + inputLine);
					// These should be parsed from sensor message.
					values = parseClimate(inputLine);
					if(values.size() >= 3) {
						//System.out.println("Parsing data: Value1: " + values.get(0) +", Value2: "+ values.get(1) +", Value3: "+ values.get(2));
						this.humidity = (values.get(2));
						this.light = (values.get(0));
						this.temperature = (values.get(1));

					} else{
						System.out.println("Sensor - Too few values parsed from sensor.");
					}

					break;

				default:
					System.out.println("Sensor - Incoming <- No data available.");
					break;
			}
		}
		catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	public ArrayList<Double> parseClimate(String contents){
		ArrayList < Double > result = new ArrayList < Double >();
		Matcher m = Pattern.compile( "(?<!R)[-+]?\\d*\\.?\\d+([eE][-+]?\\d+)?" ).matcher(contents);

		while ( m.find() )
		{
			double element = Double.parseDouble( m.group() );
			result.add( element );
		}

		return result;
	}
	public Integer getX(){ return this.placement.getX();}
	public Integer getY(){ return this.placement.getY();}
}
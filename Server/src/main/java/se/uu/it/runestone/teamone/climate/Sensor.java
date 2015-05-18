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
	private Double humidity;
	private Double light;

    public Double getTemperature() {
        return temperature;
    }

    public Double getLight() {
        return light;
    }

    public Double getHumidity() {
        return humidity;
    }

    private Double temperature;


	public Sensor(String sensor, Coordinate placement){
		this.sensorName = sensor;
		this.placement = placement;
	}
	
	private Boolean init(){
		try{
		CommPortIdentifier port = CommPortIdentifier.getPortIdentifier(sensorName);
		SerialPort handle = (SerialPort) port.open("whyBother?",TIME_OUT);
		if(handle != null){
			connection = handle;
		}
		connection.setSerialPortParams(BAUD_RATE,SerialPort.DATABITS_8,
				SerialPort.STOPBITS_1,SerialPort.PARITY_NONE);
		
			connection.addEventListener(this);
			connection.notifyOnDataAvailable(true);
		Thread.sleep(2000);
		} catch(Exception e){
			System.out.println("Failed to open connection to " + this.sensorName + ", " + e.getMessage());
		}
		return !(connection == null);
	}
	
	private void requestData(){
			try {
				if(output == null){
					output = connection.getOutputStream();
				}
				output.write("request\n".getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void run(){
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
                    System.out.println("Incoming <- " + inputLine);
                    // These should be parsed from sensor message.
                    values = parseClimate(inputLine);
                    if(values.size() >= 3) {
						this.humidity = (values.get(3));
                        this.light = (values.get(1));
                        this.temperature = (values.get(2));

                    } else{
                        System.out.println("Too few values parsed from sensor.");
                    }
                    break;

                default:
                	System.out.println("Incoming <- No data available.");
                    break;
            }
        } 
        catch (Exception e) {
            System.err.println(e.toString());
        }
    }

	private ArrayList<Double> parseClimate(String contents){
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
	

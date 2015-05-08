package se.uu.it.runestone.teamone.bt;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.io.IOException;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;

import se.uu.it.runestone.teamone.climate.Sensor;
/**
 * A class that implements BT discovery.
 * 
 * @author Daniel Eliassen
 *
 */

public class FindConnection extends Thread implements DiscoveryListener{
	private static Object lock=new Object();
	private ArrayList<RemoteDevice> devices = new ArrayList<RemoteDevice>();
	private ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	private Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public FindConnection(ArrayList<Sensor> sensor) { this.sensors.addAll(sensor); }

	/**
	 * Call back method that verifies that found devices should work with
	 * predefined list of sensors.
	 * 
	 */
	@Override
	public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
		System.out.println("Device discovered: "+btDevice.getBluetoothAddress());
		//add the device to the vector
		Sensor sensor=null;
		System.out.println("#Sensors: "+ sensors.size() + ", contains: "+ sensors.iterator().toString());
		for(int i=0;i<sensors.size();i++){
			sensor=sensors.get(i);
			if(sensor.getAddress().equals(btDevice.getBluetoothAddress())){
				System.out.println("Found matching: Device "+btDevice.getBluetoothAddress() + " and Sensor: " + sensor.getAddress());
				devices.add(btDevice);
				System.out.println("FindConnection: Added remoteDevice, " + btDevice.getBluetoothAddress());
				sensors.remove(sensor);
			}
		}
		
	}

	public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {}
	public void serviceSearchCompleted(int transID, int respCode) {}

	/**
	 * This callback method will be called when the device discovery is
	 * completed.
	 */
	public void inquiryCompleted(int discType) {
		synchronized(lock){ lock.notify(); }
	
		switch (discType) {
			case DiscoveryListener.INQUIRY_COMPLETED :
					System.out.println("[Agent] INQUIRY_COMPLETED");
					break;
			case DiscoveryListener.INQUIRY_TERMINATED :
					System.out.println("[Agent] INQUIRY_TERMINATED");
					break;
			case DiscoveryListener.INQUIRY_ERROR :
					System.out.println("[Agent] INQUIRY_ERROR");
					break;
			default :
					System.out.println("[Agent] Unknown Response");
					break;
		}
	}
		//main method of the application
	public void run() {		
		FindConnection bluetoothDeviceDiscovery = new FindConnection(sensors);
		LocalDevice localDevice = null;
		try {
			localDevice = LocalDevice.getLocalDevice();
		} catch (BluetoothStateException bse) {
			// TODO Auto-generated catch block
			bse.printStackTrace();
		}
		System.out.println("[Agent] Server address: "+localDevice.getBluetoothAddress() + " and name: "+localDevice.getFriendlyName());
	
		//find devices
		DiscoveryAgent agent = localDevice.getDiscoveryAgent();
		System.out.println("[Agent] Starting device inquiry...");
		try {
			agent.startInquiry(DiscoveryAgent.GIAC, bluetoothDeviceDiscovery);
		} catch (BluetoothStateException bse) {
			// TODO Auto-generated catch block
			bse.printStackTrace();
		}
	
		try {
			synchronized(lock){
				lock.wait();
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	
		System.out.println("[Agent] Device inquiry completed. ");
	
		//print all devices
		int deviceCount=devices.size();
	
		if(deviceCount <= 0){
			System.out.println("[Agent] No devices found .");
		}
		else{
			System.out.println("[Agent] Bluetooth Devices: ");
			for (int i = 0; i < deviceCount; i++) {
				RemoteDevice remoteDevice = (RemoteDevice) devices.get(i);
				try {
					String friendlyName = remoteDevice.getFriendlyName(true);
					System.out.println((i+1)+". "+remoteDevice.getBluetoothAddress()+" ("+friendlyName+")");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	}
}

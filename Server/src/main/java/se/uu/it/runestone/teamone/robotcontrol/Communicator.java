package se.uu.it.runestone.teamone.robotcontrol;

import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTCommFactory;
import java.io.*;

import se.uu.it.runestone.teamone.robotcontrol.command.Command;

/**
 * Communicates with a physical robot.
 *
 * @author Åke Lagercrantz
 */
public class Communicator {
    String brickName;
    String brickAddress;

    NXTConnector brickConnection;
    InputStream inputFromBrick;
    OutputStream outputToBrick;

    /**
     * The designated initializer. Creates a new communicator and
     * establishes a connection with a physical robot.
     *
     * TODO: Add communication setting parameters needed to initialize communications.
     */
    public Communicator(String brickName, String brickAddress) {
        this.brickName = brickName;
        this.brickAddress = brickAddress;
        this.brickConnection = new NXTConnector();
        this.brickConnection.setDebug(true);

        this.connect();
    }

    public void connect() {
        boolean connected = false;
        System.out.println("Connecting to " + this.brickAddress);
        while (!connected) {
            connected = this.brickConnection.connectTo(this.brickName, this.brickAddress, NXTCommFactory.BLUETOOTH);
           System.out.println("Connection to robot: " + connected);
        }

        this.inputFromBrick = brickConnection.getInputStream();
        this.outputToBrick = brickConnection.getOutputStream();
    }

    /**
     * Sends a command to the robot and waits for execution.
     *
     * Note that this call is blocking until a reply has been received
     * or the call has timed out.
     *
     * @param command The command to send.
     *
     * @return Whether the command was executed successfully.
     */
    public Boolean sendCommand(Command command) {
        byte[] buffer = new byte[512];
        String response;

        try {
            outputToBrick.write(command.toString().getBytes());
            outputToBrick.flush();
            inputFromBrick.read(buffer);

            response = new String(buffer, "UTF-8").split("\n")[0];
            return response.contains("success");
        }
        catch (IOException e) {
            System.out.println("Failed to communicate with the robot, re-connecting!");
            this.connect();
            return false;
        }
    }
}

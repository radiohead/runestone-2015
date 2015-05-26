import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.Button;
import lejos.nxt.comm.*;
import lejos.robotics.navigation.DifferentialPilot;

import java.io.*;

public class RobotClient {
  public static void drawLCDString(String message, int x, int y) {
    LCD.clear();
    LCD.drawString(message, x, y);
  }

  public static void drawLCDString(String message) {
    RobotClient.drawLCDString(message, 0, 0);
  }

  static class Robot {
    // Measurements
    double gridSize;

    // Kinetics
    DifferentialPilot robotPilot;

    // Connectivity
    BTConnection serverConnection;
    DataOutputStream serverOutput;
    DataInputStream serverInput;

    // Public methods
    public Robot(double wheelDiameter, double robotWidth, double gridSize) {
      this.gridSize = gridSize;
      this.robotPilot = new DifferentialPilot(wheelDiameter, robotWidth, Motor.A, Motor.C);
      // this.robotPilot.setTravelSpeed(1);
    }

    public void connectToServer() {
      this.serverConnection = Bluetooth.waitForConnection();
      this.serverOutput = this.serverConnection.openDataOutputStream();
      this.serverInput = this.serverConnection.openDataInputStream();

      RobotClient.drawLCDString("Connected to server!");
    }

    public void restart() {
      // Stop immediately
      this.robotPilot.quickStop();

      // Close and invalidate connection
      this.serverInput = null;
      this.serverOutput = null;
      this.serverConnection.close();
    }

    public boolean processCommands() throws Exception {
      String command;
      boolean shutdown = false;

      while (true) {
        command = this.getNextCommand();

        switch (command) {
          case "forward":
            this.travelForward();
            break;
          case "backwards":
            this.travelBackwards();
            break;
          case "left":
            this.turnLeft();
            break;
          case "right":
            this.turnRight();
            break;
          case "shutdown":
            shutdown = true;
            break;
          default:
            this.sendResponse("fail");
            continue;
        }

        this.sendResponse("success");

        if (shutdown) {
          break;
        }
      }

      return shutdown;
    }

    // Private methods
    private void turnLeft() {
      // Clockwise, in degrees
      this.robotPilot.rotate(-90);
    }

    private void turnRight() {
      this.robotPilot.rotate(90);
    }

    private void travelForward() {
      this.robotPilot.travel(this.gridSize);
    }

    private void travelBackwards() {
      this.robotPilot.travel(-this.gridSize);
    }

    private String getNextCommand() {
      try {
        String command = this.serverInput.readLine();
        RobotClient.drawLCDString("Received command: " + command);
        return command;
      }
      catch (IOException e) {
        RobotClient.drawLCDString("Failed to fetch command");
        RobotClient.drawLCDString("Send command again");
        return "";
      }
    }

    private void sendResponse(String message) throws Exception {
      try {
        this.serverOutput.writeChars(message + "\n");
        this.serverOutput.flush();
      }
      catch (IOException e) {
        RobotClient.drawLCDString("Failed to send response, need to restart");
        throw new Exception("Failed to send response");
      }
    }
  }

  // Everything in millimeters
  private static final int GRID_SIZE = 500;
  private static final int ROBOT_WIDTH = 126;
  private static final int WHEEL_DIAMETER = 56;

  public static void main(String[] args) {
    RobotClient.drawLCDString("Press any button");
    Button.waitForAnyPress();

    Robot robot = new Robot(WHEEL_DIAMETER, ROBOT_WIDTH, GRID_SIZE);
    boolean stopProgram;

    while (true) {
      try {
        // Blocking call
        // will wait for connection
        robot.connectToServer();

        // Enter an infinite loop
        // Start processing incoming commands
        stopProgram = robot.processCommands();

        // To be more explicit
        if (stopProgram) {
          return;
        }
      }
      catch (Exception e) {
        // Something bad happened
        // Restarting everything
        RobotClient.drawLCDString("Restarting!");
        robot.restart();
      }
    }
  }
}

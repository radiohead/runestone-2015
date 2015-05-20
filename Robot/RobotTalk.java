import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTCommFactory;

import java.io.*;

class RobotTalk {
  public static void main(String[] args) {
    byte[] buffer = new byte[2048];

    NXTConnector brickConnection = new NXTConnector();

    boolean connected = false;
    while (!connected) {
      connected = brickConnection.connectTo("taikaviitta", "00:16:53:17:1E:0A", NXTCommFactory.BLUETOOTH);
    }

    System.out.println("Connection " + connected);

    InputStream inputFromBrick = brickConnection.getInputStream();
    OutputStream outputToBrick = brickConnection.getOutputStream();

    try {
      outputToBrick.write("forward\n".getBytes());
      outputToBrick.flush();
      inputFromBrick.read(buffer);
      System.out.println("Received " + new String(buffer));

      outputToBrick.write("right\n".getBytes());
      outputToBrick.flush();
      inputFromBrick.read(buffer);
      System.out.println("Received " + new String(buffer));

      outputToBrick.write("forward\n".getBytes());
      outputToBrick.flush();
      inputFromBrick.read(buffer);
      System.out.println("Received " + new String(buffer));

      outputToBrick.write("backwards\n".getBytes());
      outputToBrick.flush();
      inputFromBrick.read(buffer);
      System.out.println("Received " + new String(buffer));

      outputToBrick.write("left\n".getBytes());
      outputToBrick.flush();
      inputFromBrick.read(buffer);
      System.out.println("Received " + new String(buffer));

      outputToBrick.write("backwards\n".getBytes());
      outputToBrick.flush();
      inputFromBrick.read(buffer);
      System.out.println("Received " + new String(buffer));

      outputToBrick.write("shutdown\n".getBytes());
      outputToBrick.flush();
      inputFromBrick.read(buffer);
      System.out.println("Received " + new String(buffer));
    }
    catch (IOException e) {
      System.out.println("Io failed");
    }
  }
}

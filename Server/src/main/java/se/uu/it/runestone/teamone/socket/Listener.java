package se.uu.it.runestone.teamone.socket;

import se.uu.it.runestone.teamone.climate.Sensor;
import se.uu.it.runestone.teamone.map.Node;
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.robotcontrol.Robot;

import javax.net.ServerSocketFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.*;

/**
 * Initiates and listens on a socket.
 *
 * @author Åke Lagercrantz
 */
public class Listener {

    private ServerSocket serverSocket;
    private Socket socket;

    private PrintWriter out;
    private BufferedReader in;

    private ListenerDelegate delegate;
    private Integer port;

    public Listener(Integer port, ListenerDelegate delegate) throws IOException {
        this.port = port;
        this.delegate = delegate;
    }

    public void listen() throws IOException {
        String input;

        this.serverSocket = ServerSocketFactory.getDefault().createServerSocket(this.port);

        Boolean exit = false;
        while (!exit) {
            System.out.println("Listener - Waiting for connection on port " + this.port.toString() + ".");
            this.socket = serverSocket.accept();
            System.out.println("Listener - Connected on port " + this.port.toString());

            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while ((input = in.readLine()) != null) {
                System.out.println("Listener - Received command \"" + input + "\"");

                if (input.equals("map")) { // map
                    Room room = this.delegate.getRoom();
                    JSONObject json = new JSONObject();

                    json.put("size_x", room.getDimX());
                    json.put("size_y", room.getDimX());

                    for (Node n : room.getObstructedNodes()) {
                        JSONObject obstacleJson = new JSONObject();
                        obstacleJson.put("position_x", n.getX());
                        obstacleJson.put("position_y", n.getY());

                        json.append("obstacles", obstacleJson);
                    }

                    out.println(json.toString());
                } else if (input.startsWith("goto,")) {
                    String[] parts = input.split(",");

                    if (parts.length == 3) {  // "goto,<x>,<y>"
                        Integer x = Integer.valueOf(parts[1]);
                        Integer y = Integer.valueOf(parts[2]);

                        this.delegate.goTo(x,y);
                        out.println("{\"status\": \"processing\"}");
                    } else if (parts.length == 4) {
                        out.println("{\"status\": \"processing\"}");
                    } else {
                        out.println("{\"error\": \"not_available\"}");
                    }

                } else if (input.equals("robot")) {
                    // TODO: When we start supporting multiple robot we will need to add instance handling here
                    JSONObject json = new JSONObject();
                    ArrayList<Robot> robotsList = new ArrayList<>();
                    robotsList.add(this.delegate.getRobotInstance(0));

                    for (Robot robot : robotsList) {
                        JSONObject singleRobotJson = new JSONObject();

                        singleRobotJson.put("id", robot.getId());
                        singleRobotJson.put("name", robot.getName());
                        singleRobotJson.put("direction", robot.getCurrentDirection().name().toLowerCase());
                        singleRobotJson.put("position_x", robot.getCurrentPosition().getX());
                        singleRobotJson.put("position_y", robot.getCurrentPosition().getY());

                        json.append("robots", singleRobotJson);
                    }

                    out.println(json.toString());
                } else if (input.equals("release")) {
                    out.println("{\"success\": true}");
                } else if (input.equals("sensor")) {
                    JSONObject json = new JSONObject();

                    for (Sensor sensor : this.delegate.getSensors()) {
                        JSONObject singleSensorJson = new JSONObject();

                        singleSensorJson.put("id", sensor.getId());
                        singleSensorJson.put("name", sensor.getSensorName());
                        singleSensorJson.put("light", sensor.getLight());
                        singleSensorJson.put("temperature", sensor.getTemperature());
                        singleSensorJson.put("position_x", sensor.getX());
                        singleSensorJson.put("position_y", sensor.getY());

                        json.append("sensors", singleSensorJson);
                    }

                    out.println(json.toString());
                } else {
                    out.println("{\"error\": \"not_available\"}");
                }
            }

            System.out.println("Listener - Connection closed.");
        }
    }
}

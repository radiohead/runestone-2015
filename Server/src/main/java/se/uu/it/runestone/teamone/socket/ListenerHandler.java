package se.uu.it.runestone.teamone.socket;

import org.json.JSONObject;
import se.uu.it.runestone.teamone.climate.Sensor;
import se.uu.it.runestone.teamone.map.Node;
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.robotcontrol.Robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Igor Suleymanov on 5/30/15.
 * This class provides threaded handling of incoming socket requests
 */
public class ListenerHandler extends Thread {
    Socket socket;
    ListenerDelegate delegate;

    public ListenerHandler(Socket clientSocket, ListenerDelegate serverDelegate) {
        this.socket = clientSocket;
        this.delegate = serverDelegate;
    }

    @Override
    public void run() {
        String input;
        PrintWriter out;
        BufferedReader in;

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

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

                        this.delegate.goTo(x, y);
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
                    this.delegate.getDispatcher().releaseManualMode();
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

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }
}

package se.uu.it.runestone.teamone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;
//Test
import se.uu.it.runestone.teamone.map.Room;
import se.uu.it.runestone.teamone.pathfinding.PathFinder;
import se.uu.it.runestone.teamone.robotcontrol.Dispatch;
import se.uu.it.runestone.teamone.robotcontrol.Robot;
import se.uu.it.runestone.teamone.scheduler.Job;
import se.uu.it.runestone.teamone.scheduler.Scheduler;

import java.util.ArrayList;


/**
 * The main application. Starts a server as well as a Spring instance.
 *
 * @author Åke Lagercrantz
 */
@SpringBootApplication
@IntegrationComponentScan
public class Application {
    private Room map1;
    private PathFinder pathFinder;
    private Scheduler scheduler;
    private Dispatch dispatch;
    private ArrayList<Robot> robots;


    public static void main(String[] args) {
        Room map1 = new Room(10,10); // TODO: Add sensors <akelagercrantz>
        PathFinder pathFinder = new PathFinder();
        Scheduler scheduler = new Scheduler();
        ArrayList<Robot>robots = new ArrayList<Robot>(1);
        robots.add(new Robot()); // TODO: Give robot coms info. <akelagercrantz>
        Dispatch dispatch = new Dispatch(map1, pathFinder);

        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        new Thread(new Server(map1, pathFinder, scheduler, dispatch, robots));
    }
    public Room getMap(){
        return map1;
    }
}

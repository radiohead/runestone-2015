package se.uu.it.runestone.teamone.webservice;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;


/**
 * Handles requests for the Robot service.
 */
@RestController
public class RobotController {

    //Map to store robots, ideally we should use database
    Map<Integer, Robot> robData = new HashMap<Integer, Robot>();
     
    @RequestMapping(value = RuneURIConstants.DUMMY_ROB, method = RequestMethod.GET)
    public @ResponseBody Robot getDummyRobot() {
        Robot rob = new Robot();
        rob.setId(9999);
        rob.setLoad(1);
        rob.setStuck(0);
        robData.put(9999, rob);
        return rob;
    }

    @RequestMapping(value = RuneURIConstants.GET_ROB, method = RequestMethod.GET)
    public @ResponseBody Robot getRobot(@PathVariable("id") int robId) {
        return robData.get(robId);
    }


    @RequestMapping(value = RuneURIConstants.GET_ALL_ROB, method = RequestMethod.GET)
    public @ResponseBody List<Robot> getAllRobots() {
        List<Robot> robs = new ArrayList<Robot>();
        Set<Integer> robIdKeys = robData.keySet();
        for(Integer i : robIdKeys){
            robs.add(robData.get(i));
        }
        return robs;
    }

    @RequestMapping(value = RuneURIConstants.CREATE_ROB, method = RequestMethod.POST)
    public Robot robot(@RequestParam(value="id", defaultValue="0") int id,
                       @RequestParam(value="load", defaultValue="0") int load,
                       @RequestParam(value="stuck", defaultValue="0") int stuck,
                       @RequestParam(value="content", defaultValue="unnamed") String content) {

      //ADD CHECK FOR ID TAKEN OR NOT
        Robot rob = new Robot(id, load, stuck, content);
        rob.setId(id);
        robData.put(id, rob);
        return rob;
    }

    @RequestMapping(value = RuneURIConstants.DELETE_ROB, method = RequestMethod.PUT)
    public @ResponseBody Robot deleteRobot(@PathVariable("id") int robId) {
        Robot rob = robData.get(robId);
        robData.remove(robId);
        return rob;
    }  
}
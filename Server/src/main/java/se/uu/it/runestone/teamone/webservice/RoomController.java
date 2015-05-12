package se.uu.it.runestone.teamone.webservice;

import org.springframework.web.bind.annotation.*;
import se.uu.it.runestone.teamone.map.*;
import java.util.*;


/**
 * Handles requests for the Rooms service.
 */
@RestController
public class RoomController {
    //Map to store robots, ideally we should use database
    java.util.Map<Integer, RoomTest> roomData = new HashMap<Integer, RoomTest>();

    @RequestMapping(value = RuneURIConstants.DUMMY_ROOM, method = RequestMethod.GET)
    public @ResponseBody
    RoomTest getDummyRoom() {
        RoomTest dummyRoom = new RoomTest(10, 10);
        return dummyRoom;
    }

    @RequestMapping(value = RuneURIConstants.GET_ROOM, method = RequestMethod.GET)
    public @ResponseBody
    RoomTest getRoom(@PathVariable("id") int roomID){
        RoomTest room = roomData.get(roomID);
        return room;
    }

}
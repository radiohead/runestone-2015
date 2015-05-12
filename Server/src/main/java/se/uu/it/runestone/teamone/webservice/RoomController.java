package se.uu.it.runestone.teamone.webservice;

import org.springframework.web.bind.annotation.*;
import se.uu.it.runestone.teamone.map.*;
import se.uu.it.runestone.teamone.climate.*;
import java.util.*;


/**
 * Handles requests for the Rooms service.
 */
@RestController
public class RoomController {

    //Map to store robots, ideally we should use database
    java.util.Map<Integer, Room> roomData = new HashMap<Integer, Room>();
     
    @RequestMapping(value = RuneURIConstants.DUMMY_ROOM, method = RequestMethod.GET)
    public @ResponseBody Room getDummyRoom() {
        Room dummyRoom = new Room(50, 50);
        roomData.put(9999, dummyRoom);
        return dummyRoom;
    }

    @RequestMapping(value = RuneURIConstants.GET_ROOM, method = RequestMethod.GET)
    public @ResponseBody Room getRoom(@PathVariable("id") int roomID){
        Room room = roomData.get(roomID);
        return room;
    }

}
package se.uu.it.runestone.teamone.robotcontrol.command;

import se.uu.it.runestone.teamone.map.Node;

/**
 * Represents a command that tells the robot to move forward.
 *
 * @author Åke Lagercrantz
 */
public class MoveForward extends Command {
    Node destination;

    public MoveForward(Node destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "forward\n";
    }

    public Node getDestination() {
        return this.destination;
    }
}

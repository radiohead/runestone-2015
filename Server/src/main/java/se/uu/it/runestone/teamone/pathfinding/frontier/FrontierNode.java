package se.uu.it.runestone.teamone.pathfinding.frontier;

import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;

/**
 * A node in the frontier including a PathFindingNode and a priority.
 *
 * @author Åke Lagercrantz
 */
public class FrontierNode {
    public final PathFindingNode node;
    public final Integer priority;

    /**
     * The designated initializer. Creates a new FrontierNode with a given node and a priority.
     *
     * @param node      The node that is part of the frontier.
     * @param priority  The priorty this node has compared to others in the frontier.
     */
    public FrontierNode(PathFindingNode node, Integer priority) {
        this.node = node;
        this.priority = priority;
    }
}

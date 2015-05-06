package se.uu.it.runestone.teamone.pathfinding;

import java.util.ArrayList;

/**
 * An interface describing the neccessary attributes and methods
 * needed for a map to be able to be used by the PathFinder.
 */
public interface PathFindingGraph {
    /**
     * Returns a list of the all the adjacent nodes (top, left, bottom, right) to the given node.
     *
     * @param node  The node on which to receive neighbours.
     *
     * @return A list of neighbouring nodes. The list is empty if no neighbours exists.
     */
    public ArrayList<PathFindingNode> neighbours(PathFindingNode node);

    /**
     * Returns the cost of moving from a node to its neighbour.
     *
     * @note The two nodes must be adjacent to eachother.
     *
     * @param node        The node from which the movement takes place.
     * @param neighbour   The neighbour to which the movement takes place.
     *
     * @return An integer representing the cost of the movement. -1 if the movement cannot take place.
     */
    public int cost(PathFindingNode node, PathFindingNode neighbour);

    /**
     * Returns the manhattan distance from node a to node b.
     *
     * @param a   The node of which to calculate distance from.
     * @param b   The node of which to calculate distance to.
     *
     * @return The manhattan distance as an integer.
     */
    public int distance(PathFindingNode a, PathFindingNode b);

    /**
     * Whether or not the given node meets the reqirements for an end node.
     *
     * @param node          The node which to check.
     * @param requirements  The requirements which should be met.
     *
     * @return A boolean stating whether the node meeds the requirements.
     */
    public Boolean nodeMeetsRequirements(PathFindingNode node, PathFindingRequirements requirements);
}


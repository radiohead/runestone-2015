package se.uu.it.runestone.teamone.pathfinding.frontier;

import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;

/**
 * An object supplying priorities for frontier nodes.
 *
 * @author Åke Lagercrantz
 */
public interface PrioritySupplier {
    /**
     * Supplies a priority for the frontier node.
     *
     * @param cost The cost of navigating to the node from the start node.
     * @param node The node to calculate priority for.
     *
     * @return An integer representing the priority of this frontier node compared to others.
     */
    int priority(int cost, PathFindingNode node);
}

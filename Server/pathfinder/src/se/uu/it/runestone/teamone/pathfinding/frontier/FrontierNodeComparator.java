package se.uu.it.runestone.teamone.pathfinding.frontier;

import java.util.Comparator;

/**
 * Compares two FrontierNode objects based on their priority.
 */
public class FrontierNodeComparator implements Comparator<FrontierNode> {
    @Override
    public int compare(FrontierNode node, FrontierNode other) {
        return Integer.valueOf(node.priority).compareTo(other.priority);
    }
}

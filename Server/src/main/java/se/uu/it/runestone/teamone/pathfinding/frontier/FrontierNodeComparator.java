package se.uu.it.runestone.teamone.pathfinding.frontier;

import java.util.Comparator;

/**
 * Compares two FrontierNode objects based on their priority.
 *
 * @author Åke Lagercrantz
 */
public class FrontierNodeComparator implements Comparator<FrontierNode> {
    @Override
    public int compare(FrontierNode node, FrontierNode other) {
        return node.priority.compareTo(other.priority);
    }
}

package se.uu.it.runestone.teamone.pathfinding;

import java.util.ArrayList;

/**
 * An interface describing a node in a PathFindingGraph.
 *
 * @author Åke Lagercrantz
 */
public interface PathFindingNode {
    public Integer getX();
    public Integer getY();
    public Boolean getVisited();
    public Boolean getObstructed();
    public String toString(ArrayList<PathFindingNode> path);
}

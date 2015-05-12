package se.uu.it.runestone.teamone.pathfinding.frontier;

import se.uu.it.runestone.teamone.pathfinding.PathFindingNode;

/**
 * An object checking wether the current node meets requirements.
 *
 * @author Åke Lagercrantz
 */
public interface RequirementChecker {
    /**
     * Wether the node meets end requirements or not.
     *
     * @param node The node to check requirements against.
     *
     * @return A boolean stating whether or not the node meets the requirements or not.
     */
    public Boolean nodeMeetsRequirements(PathFindingNode node);
}

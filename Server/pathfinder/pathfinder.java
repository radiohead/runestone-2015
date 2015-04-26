package se.uu.it.runestone.teamone.pathfinding;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.ArrayList;

import se.uu.it.runestone.teamone.pathfinding.graph;

/**
 * A class that implements pathfinding algorithms.
 * 
 * @author Åke Lagercrantz
 */
public class PathFinder {
  /// A FrontierNode comparator that compares nodes on priority.
  private PathFinder.FrontierNodeComparator frontierComparator;

  /**
   * The designated initializer.
   */
  public PathFinder() {
    this.frontierComparator = new PathFinder.FrontierNodeComparator();
  }

  /**
   * Finds the shortest path form point A to point B.
   *
   * @discussion Based on the A-star algorithm.
   * 
   * @param from  The node to start from.
   * @param goal  The target node.
   * @param graph The graph on which to find a path on.
   *
   * @return The shortest path from start to target.
   */
  public ArrayList<PathFindingNode> shortestPath(PathFindingNode from, PathFindingNode goal, PathFindingGraph graph) {
    RequirementChecker requirementChecker = new RequirementChecker() {
      public Boolean nodeMeetsRequirements(PathFindingNode node) {
        return node == goal;
      }
    }
    PrioritySupplier prioritySupplier = new PrioritySupplier() {
      public int priority(int cost, PathFindingNode node) {
        return cost + graph.distance(goal, neighbour);
      }
    }

    this.shortestPathWithPriorityAndRequirementFunction(from, graph, requirementChecker, prioritySupplier);
  }

  /**
   * Finds the shortest path form point A to any given point matching the requirements.
   * 
   * @discussion Based on Dijkstra's Algorithm.
   * 
   * @param from          The node to start from.
   * @param requirements  The requirements that must be met for the node for it to be
   *                      an acceptable endpoint.
   * @param graph         The graph on which to find a path on.
   *
   * @return The shortest path from start to a suitable target.
   */
  public ArrayList<PathFindingNode> shortestPathToNodeMatchingRequirements(PathFindingNode from, PathfindingRequirements requirements, PathFindingGraph graph) {
    {
    RequirementChecker requirementChecker = new RequirementChecker() {
      public Boolean nodeMeetsRequirements(PathFindingNode node) {
        return graph.nodeMeetsRequirements(requirements);
      }
    }
    PrioritySupplier prioritySupplier = new PrioritySupplier() {
      public int priority(int cost, PathFindingNode node) {
        return cost;
      }
    }

    this.shortestPathWithPriorityAndRequirementFunction(from, graph, requirementChecker, prioritySupplier);
  }

  ///////// PRIVATE /////////

  /**
   * An object checking wether the current node meets requirements.
   */
  private interface RequirementChecker {
    /**
     * Wether the node meets end requirements or not.
     *
     * @param node The node to check requirements against.
     *
     * @return A boolean stating whether or not the node meets the requirements or not.
     */
    public Boolean nodeMeetsRequirements(PathFindingNode node);
  }

  /**
   * An object supplying priorities for frontier nodes.
   */
  private interface PrioritySupplier {
    /**
     * Supplies a priority for the frontier node.
     *
     * @param cost The cost of navigating to the node from the start node.
     * @param node The node to calculate priority for.
     *
     * @param An integer representing the priority of this frontier node compared to others.
     */
    public int priority(int cost, PathFindingNode node);
  }

  /**
   * Calculates the shortest path from a node to any node meeting the requirements.
   *
   * @discussion The requirements are checked by a supplied object.
   *             This enables different types of requirements, such as a single goal node,
   *             or any node matching climate criterias.
   * @discussion The priorities are given by a supplied object. 
   *             This enables usage of different pathfinding algorithms such
   *             as A-star or Dijkstra's based on the same method.
   * 
   * @param from                The node to start from.
   * @param graph               The graph on which to find a path on.
   * @param requirementChecker  The requirement checker object used to determine
   *                            when a goal has been found.
   * @param prioritySupplier    The priority supplier object used to give priorities to
   *                            the frontier.
   */
  private ArrayList<PathFindingNode> shortestPathWithPriorityAndRequirementFunction(PathFindingNode from, PathFindingGraph graph, RequirementChecker requirementChecker, PrioritySupplier prioritySupplier) {
    PriorityQueue<FrontierNode> frontier = new PriorityQueue<FrontierNode>(100, this.frontierComparator);
    FrontierNode start = new FrontierNode(from, 0);
    frontier.add(start);

    HashMap<PathFindingNode,PathFindingNode> links = new HashMap<PathFindingNode,PathFindingNode>();
    links.put(form) = null;
    HashMap<PathFindingNode,int> costs = new HashMap<PathFindingNode,int>();
    costs.put(from) = 0;

    while ((FrontierNode currentFrontier = frontier.poll()) != null) {
      PathFindingNode current = currentFrontier.node;

      if (requirementChecker.nodeMeetsRequirements(current))
        break;
      }

      ArrayList<PathFindingNode> neighbours = graph.neighbours(current);
      for (PathFindingNode neighbour : neighbours) {
        int stepCost = graph.cost(current, neighbour);
        // If we cannot navigate to this space.
        if (stepCost == -1) {
          continue;
        }

        int cost = costs.get(current) + stepCost;
        if (!costs.containsKey(neighbour) || cost < costs.get(current)) {
          costs.put(neighbour, cost);
          int priority = prioritySupplier.priority(cost, neighbour);
          FrontierNode next = new FrontierNode(neighbour, priority);
          frontier.add(next); // We don't care about duplicates here.
          links.put(neighbour, current);
        }
      }
    }

    return this.constructPath(from, currentFrontier.node, links);
  }

  /**
   * Constructs a path given the start, the goal and a hasmap of links between nodes.
   *
   * @param from    The node to start at.
   * @param goal    The node to end at.
   * @param links   A hashmap with all nodes linked together.
   *
   * @return An array list with nodes in the order of the path.
   */
  private ArrayList<PathFindingNode> constructPath(PathFindingNode from, PathFindingNode goal, HashMap<PathFindingNode> links) {
    PathFindingNode current = goal;
    ArrayList<PathFindingNode> path = new ArrayList<PathFindingNode>();
    path.add(current);

    while (current != from) {
      current = links.get(current);
      path.add(current);
    }

    return path;
  }

  //////// FRONTIER NODES ////////

  /**
   * A node in the frontier including a PathFindingNode and a priority.
   */
  private static class FrontierNode implements Comparable<FrontierNode> {
    public final PathFindingNode node;
    public final int priority;

    /**
     * The designated initializer. Creates a new FrontierNode with a given node and a priority.
     *
     * @param node      The node that is part of the frontier.
     * @param priority  The priorty this node has compared to others in the frontier.
     */
    public FrontierNode(PathFindingNode node, int priority) {
      this.node = node;
      this.priority = priority;
    }
  }

  /**
   * Compares two FrontierNode objects based on their priority.
   */
  private static class FrontierNodeComparator implements Comparator<FrontierNode> {
    @Override
    public int compare(FrontierNode node, FrontierNode other) {
      return Integer.valueOf(node.priority).compareTo(other.priority);
    }
  }
}

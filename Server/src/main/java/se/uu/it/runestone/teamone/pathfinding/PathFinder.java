package se.uu.it.runestone.teamone.pathfinding;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.PriorityQueue;

import se.uu.it.runestone.teamone.pathfinding.frontier.PrioritySupplier;
import se.uu.it.runestone.teamone.pathfinding.frontier.RequirementChecker;
import se.uu.it.runestone.teamone.pathfinding.frontier.FrontierNode;
import se.uu.it.runestone.teamone.pathfinding.frontier.FrontierNodeComparator;


/**
 * A class that implements pathfinding algorithms.
 * 
 * @author Åke Lagercrantz
 */
public class PathFinder {
  /// A FrontierNode comparator that compares nodes on priority.
  private FrontierNodeComparator frontierComparator;

  /**
   * The designated initializer.
   */
  public PathFinder() {
    this.frontierComparator = new FrontierNodeComparator();
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
  public ArrayList<PathFindingNode> shortestPath(final PathFindingNode from, final PathFindingNode goal, final PathFindingGraph graph) {
    RequirementChecker requirementChecker = new RequirementChecker() {
      public Boolean nodeMeetsRequirements(PathFindingNode node) {
        return node.equals(goal);
      }
    };
    PrioritySupplier prioritySupplier = new PrioritySupplier() {
      public int priority(int cost, PathFindingNode node) {
        return cost + graph.distance(goal, node);
      }
    };

    return this.shortestPathWithPriorityAndRequirementFunction(from, graph, requirementChecker, prioritySupplier);
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
  public ArrayList<PathFindingNode> shortestPathToNodeMatchingRequirements(final PathFindingNode from, final PathFindingRequirements requirements, final PathFindingGraph graph) {
    RequirementChecker requirementChecker = new RequirementChecker() {
      public Boolean nodeMeetsRequirements(PathFindingNode node) {
        return graph.nodeMeetsRequirements(node, requirements);
      }
    };
    PrioritySupplier prioritySupplier = new PrioritySupplier() {
      public int priority(int cost, PathFindingNode node) {
        return cost;
      }
    };

    return this.shortestPathWithPriorityAndRequirementFunction(from, graph, requirementChecker, prioritySupplier);
  }

  ///////// PRIVATE /////////

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
    PriorityQueue<FrontierNode> frontier = new PriorityQueue<>(100, this.frontierComparator);
    FrontierNode start = new FrontierNode(from, 0);
    frontier.add(start);

    HashMap<PathFindingNode,PathFindingNode> links = new HashMap<>();
    links.put(from, null);

    HashMap<PathFindingNode,Integer> costs = new HashMap<>();
    costs.put(from, 0);

    FrontierNode currentFrontier;
    while ((currentFrontier = frontier.poll()) != null) {
      PathFindingNode current = currentFrontier.node;
      graph.didVisitNode(current);

      if (requirementChecker.nodeMeetsRequirements(current)) {
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
        if (!costs.containsKey(neighbour) || cost < costs.get(current)) {
          costs.put(neighbour, cost);
          int priority = prioritySupplier.priority(cost, neighbour);
          FrontierNode next = new FrontierNode(neighbour, priority);
          frontier.add(next); // We don't care about duplicates here.
          links.put(neighbour, current);
        }
      }
    }

    if (currentFrontier == null) {
      return null;
    } else {
      return this.constructPath(from, currentFrontier.node, links);
    }
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
  private ArrayList<PathFindingNode> constructPath(PathFindingNode from, PathFindingNode goal, HashMap<PathFindingNode, PathFindingNode> links) {
    PathFindingNode current = goal;
    ArrayList<PathFindingNode> path = new ArrayList<PathFindingNode>();
    path.add(current);

    while (current != from) {
      current = links.get(current);
      path.add(current);
    }

    return path;
  }
}

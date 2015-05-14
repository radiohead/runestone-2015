package se.uu.it.runestone.teamone.pathfinding;

import java.util.ArrayList;

import se.uu.it.runestone.teamone.map.Room;

public class Main {
    public static void main(String[] args) {

        Room map = new Room(10,10);
        PathFinder pathfinder = new PathFinder();

        PathFindingNode start = map.nodeFromCoordinates(0, 9);
        PathFindingNode goal = map.nodeFromCoordinates(9, 9);

        // Shortest path from A to B
        ArrayList<? extends PathFindingNode> path = pathfinder.shortestPath(start, goal, map);

        // Shortest path from A to node matching reqs. Modify reqs in Map - nodeMeetsRequirements.
        //ArrayList<PathFindingNode> path = pathfinder.shortestPathToNodeMatchingRequirements(start, null, map);

        System.out.println(map.toString(path));
    }
}

/*
class Map implements PathFindingGraph {

    private Integer width;
    private Integer height;

    private ArrayList<ArrayList<PathFindingNode>> nodes;

    public Map(Integer width, Integer height) {
        this.width = width;
        this.height = height;

        this.constructNodes();
        this.addObstructions();
    }

    public PathFindingNode nodeFromCoordinates(Integer x, Integer y) {
        ArrayList<PathFindingNode> row = this.nodes.get(x);
        return row.get(y);
    }

    //// PathFindingGraph ////

    @Override
    public ArrayList<PathFindingNode> neighbours(PathFindingNode node) {
        Integer x = ((Node) node).x;
        Integer y = ((Node) node).y;

        ArrayList<PathFindingNode> neighbours = new ArrayList<PathFindingNode>();

        // Top
        if (y > 0) {
            neighbours.add(this.nodeFromCoordinates(x, y-1));
        }
        // TurnLeft
        if (x > 0) {
            neighbours.add(this.nodeFromCoordinates(x-1, y));
        }
        // Bottom
        if (y < this.height - 1) {
            neighbours.add(this.nodeFromCoordinates(x, y+1));
        }
        // TurnRight
        if (x < this.width - 1) {
            neighbours.add(this.nodeFromCoordinates(x+1, y));
        }

        return neighbours;
    }

    @Override
    public Integer cost(PathFindingNode node, PathFindingNode neighbour) {
        if (((Node)neighbour).obstructed) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public Integer distance(PathFindingNode a, PathFindingNode b) {
        // Manhattan distance
        return Math.abs(((Node) a).x - ((Node) b).x) + Math.abs(((Node) a).y - ((Node) b).y);
    }

    @Override
    public Boolean nodeMeetsRequirements(PathFindingNode node, PathFindingRequirements requirements) {
        return ((Node)node).x == 9 && ((Node) node).y == 2;
    }

    @Override
    public void didVisitNode(PathFindingNode node) {
        ((Node)node).visited = true;
        //System.out.println(this);
    }

    //// Printing ////

    @Override
    public String toString() {
        return this.toString(null);
    }

    public String toString(ArrayList<PathFindingNode> path) {
        String map = "";

        for (Integer i = 0; i < this.width; i++) {
            map += "---";
        }
        map += "\n";

        for (Integer y = 0; y < this.height; y++) {
            for (Integer x = 0; x < this.width; x++) {
                Node node = (Node)this.nodeFromCoordinates(x,y);
                map += node.toString(path);
            }
            map += "\n";
        }

        for (Integer i = 0; i < this.width; i++) {
            map += "---";
        }

        return map;
    }

    //// Private ////

    private void constructNodes() {
        this.nodes = new ArrayList<ArrayList<PathFindingNode>>();
        for (Integer x = 0; x < this.width; x++) {
            ArrayList<PathFindingNode> row = new ArrayList<PathFindingNode>();
            this.nodes.add(x, row);
            for (Integer y = 0; y < this.height; y++) {
                Node node = new Node(x, y);
                row.add(y, node);
            }
        }
    }

    private void addObstructions() {
        for (Integer y = 0; y < this.height; y++) {
            if (y != 2) {
                Node node = (Node)this.nodeFromCoordinates(3, y);
                node.obstructed = true;
            }
        }

        for (Integer y = 0; y < this.height; y++) {
            if (y != 7) {
                Node node = (Node)this.nodeFromCoordinates(7, y);
                node.obstructed = true;
            }
        }
    }
}

class Node implements PathFindingNode {
    public Integer x;
    public Integer y;

    public Boolean visited;
    public Boolean obstructed;

    public Node(Integer x, Integer y) {
        this.x = x;
        this.y = y;
        this.visited = false;
        this.obstructed = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == this.getClass()) {
            return this.x == ((Node)o).x && this.y == ((Node)o).y;
        } else {
            return super.equals(o);
        }
    }

    @Override
    public String toString() {
        return this.toString(null);
    }

    public String toString(ArrayList<PathFindingNode> path) {
        if (path != null && path.contains(this)) {
            return " s ";
        } else if (this.visited) {
            return " - ";
        } else if (this.obstructed) {
            return " o ";
        } else {
            return "   ";
        }
    }
}*/
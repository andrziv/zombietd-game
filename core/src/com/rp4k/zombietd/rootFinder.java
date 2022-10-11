package com.rp4k.zombietd;

import com.badlogic.gdx.math.Vector2;

import java.awt.Rectangle;
import java.util.ArrayList;

// pathfinding algorithm
public class rootFinder {
    boolean isFinished = false;
    static Vector2 endNode;

    Node currentNode;
    ArrayList<Node> finishedPath = new ArrayList<>();
    ArrayList<Node> Path = new ArrayList<>();
    boolean hasPath;

    // returns a list of nodes that finds the most optimal path
    // from the starting position, to the ending position
    // if there is a possible path, return a list of nodes from the start to end points
    // else return an empty list
    public ArrayList<Node> findRoot(Vector2 start, Vector2 end) {
        Path.clear();
        finishedPath.clear();
        endNode = end;
        Path.add(new Node(start, null));

        while (!isFinished) {
            currentNode = null;
            for (Node node : Path) {
                if (node.isPast == false) {
                    currentNode = node;
                }
            }
            // for whenever there is no possible path
            if (currentNode == null) {
                System.out.println("No Path!");
                hasPath = false;
                isFinished = true;
                return new ArrayList<>();
            }
            for (Node node : Path) {
                if (node.isPast) { // if a node is one that is already on the path
                    continue;
                }
                if (node.F() < currentNode.F()) { // compares node scores to see which is more optimal
                    currentNode = node;
                } else if (node.H < currentNode.H) {
                    currentNode = node;
                }
            }

            currentNode.isPast = true;

            // if the path has found its way to the end point, flag the algorithm to be finished and
            // save the path
            if ((currentNode.position.x == endNode.x && currentNode.position.y == endNode.y) && !isFinished) {
                hasPath = true;
                isFinished = true;
                currentNode.Save(finishedPath);
            }

            // tries to place a node in the 4 cardinal directions of the current node
            Node neighbor1 = new Node(new Vector2(currentNode.position.x + 50, currentNode.position.y), currentNode); // right
            Node neighbor2 = new Node(new Vector2(currentNode.position.x - 50, currentNode.position.y), currentNode); // left
            Node neighbor3 = new Node(new Vector2(currentNode.position.x, currentNode.position.y + 50), currentNode); // top
            Node neighbor4 = new Node(new Vector2(currentNode.position.x, currentNode.position.y - 50), currentNode); // bottom

            tryPlace(neighbor1);
            tryPlace(neighbor2);
            tryPlace(neighbor3);
            tryPlace(neighbor4);
        }

        return finishedPath;
    }

    // rounds a number to the closest 50
    public static int roundToFifty(int x) {
        return ((x + 25) / 50) * 50;
    }

    // tries to place a node at a position
    // if the area located is occupied by a tower, return nothing
    // if the area located is already occupied by a node, return nothing
    // if the area located is occupied by the ui, return nothing
    // else, place the node in the path finding list
    public void tryPlace(Node node) {
        // an area that there should not be any nodes in. this area is occupied by the ui
        if (!new Rectangle(-75, 0, 1375, 500).contains(node.position.x, node.position.y)) {
            return;
        }

        // if there is a tower at the position
        for (Towers tower : ZombieTD.towerList) {
            if (tower.towerXPos == node.position.x && tower.towerYPos == node.position.y) {
                return;
            }
        }
        // if there is already a node at the position
        for (Node n : Path) {
            if (n.position.x == node.position.x && n.position.y == node.position.y) {
                return;
            }
        }
        Path.add(node);
    }
}
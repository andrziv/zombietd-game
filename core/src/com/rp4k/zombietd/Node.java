package com.rp4k.zombietd;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

// class for a positioning node
public class Node {
    int nodeID = 0;
    Vector2 position = new Vector2();
    float Score = 0;
    int G; // "Cost" to get to the point
    float H; // Estimated "cost" of the most efficient way to get to the point
    boolean isPast = false;
    Node parent; // the parent node

    // creates a node at a
    // vector2 position
    // with a parent node
    public Node(Vector2 position, Node parent)
    {
        this.position = position;
        this.H = rootFinder.roundToFifty((int) Math.abs(Math.sqrt(Math.pow(rootFinder.endNode.x - position.x, 2) + Math.pow(rootFinder.endNode.y - position.y, 2))))/50;;
        F();
        this.parent = parent;
        if (parent == null)
        {
            G = 0;
        }
        else
        {
            G = parent.G + 1;
        }
    }

    // F is final score of the node between the G and H values
    public int F()
    {
        Score = G + H;
        return (int) Score;
    }

    // gets the entire chain of nodes that are ancestor (parent) to this node
    public void Save(List<Node> list)
    {
        list.add(0, this);
        if(parent != null) {
            parent.Save(list);
        }
    }

    // gets the node ID
    public int getNodeID()
    {
        return nodeID;
    }
}

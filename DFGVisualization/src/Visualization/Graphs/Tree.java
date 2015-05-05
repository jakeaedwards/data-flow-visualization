package Visualization.Graphs;

import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Jake on 5/4/2015.
 */
public class Tree extends DirectedGraph {

    Node root;

    public Tree(Node r, PApplet parent) {
        super(parent);
        root = r;
        nodes.add(root);
        flower = new TreeFlowAlgorithm(parent);
    }

    public Node getRoot() { return root; }

    void addChild(Node parent, Node child) {
        nodes.add(child);
        linkNodes(parent, child); }

    int getDepth() { return getDepth(root); }

    int getDepth(Node r)
    {
        if(r.getOutgoingLinksCount()==0) return 1;
        int d = 0;
        ArrayList<Node> outgoing = r.getOutgoingLinks();
        for(Node child: outgoing) {
            int dc = getDepth(child);
            if(dc>d) { d=dc; }}
        return 1+d;
    }
}

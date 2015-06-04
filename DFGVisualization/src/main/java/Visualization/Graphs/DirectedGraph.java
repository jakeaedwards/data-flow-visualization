package Visualization.Graphs;

import processing.core.*;
import java.util.ArrayList;

/**
 * Created by Jake on 5/4/2015.
 */
public class DirectedGraph{
    ArrayList<Node> nodes = new ArrayList<>();
    FlowAlgorithm flower;
    PApplet parent;

    public DirectedGraph(PApplet parent){
        this.parent = parent;
        flower = new CircleFlowAlgorithm(parent);
    }

    public void setFlowAlgorithm(FlowAlgorithm f) {
        flower = f;
    }

    public void addNode(Node node) {
        if(!nodes.contains(node)) {
            nodes.add(node);
        }
    }


    public int getGraphSize() {

        return nodes.size();
    }


    public boolean linkNodes(Node n1, Node n2) {
        if(nodes.contains(n1) && nodes.contains(n2)) {
            n1.addOutgoingLink(n2);
            n2.addIncomingLink(n1);
            return true;
        }
        return false;
    }

    public Node getNode(int index) {
        return nodes.get(index);
    }

    public ArrayList<Node> getNodes() {
        return nodes; }

    public ArrayList<Node> getRoots() {
        ArrayList<Node> roots = new ArrayList<>();
        for(Node n: nodes) {
            if(n.getIncomingLinksCount()==0) {
                roots.add(n); }}
        return roots; }

    public ArrayList<Node> getLeaves() {
        ArrayList<Node> leaves = new ArrayList<>();
        for(Node n: nodes) {
            if(n.getOutgoingLinksCount()==0) {
                leaves.add(n); }}
        return leaves;
    }

    // the method most people will care about
    public boolean reflow() {
        return flower.reflow(this);
    }

    // this does nothing other than tell nodes to draw themselves.
    public void draw() {
        for(Node n: nodes) {
            n.draw();
        }
    }
}

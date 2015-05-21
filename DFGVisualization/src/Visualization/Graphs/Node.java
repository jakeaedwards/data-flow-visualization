package Visualization.Graphs;

import processing.core.*;
import java.util.ArrayList;

/**
 * Created by Jake on 5/4/2015.
 */
public class Node{
    ArrayList<Node> inlinks = new ArrayList<Node>();
    ArrayList<Node> outlinks = new ArrayList<Node>();
    String id;
    String label;
    GraphUtility utility;
    PApplet parent;

    public Node(String _id, String _label, int _x, int _y, PApplet parent) {
        id = _id;
        label = _label;
        x = _x;
        y = _y;
        r1 = 5;
        r2 = 5;
        this.parent = parent;
        utility = new GraphUtility(parent);
    }

    public String getID(){
        return this.id;
    }

    public void addIncomingLink(Node n) {
        if (!inlinks.contains(n)) {
            inlinks.add(n);
        }
    }

    public ArrayList<Node> getIncomingLinks() {

        return inlinks;
    }

    public int getIncomingLinksCount() {

        return inlinks.size();
    }

    public void addOutgoingLink(Node n) {
        if (!outlinks.contains(n)) {
            outlinks.add(n);
        }
    }

    public ArrayList<Node> getOutgoingLinks() {

        return outlinks;
    }

    public int getOutgoingLinksCount() {

        return outlinks.size();
    }

    public float getShortestLinkLength() {
        if (inlinks.size() == 0 && outlinks.size() == 0) {
            return -1;
        }
        float l = 100;
        for (Node inode : inlinks) {
            int dx = inode.x - x;
            int dy = inode.y - y;
            float il = parent.sqrt(dx * dx + dy * dy);
            if (il < l) {
                l = il;
            }
        }
        for (Node onode : outlinks) {
            int dx = onode.x - x;
            int dy = onode.y - y;
            float ol = parent.sqrt(dx * dx + dy * dy);
            if (ol < l) {
                l = ol;
            }
        }
        return l;
    }

    public boolean equals(Node other) {
        if (this == other) {
            return true;
        }
        return label.equals(other.label);
    }

    // visualisation-specific
    int x = 0;
    int y = 0;
    int r1 = 10;
    int r2 = 10;

    public void setPosition(int _x, int _y) {
        x = _x;
        y = _y;
    }

    public void setRadii(int _r1, int _r2) {
        r1 = _r1;
        r2 = _r2;
    }

    public void draw() {
        parent.stroke(0);
        parent.fill(255);
        for (Node o : outlinks) {
            drawArrow(x, y, o.x, o.y);
        }

        parent.ellipse(x, y, r1 * 2, r2 * 2);
        parent.fill(50, 50, 255);
        parent.text(label, x + r1 * 2, y + r2 * 2);
    }

    int[] arrowhead = {0, -4, 0, 4, 7, 0};

    void drawArrow(int x, int y, int ox, int oy) {
        int dx = ox - x;
        int dy = oy - y;
        float angle = utility.getDirection(dx, dy);
        float vl = (float) (Math.sqrt(dx * dx + dy * dy) - Math.sqrt(r1 * r1 + r2 * r2) * 1.5);
        int[] end = utility.rotateCoordinate(vl, 0, angle);
        parent.line(x, y, x + end[0], y + end[1]);
        drawArrowHead(x + end[0], y + end[1], angle);
    }

    void drawArrowHead(int ox, int oy, float angle) {
        int[] rc1 = utility.rotateCoordinate(arrowhead[0], arrowhead[1], angle);
        int[] rc2 = utility.rotateCoordinate(arrowhead[2], arrowhead[3], angle);
        int[] rc3 = utility.rotateCoordinate(arrowhead[4], arrowhead[5], angle);
        int[] narrow = {ox + rc1[0], oy + rc1[1], ox + rc2[0], oy + rc2[1], ox + rc3[0], oy + rc3[1]};
        parent.stroke(0);
        parent.fill(255);
        parent.triangle(narrow[0], narrow[1], narrow[2], narrow[3], narrow[4], narrow[5]);
    }
}
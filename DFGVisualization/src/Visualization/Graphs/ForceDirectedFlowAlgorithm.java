package Visualization.Graphs;

import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Jake on 5/4/2015.
 */
public class ForceDirectedFlowAlgorithm extends FlowAlgorithm {


    float min_size = 80.0F;
    float elasticity = 200.0F;

    public ForceDirectedFlowAlgorithm(PApplet parent){
        super(parent);
    }

    public void setElasticity(float e) {
        elasticity = e;
    }

    float repulsion = 4.0F;

    public void setRepulsion(float r) {
        repulsion = r;
    }

    // this is actually a simplified force
    // directed algorithm, taking into account
    // only incoming links.

    boolean reflow(DirectedGraph g)
    {
        ArrayList<Node> nodes = g.getNodes();
        int reset = 0;
        for(Node n: nodes)
        {
            ArrayList<Node> incoming = n.getIncomingLinks();
            ArrayList<Node> outgoing = n.getOutgoingLinks();
            // compute the total push force acting on this node
            int dx = 0;
            int dy = 0;
            for(Node ni: incoming) {
                dx += (ni.x-n.x);
                dy += (ni.y-n.y); }
            float len = (float) Math.sqrt(dx * dx + dy * dy);
            float angle = utility.getDirection(dx, dy);
            int[] motion = utility.rotateCoordinate((float) 0.9 * repulsion, (float) 0.0, angle);
            // move node
            int px = n.x;
            int py = n.y;
            n.x += motion[0];
            n.y += motion[1];
            if(n.x<0) { n.x=0; } else if(n.x>parent.width) { n.x=parent.width; }
            if(n.y<0) { n.y=0; } else if(n.y>parent.height) { n.y=parent.height; }
            // undo repositioning if elasticity is violated
            float shortest = n.getShortestLinkLength();
            if(shortest<min_size || shortest>elasticity*2) {
                reset++;
                n.x=px; n.y=py; }
        }
        return reset==nodes.size();
    }

}

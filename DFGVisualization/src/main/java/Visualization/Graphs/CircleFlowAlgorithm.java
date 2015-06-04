package Visualization.Graphs;

import processing.core.PApplet;

/**
 * Created by Jake on 5/4/2015.
 */
public class CircleFlowAlgorithm extends FlowAlgorithm{

    // draw all nodes in a big circle,
    // without trying to find the best
    // arrangement possible.

    public CircleFlowAlgorithm(PApplet parent){

        super(parent);
    }

    boolean reflow(DirectedGraph g) {
        float interval = 2*parent.PI / (float)g.getGraphSize();
        int cx = parent.width/2;
        int cy = parent.height/2;
        float vl = cx - (2*g.getNode(0).r1) - 10;
        for(int a=0; a<g.getGraphSize(); a++){
            int[] nc = utility.rotateCoordinate(vl, 0, (float) a * interval);
            g.getNode(a).x = cx+nc[0];
            g.getNode(a).y = cy+nc[1];
        }
        return true;
    }

}

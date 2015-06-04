package Visualization.Graphs;

import processing.core.*;
/**
 * Created by Jake on 5/4/2015.
 */
public class FlowAlgorithm{
    // returns "true" if done, or "false" if not done
    PApplet parent;
    GraphUtility utility;

    public FlowAlgorithm(PApplet parent){
        this.parent = parent;
        utility = new GraphUtility(parent);
    }

    boolean reflow(DirectedGraph g){
        return true;
    }
}

package Visualization.Graphs;

import java.util.ArrayList;
import processing.core.*;

/**
 * Created by Jake on 5/4/2015.
 */
public class GraphUtility{

    PApplet parent;

    public GraphUtility(PApplet parent){
        this.parent = parent;
    }

    // =============================================
    //      Some universal helper functions
    // =============================================

    // universal helper function: get the angle (in radians) for a particular dx/dy
    float getDirection(double dx, double dy) {
        // quadrant offsets
        double d1 = 0.0;
        double d2 = parent.PI/2.0;
        double d3 = parent.PI;
        double d4 = 3.0*parent.PI/2.0;
        // compute angle basd on dx and dy values
        double angle = 0;
        float adx = parent.abs((float)dx);
        float ady = parent.abs((float)dy);
        // Vertical lines are one of two angles
        if(dx==0) { angle = (dy>=0? d2 : d4); }
        // Horizontal lines are also one of two angles
        else if(dy==0) { angle = (dx>=0? d1 : d3); }
        // The rest requires trigonometry (note: two use dx/dy and two use dy/dx!)
        else if(dx>0 && dy>0) { angle = d1 + parent.atan(ady/adx); }		// direction: X+, Y+
        else if(dx<0 && dy>0) { angle = d2 + parent.atan(adx/ady); }		// direction: X-, Y+
        else if(dx<0 && dy<0) { angle = d3 + parent.atan(ady/adx); }		// direction: X-, Y-
        else if(dx>0 && dy<0) { angle = d4 + parent.atan(adx/ady); }		// direction: X+, Y-
        // return directionality in positive radians
        return (float)(angle + 2*parent.PI)%(2*parent.PI);
    }

    // universal helper function: rotate a coordinate over (0,0) by [angle] radians
    int[] rotateCoordinate(float x, float y, float angle) {
        int[] rc = {0,0};
        rc[0] = (int)(x*parent.cos(angle) - y*parent.sin(angle));
        rc[1] = (int)(x*parent.sin(angle) + y*parent.cos(angle));
        return rc;
    }

    // universal helper function for Processing.js - 1.1 does not support ArrayList.addAll yet
    void addAll(ArrayList a, ArrayList b) {
        for(Object o: b) {
            a.add(o);
        }
    }
}

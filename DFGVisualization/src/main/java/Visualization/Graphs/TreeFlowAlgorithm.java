package Visualization.Graphs;

import processing.core.PApplet;

import java.util.ArrayList;
/**
 * Created by Jake on 5/4/2015.
 */
public class TreeFlowAlgorithm extends FlowAlgorithm {
    // tree layout is fairly simple: segment
    // the screen into as many vertical strips
    // as the tree is deep, then at every level
    // segment a strip in as many horizontal
    // bins as there are nodes at that depth.

    public TreeFlowAlgorithm(PApplet parent){

        super(parent);
    }

    boolean reflow(DirectedGraph g)
    {
        if(g instanceof Tree) {
            Tree t = (Tree) g;
            int depth = t.getDepth();
            int vstep = (parent.height-20)/depth;
            int vpos = 30;

            Node first = t.root;
            first.x = parent.width/2;
            first.y = vpos;

            // breadth-first iteration
            ArrayList<Node> children = t.root.getOutgoingLinks();
            while(children.size()>0)
            {
                vpos += vstep;
                int cnum = children.size();
                int hstep = (parent.width-20) / cnum;
                int hpos = 10 + (hstep/2);
                ArrayList<Node> newnodes = new ArrayList<>();
                for(Node child: children) {
                    child.x = hpos;
                    child.y = vpos;
                    newnodes.addAll(child.getOutgoingLinks()); //Altered
                    hpos += hstep;
                }
                children = newnodes;
            }
        }
        return true;
    }
}

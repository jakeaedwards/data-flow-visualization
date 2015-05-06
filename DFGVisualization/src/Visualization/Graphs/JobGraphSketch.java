package Visualization.Graphs;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import processing.core.PApplet;

import org.json.simple.parser.ParseException;

import java.util.ArrayList;

/**
 * Created by Jake on 5/5/2015.
 */
public class JobGraphSketch extends PApplet {

    private DirectedGraph graph = null;
    private static JSONParser parser = new JSONParser();
    private JSONArray nodes = new JSONArray();

    public JobGraphSketch(String executionPlan){

        try {
            Object obj = parser.parse(executionPlan);
            JSONObject jsonObj = (JSONObject) obj;
            nodes = (JSONArray) jsonObj.get("nodes");
        } catch(ParseException pe) {
            System.out.println("position: " + pe.getPosition());
            System.out.println(pe);
        }
    }

    public void setup()
    {
        size(300,300);
        frameRate(24);
        noLoop();
    }

    public void draw() {
        background(255);
        makeGraph();
        graph.setFlowAlgorithm(new ForceDirectedFlowAlgorithm(this));
        redraw();

        boolean done = graph.reflow();
        graph.draw();
    }

    /**
     * Parses the execution plan and adds nodes/vertices to the plan tree as appropriate
     */
    public void makeGraph(){

        for(int i = 0; i < nodes.size(); i++){
            JSONObject current = (JSONObject) nodes.get(i);
            String label = current.get("pact").toString();
            String id = current.get("id").toString();

            //Potentially arrange positions intelligently later
            int x = width / 2;
            int y = i * 50;

            Node newNode = new Node(id,label,x,y,this);

            //Parse through parent nodes and add relationships
            if(current.containsKey("predecessors")) {
                try {
                    Object jsonObj = parser.parse(current.get("predecessors").toString());
                    JSONArray predecessors = new JSONArray();
                    predecessors = (JSONArray) jsonObj;

                    System.out.println(predecessors.toString());
                    //Check for matching IDs in added nodes
                    for (int j = 0; j < predecessors.size(); j++) {
                        Object obj = predecessors.get(j);
                        JSONObject predecessor = (JSONObject) obj;
                        ArrayList<Node> addedNodes = graph.getNodes();
                        for (Node n : addedNodes) {
                            if (n.getID().equals(predecessor.get("id").toString())) {
                                newNode.addIncomingLink(n);
                                n.addOutgoingLink(newNode);
                            }
                        }
                    }
                } catch (ParseException pe) {
                    System.out.println("position: " + pe.getPosition());
                    System.out.println(pe);
                }
            }

            if(graph == null){
                graph = new DirectedGraph(this);
                graph.addNode(newNode);
            }
            else{
                graph.addNode(newNode);
            }
        }
    }
}

package Visualization;

import Visualization.Graphs.Graphs;
import org.apache.flink.api.java.tuple.Tuple;
import java.util.ArrayList;
import processing.core.PApplet;


/**
 * Created by Jake on 4/1/2015.
 */
public class Visualizer {

    public ArrayList<ArrayList> dataSets = new ArrayList<>();
    private String QUEUE_NAME = "queue";

    //TODO: Examine why this can print output data before the actual job does
    public void visualizeBarChart(){
        PApplet sketch = new BarChartSketch(dataSets.get(0));
        new DisplayFrame(sketch).setVisible(true);
    }

    public void addData(ArrayList<Tuple> newData){
        dataSets.add(newData);
    }

    public void visualizeGraph(){
        PApplet sketch = new Graphs();
        new DisplayFrame(sketch).setVisible(true);
    }
}

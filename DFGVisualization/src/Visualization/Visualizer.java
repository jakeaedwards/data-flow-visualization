package Visualization;

import org.apache.flink.api.java.tuple.Tuple;

import java.util.ArrayList;

/**
 * Created by Jake on 4/1/2015.
 */
public class Visualizer {

    public ArrayList<ArrayList> dataSets = new ArrayList<>();
    private String QUEUE_NAME = "queue";

    public void visualize(){
        System.out.println(dataSets.toString());
        new DisplayFrame(dataSets.get(0)).setVisible(true);
    }

    public void addData(ArrayList<Tuple> newData){
        dataSets.add(newData);
    }


}

package Visualization;

import Data.InSituDataSet;
import Visualization.Graphs.Graphs;
import Visualization.Graphs.JobGraphSketch;
import Visualization.Numerical.BarChartSketch;
import Visualization.Numerical.LineChartSketch;
import Visualization.Numerical.ScatterplotSketch;
import Visualization.Text.WordCloudSketch;
import org.apache.flink.api.java.tuple.Tuple;
import java.util.ArrayList;
import processing.core.PApplet;


/**
 * Created by Jake on 4/1/2015.
 */
public class Visualizer {

    public ArrayList<InSituDataSet> dataSets = new ArrayList<>();
    private String QUEUE_NAME = "queue";
    private String executionPlan;

    //TODO: Examine why this can print output data before the actual job does
    public void visualizeBarChart(int id){
        PApplet sketch = new BarChartSketch(getDataSet(id));
        new DisplayFrame(sketch).setVisible(true);
    }

    public void visualizeLineChart(int id){
        PApplet sketch = new LineChartSketch(getDataSet(id));
        new DisplayFrame(sketch).setVisible(true);
    }

    public void visualizeScatterPlot(int id){
        PApplet sketch = new ScatterplotSketch(getDataSet(id));
        new DisplayFrame(sketch).setVisible(true);
    }

    public void addData(InSituDataSet newData){
        dataSets.add(newData);
    }

    public void visualizeGraph(){
        PApplet sketch = new Graphs();
        new DisplayFrame(sketch).setVisible(true);
    }

    public void visualizeExecutionPlan(){
        PApplet sketch = new JobGraphSketch(executionPlan);
        new DisplayFrame(sketch).setVisible(true);
    }

    public void visualizeWordCloud(int id){
        PApplet sketch= new WordCloudSketch(getDataSet(id));
        new DisplayFrame(sketch).setVisible(true);
    }

    public void setPlan(String plan){
        executionPlan = plan;
    }

    private InSituDataSet getDataSet(int id){
        for(InSituDataSet i : dataSets){
            if(i.getId() == id){
                return i;
            }
        }

        return null;
    }
}
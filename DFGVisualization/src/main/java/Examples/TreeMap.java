package Examples;

import DataRecording.InSituCollector;
import Visualization.Visualizer;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple7;

/**
 * Created by Jake on 7/20/2015.
 */
public class TreeMap {

    public static void main(String[] args){

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Tuple7<String,String,String,String,String,String,String>> text = env.readCsvFile("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Generic\\treemap.csv")
                                  .includeFields(true, true, true, true, true, true, true)
                                  .types(String.class,String.class,String.class,String.class,String.class,String.class,String.class);

        Visualizer visualizer = new Visualizer();
        InSituCollector collector = new InSituCollector(env, visualizer);
        visualizer.visualizeTreeMap(1);

        text.print();
    }
}

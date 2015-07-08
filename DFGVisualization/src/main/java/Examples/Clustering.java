package Examples;

import DataRecording.InSituCollector;
import Visualization.Visualizer;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple5;

/**
 * Created by Jake on 7/6/2015.
 */
public class Clustering {
    public static void main(String[] args) throws Exception {

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //"Pre-Clustered" data set
        DataSet<Tuple5<Float, Float, Float, Float, String>> iris = env.readCsvFile("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Generic\\iris.csv")
                                                                                    .ignoreFirstLine().types(Float.class, Float.class,Float.class, Float.class, String.class);

        Visualizer visualizer = new Visualizer();
        InSituCollector collector = new InSituCollector(env, visualizer);
        collector.collect(1, iris, Float.class, Float.class, Float.class, Float.class, String.class);

        visualizer.visualizeScatterPlot(1, "", "Sepal Length", "Sepal Width");
        visualizer.visualizeMatrix(1);

        iris.print();

    }
}

package Examples;

import DataRecording.InSituCollector;
import Visualization.Visualizer;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.util.Collector;

/**
 * Created by Jake on 6/5/2015.
 */
public class GraphAnalysis {
    public static void main(String[] args) throws Exception{

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();


        DataSet<String> text = env.readTextFile("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Graphs\\moreno_lesmis\\out.moreno_lesmis_lesmis");
        DataSet<Tuple3<String, String, Float>> graph = text.flatMap(new GraphParser());

        //DataSet<Tuple1<Float>> weights = graph.project(2);
        //DataSet<Tuple2<Float, Integer>> edgeWeightFreq = weights.flatMap(new WeightLister()).groupBy(0).sum(1);

        DataSet<Tuple2<String, String>> edges = graph.project(0,1);
        DataSet<Tuple2<String, Integer>> degreeCounts = edges.flatMap(new EdgeLister()).groupBy(0).sum(1);
        DataSet<Tuple1<Integer>> degrees = degreeCounts.project(1);
        DataSet<Tuple2<Integer, Integer>> degreeFreq = degrees.flatMap(new WeightLister()).groupBy(0).sum(1);


        //Wikiconf stuff
        DataSet<String> wikiconf = env.readTextFile("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Graphs\\wikiconflict\\out.wikiconflict");
        DataSet<Tuple3<String, String, Float>> wikiGraph = wikiconf.flatMap(new GraphParser());

        //DataSet<Tuple1<Float>> wikiWeights = wikiGraph.project(2);
        //DataSet<Tuple2<Float, Integer>> wikiWeightDists = wikiWeights.flatMap(new WeightLister()).groupBy(0).sum(1);

        DataSet<Tuple2<String, String>> wikiEdges = wikiGraph.project(0,1);
        DataSet<Tuple2<String, Integer>> wikiDegreeCounts = wikiEdges.flatMap(new EdgeLister()).groupBy(0).sum(1);
        DataSet<Tuple1<Integer>> wikiDegrees = wikiDegreeCounts.project(1);
        DataSet<Tuple2<Integer, Integer>> wikiDegreeFreq = wikiDegrees.flatMap(new WeightLister()).groupBy(0).sum(1);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Visualizer visualizer = new Visualizer();
        InSituCollector collector = new InSituCollector(env, visualizer);
        ////////////////////////id  data   classes
        //collector.collect(1, edgeWeightFreq, Integer.class, Integer.class);
        collector.collect(2, degreeFreq, Float.class, Float.class);
        //collector.collectPlan(env.getExecutionPlan());
        //collector.collect(3, wikiWeightDists, Float.class, Integer.class);
        collector.collect(4,wikiDegreeFreq, Float.class, Float.class);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        wikiDegreeFreq.print();

        env.execute("Graph Analysis");

        //visualizer.visualizeBarChart(1, "Edge Weight Frequency", "Weight", "Frequency");
        visualizer.visualizeScatterPlot(2, "Degree Frequency", "Degree", "Frequency");
        //visualizer.visualizeBarChart(3, "Wikipedia Conflict", "Weight", "Frequency");
        visualizer.visualizeScatterPlot(4, "Wikipedia Conflict Degree Frequency", "Degree", "Frequency");
    }

    public static final class GraphParser implements FlatMapFunction<String, Tuple3<String, String, Float>> {

        public void flatMap(String value, Collector<Tuple3<String, String, Float>> out){

            String[] lines = value.toLowerCase().split("\\r?\\n");
            String[] tokens;

            for (String line : lines) {
                tokens = line.split("\\t?\\s");
                String from = tokens[0];
                String to = tokens[1];
                Float weight =  (Float.parseFloat(Integer.toString(Math.round(Float.valueOf(tokens[2]) * 1))))/1F;
                out.collect(new Tuple3<>(from, to, weight));
            }
        }
    }

    public static final class WeightLister implements FlatMapFunction<Tuple1<Integer>, Tuple2<Integer, Integer>>{

        public void flatMap(Tuple1<Integer> weight, Collector<Tuple2<Integer, Integer>> out){
            out.collect(new Tuple2<>(weight.f0, 1));
        }
    }

    public static final class EdgeLister implements FlatMapFunction<Tuple2<String, String>, Tuple2<String, Integer>>{

        public void flatMap(Tuple2<String, String> edge, Collector<Tuple2<String, Integer>> out){
            out.collect(new Tuple2<>(edge.f0, 1));
            out.collect(new Tuple2<>(edge.f1, 1));
        }
    }
}

package Examples;

import DataRecording.InSituCollector;
import Visualization.Visualizer;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by Jake on 5/22/2015.
 */
public class TextAnalysis {

    public static void main(String[] args) throws Exception{

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<String> text = getTextDataSet(env);

        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new Tokenizer()).groupBy(0).sum(1);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Visualizer visualizer = new Visualizer();
        InSituCollector inSituCollector = new InSituCollector(env, visualizer);
        inSituCollector.collect(1, counts, String.class, Integer.class);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        //counts.print();

        visualizer.visualizeWordCloud(1);
        env.execute("Text Analysis");
}

// *************************************************************************
//     USER FUNCTIONS
// *************************************************************************

/**
 * Implements the string tokenizer that splits sentences into words as a user-defined
 * FlatMapFunction. The function takes a line (String) and splits it into
 * multiple pairs in the form of "(word,1)" (Tuple2<String, Integer>).
 */
public static final class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {

    @Override
    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
        // normalize and split the line
        String[] tokens = value.toLowerCase().split("\\W+");

        // emit the pairs
        for (String token : tokens) {
            if (token.length() > 0) {
                out.collect(new Tuple2<String, Integer>(token, 1));

            }
        }
    }
}

    // *************************************************************************
    //     UTIL METHODS
    // *************************************************************************

    private static DataSet<String> getTextDataSet(ExecutionEnvironment env) {

        return env.readTextFile("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Text\\hamlet.txt");

    }
}

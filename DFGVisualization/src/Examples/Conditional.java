package Examples;

import DataRecording.InSituCollector;
import Visualization.Visualizer;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by Jake on 4/6/2015.
 */
public class Conditional {

    public static void main(String[] args) throws Exception{

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // get input data
        DataSet<Tuple2<String, Integer>> data = getCSVDataSet(env);

        // group by category and sum
        DataSet<Tuple2<String, Integer>> totals = data.flatMap(new Counter()).groupBy(0).sum(1);


        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Visualizer visualizer = new Visualizer();
        InSituCollector totalsCollector = new InSituCollector(visualizer, totals.getType());
        totalsCollector.collect(totals);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // emit result
        totals.print();

        // execute program
        visualizer.visualizeBarChart();
        env.execute("Conditional");
    }

    // *************************************************************************
    //     USER FUNCTIONS
    // *************************************************************************

    /**
     * Returns a tuple for every element in a category
      */
    public static final class Counter implements FlatMapFunction<Tuple2<String, Integer>, Tuple2<String, Integer>>{

        @Override
        public void flatMap(Tuple2<String, Integer> category, Collector<Tuple2<String, Integer>> out){
            out.collect(new Tuple2<>(category.f0, 1));
        }
    }

    private static DataSet<Tuple2<String,Integer>> getCSVDataSet(ExecutionEnvironment env) {

        DataSet<Tuple2<String, Integer>> source = env.readCsvFile("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Generic\\Test_Sums.csv")
                                                    .ignoreFirstLine().types(String.class, Integer.class);

        return source;
    }
}

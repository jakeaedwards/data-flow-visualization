package Examples;

import DataRecording.InSituCollector;
import Visualization.Visualizer;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by Jake on 6/30/2015.
 */
public class Census {

    public static void main(String[] args) throws Exception{

        // set up the execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // get input data
        DataSet<Tuple1<String>> data = getCSVDataSet(env);

        // group by category and sum
        DataSet<Tuple2<String, Integer>> totals = data.flatMap(new Counter()).groupBy(0).sum(1);

        DataSet<Tuple2<String,String>> ages = env.readCsvFile("resources\\Generic\\salaries.csv")
                                                .includeFields(true, false, false, false, false, false, false, false, false, false, false, false, false, false, true)
                                                .types(String.class, String.class);

        DataSet<Tuple1<String>> rich = ages.filter(new RichFilter()).project(0);
        DataSet<Tuple1<String>> poor = ages.filter(new PoorFilter()).project(0);

        DataSet<Tuple2<String,Integer>> richTotals = rich.flatMap(new Counter()).groupBy(0).sum(1);
        DataSet<Tuple2<String,Integer>> poorTotals = poor.flatMap(new Counter()).groupBy(0).sum(1);



        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Visualizer visualizer = new Visualizer();
        InSituCollector totalsCollector = new InSituCollector(env, visualizer);
        totalsCollector.collect(1, totals, String.class, Integer.class);
        totalsCollector.collect(2, richTotals, Float.class, Float.class);
        totalsCollector.collect(3, poorTotals, Float.class, Float.class);
        visualizer.visualizeBarChart(1, "Census Income Categories", "Category", "Count");
        visualizer.visualizeLineChart(2, ">50K Income Earners", "Age", "Count");
        visualizer.visualizeLineChart(3, "<=50K Income Earners", "Age", "Count");
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // emit result
        richTotals.print();

        // execute program
        env.execute("Conditional");
    }

    // *************************************************************************
    //     USER FUNCTIONS
    // *************************************************************************

    /**
     * Returns a tuple for every element in a category
     */
    public static final class Counter implements FlatMapFunction<Tuple1<String>, Tuple2<String, Integer>> {

        @Override
        public void flatMap(Tuple1<String> category, Collector<Tuple2<String, Integer>> out){
            out.collect(new Tuple2<>(category.f0, 1));
        }
    }

    public static final class RichFilter implements FilterFunction<Tuple2<String, String>> {

        public boolean filter(Tuple2<String, String> input){
            return input.getField(1).equals(">50K");
        }
    }

    public static final class PoorFilter implements FilterFunction<Tuple2<String, String>> {

        public boolean filter(Tuple2<String, String> input){
            return input.getField(1).equals("<=50K");
        }
    }

    private static DataSet<Tuple1<String>> getCSVDataSet(ExecutionEnvironment env) {

        DataSet<Tuple1<String>> source = env.readCsvFile("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Generic\\salaries.csv")
                .includeFields(false, false, false, false, false, false, false, false, false, false, false, false, false, false, true)
                .types(String.class);

        //39, State-gov, 77516, Bachelors, 13, Never-married, Adm-clerical, Not-in-family, White, Male, 2174, 0, 40, United-States, <=50K

        return source;
    }
}

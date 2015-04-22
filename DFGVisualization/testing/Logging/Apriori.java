package Logging;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.io.CsvReader;
import org.apache.flink.api.java.tuple.Tuple1;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * Created by Jake on 4/6/2015.
 *
 * Input: D(data in HDFS), min_sup(minimum support threshold),
 Output: L, frequent itemsets
 Method:
 (1) L 1 =find_frequt_1-itemsets(D);
 (2)for(k=2; L k −1 != ∅;k++){
 (3) C k =candidate_gen( L k −1 ,min_sup);
 (4) for each row of data t∈D{//scan D for counts
 (5) C t = map();//get the subset of the candidate itemsets
 (6) }
 (7) L k =Reduce();// get the subset of the frequent itemsets
 (8) }
 (9)return L=L∪ k L k ;
 */
public class Apriori {

    private static int MIN_SUPPORT_THRESHOLD = 3;

    public static void main(String[] args) throws Exception{

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<String> transactions = getTextDataSet(env, "C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Apriori\\trivial.csv");

        DataSet<Tuple2<String, Integer>> frequentItems = transactions.flatMap(new Tokenizer()).groupBy(0).sum(1);

        frequentItems.print();

        env.execute("Apriori");

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
            String[] tokens = value.toLowerCase().split(", ");

            // emit the pairs
            for (String token : tokens) {
                if (token.length() > 0) {
                    out.collect(new Tuple2<String, Integer>(token, 1));

                }
            }
        }
    }


    /**
     * Simply sums up all long values.
     */
    public static final class SumReducer implements ReduceFunction<Long>{

        @Override
        public Long reduce(Long value1, Long value2) throws Exception {

            return value1 + value2;
        }
    }

    // *************************************************************************
    //     UTIL FUNCTIONS
    // *************************************************************************

    private static DataSet<String> getTextDataSet(ExecutionEnvironment env, String path) {
        //Read text from given input path
        CsvReader reader = env.readCsvFile(path);
        Boolean[] mask = {false,true,true};

        DataSet<Tuple1<String>> items = reader.includeFields(false, true, true).tupleType(String);
        return items;
    }
}



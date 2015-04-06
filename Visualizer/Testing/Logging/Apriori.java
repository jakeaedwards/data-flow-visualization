package Logging;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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

    public static void main(String[] args){ //Modify arg path to be general

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        String filepath = args[0];

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // process the line.
            }
        }
        catch(IOException e){

        }

    }

    /**
     * Sampler randomly emits points that fall within a square of edge x * y.
     * It calculates the distance to the center of a virtually centered circle of radius x = y = 1
     * If the distance is less than 1, then and only then does it returns a 1.
     */
    public static class Sampler implements MapFunction<Long, Long> {

        @Override
        public Long map(Long value) throws Exception{
            double x = Math.random();
            double y = Math.random();
            return (x * x + y * y) < 1 ? 1L : 0L;
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
}


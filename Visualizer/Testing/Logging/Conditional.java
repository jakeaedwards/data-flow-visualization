package Logging;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
/**
 * Created by Jake on 4/6/2015.
 */
public class Conditional {

    public static void main(String[] args){

    }

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

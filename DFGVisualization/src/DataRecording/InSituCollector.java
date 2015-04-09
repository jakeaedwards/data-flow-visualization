package DataRecording;


import Data.InSituDataSet;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.core.fs.FileSystem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 3/23/2015.
 */
public class InSituCollector{

    ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
    InSituDataSet dataSet;
    String outputPath = "C:\\Users\\Jake\\Desktop\\TestOutput";

    public InSituCollector() {

    }

    /**
     * Collects a dataset taken from an external job graph and creates a local copy. This is achieved through storing
     * the contained data and then reading it into a new dataset which has the InSituCollector's ExecutionEnvironment
     * @param data External DataSet to be collected
     */
    public void collect(DataSet data){

        //Initialize local data set
        if(dataSet == null){
            dataSet = new InSituDataSet(env, data.getType());
        }

        //Write external data set to CSV
        data.writeAsCsv(outputPath, FileSystem.WriteMode.OVERWRITE);

        System.out.println(data.getType().getTypeClass().toString());
        DataSet<Tuple2<String, Integer>> temp = env.readCsvFile(outputPath).types(String.class, Integer.class);

        //Read data originally from external data set into internal one
        dataSet.union(temp);

    }

    public DataSet getData(){
        return dataSet;
    }

    public void output(){

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        List<Tuple2<String, Integer>> outData = new ArrayList<>();
        dataSet.writeAsCsv(outputPath + "\\final", FileSystem.WriteMode.OVERWRITE);



        //dataSet.output(new LocalCollectionOutputFormat(outData)).name("Test Sink");
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
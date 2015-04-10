package DataRecording;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.*;
import org.apache.flink.core.fs.FileSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jake on 3/23/2015.
 */
public class InSituCollector{

    ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
    List dataSet;
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
        dataSet = new ArrayList<>();

        //Write external data set to CSV
        data.writeAsCsv(outputPath, FileSystem.WriteMode.OVERWRITE);

        //Read data originally from external data set into internal one
        File dir = new File(outputPath);
        File[] directoryListing = dir.listFiles();
        BufferedReader reader = null;
        String line;
        int tupleSize = data.getType().getArity();
        TypeVariable[] tupleClasses = data.getType().getTypeClass().getTypeParameters();
        System.out.println(data.getType().getTypeClass().getDeclaredFields()[0]);
        //TODO: Find way around erasure obfuscation

        for(File file: directoryListing) {
            try {
                reader = new BufferedReader(new FileReader(file.getPath()));
                while ((line = reader.readLine()) != null) {


                    Tuple addedTuple = parseTuple(line, tupleSize);
                    dataSet.add(addedTuple);
                }
            }
            catch(IOException e){
                e.printStackTrace();
            }
            finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Tuple parseTuple(String line, int size){
        String[] tuple = line.split(",");
        Tuple created = null;

        for(int i = 0; i < size; i++){
            Boolean b = Boolean.parseBoolean(tuple[i]);
            if(b != null){

            }
        }

        return created;
    }

    public void output() throws Exception{

        System.out.println(dataSet.toString());


    }
}
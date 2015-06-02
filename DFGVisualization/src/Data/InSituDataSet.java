package Data;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.io.CsvOutputFormat;
import org.apache.flink.api.java.operators.DataSink;
import org.apache.flink.api.java.tuple.Tuple;

import java.util.ArrayList;

/**
 * Created to handle data collected by an InSituCollector.
 * Stores metadata reflecting point of collection and timing.
 */
public class InSituDataSet{

    ArrayList<Tuple> data;
    int id;
    int timestamp;

    public InSituDataSet(int id, ArrayList data){
        this.data = data;
        this.id = id;
    }

    public InSituDataSet(int id, int timestamp, ArrayList data){
        this.data = data;
        this.id = id;
        this.timestamp = timestamp;
    }

    public ArrayList<Tuple> getData(){
        return this.data;
    }

    public int getId(){
        return id;
    }

    public int getTimestamp(){
        return timestamp;
    }
}

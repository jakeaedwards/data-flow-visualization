package Data;

import org.apache.commons.lang3.Validate;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.io.CsvOutputFormat;
import org.apache.flink.api.java.operators.DataSink;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.core.fs.FileSystem;
import org.apache.flink.core.fs.Path;

/**
 * Created to handle data collected by an InSituCollector.
 * Extends the Flink dataset, meant to allow for additional functionality. Primarily timestamping and metadata tracking.
 */
public class InSituDataSet<T> extends DataSet{

    public InSituDataSet(ExecutionEnvironment context, TypeInformation type){
        super(context, type);
    }

    public String toString(){
        return this.print().toString();
    }

    public DataSink<T> writeAsCsv(String filePath) {
        System.out.println("writing from dataset");
        return this.writeAsCsv(filePath, "\n", CsvOutputFormat.DEFAULT_FIELD_DELIMITER);
    }

}

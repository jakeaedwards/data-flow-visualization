package Data;

import org.apache.flink.api.java.tuple.Tuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public void sortOn(int field){
        Collections.sort(data, new TupleComparator(field));
    }

    public class TupleComparator implements Comparator<Tuple>{

        private int field;

        public TupleComparator(int field){
            this.field = field;
        }

        public int compare(Tuple tuple1, Tuple tuple2){

            Class c = tuple1.getField(field).getClass();

            if(c.equals(Integer.class)){
                return Integer.compare((Integer) tuple1.getField(field), (Integer) tuple2.getField(field));
            }
            else if(c.equals(Double.class)){
                return Double.compare((Double) tuple1.getField(field),(Double) tuple2.getField(field));
            }
            else if(c.equals(Float.class)){
                return Float.compare((Float) tuple1.getField(field),(Float) tuple2.getField(field));
            }
            else if(c.equals(Character.class)){
                return Character.compare((Character) tuple1.getField(field),(Character) tuple2.getField(field));
            }
            else { //String
                return ((String) tuple1.getField(field)).compareTo((String) tuple2.getField(field));
            }
        }
    }
}

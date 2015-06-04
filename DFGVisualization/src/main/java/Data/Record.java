package Data;

import java.util.ArrayList;

/**
 * Created by Jake on 3/23/2015.
 */
public class Record {

    public static long timestamp; //Time in milliseconds
    public static ArrayList<Object> values; //Value of each data field

    public Record(long timestamp,ArrayList values){
        this.timestamp = timestamp;
        this.values = values;
    }

    public String toString(){
        String output = timestamp + ", ";

        for(int i = 0; i < values.size(); i++){
            if(i == values.size() - 1) {
                output += values.get(i).toString();
            }
            else{
                output += values.get(i).toString() + ", ";
            }
        }

        return output;
    }

    public int getSize(){
        return values.size();
    }

    public long getTimestamp(){
        return timestamp;
    }

    public ArrayList<Object> getValues(){
        return values;
    }
}

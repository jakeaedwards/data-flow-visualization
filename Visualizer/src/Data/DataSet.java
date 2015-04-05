package Data;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Jake on 3/23/2015.
 */
public class DataSet {

    private static String[] labels;  //String name of each data field
    private ArrayList<Record> data; //List of records

    public void addRecord(Record record){
        data.add(record);
    }

    public void addRecord(long timestamp, ArrayList values){
        Record added = new Record(timestamp, values);
        data.add(added);
    }

    public void setLabels(String[] labels){

        this.labels = new String[labels.length];
        this.labels = labels;
    }

    public String toString(){
        String output = "Timestamp (ns), ";

        for(int i = 0; i < labels.length; i++){
            if(i == labels.length - 1) {
                output += labels[i].toString();
            }
            else{
                output += labels[i].toString() + ", ";
            }
        }

        for(int i = 0; i < data.size(); i++){
            output += "/n" + data.get(i).toString();
        }

        return output;
    }

    public String[] getLabels(){
        return labels;
    }

    public double[] getAverage(){

        double[] averages = new double[data.get(0).getSize()];

        for(Record record : data){
            for(int i = 0; i < averages.length; i++){
                averages[i] += (Integer) record.getValues().get(i);
            }
        }

        for(int i = 0; i < averages.length; i++){
            averages[i] = averages[i]/data.size();
        }

        return averages;
    }
}

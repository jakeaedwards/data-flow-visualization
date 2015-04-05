package Testing;

import Visualization.Visualizer;
import Data.DataSet;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Jake on 4/1/2015.
 */
public class BasicTest {

    private static Visualizer visualizer = new Visualizer();
    private static DataSet dataset = new DataSet();

    public static void main(String[] args){

        String[] labels = {"time", "x","y"};
      /*  dataset.setLabels(labels);
        dataset.addRecord(System.currentTimeMillis(), new ArrayList<Integer>(Arrays.asList(1,5)));
        dataset.addRecord(System.currentTimeMillis(),new ArrayList<Integer>(Arrays.asList(2,4)));
        dataset.addRecord(System.currentTimeMillis(),new ArrayList<Integer>(Arrays.asList(3,3)));
        dataset.addRecord(System.currentTimeMillis(),new ArrayList<Integer>(Arrays.asList(4,2)));
        dataset.addRecord(System.currentTimeMillis(),new ArrayList<Integer>(Arrays.asList(5,1)));
        */

        visualizer.setDataSet(dataset);
        visualizer.visualize();
    }
}

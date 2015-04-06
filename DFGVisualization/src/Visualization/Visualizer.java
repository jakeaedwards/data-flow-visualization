package Visualization;

import Data.DataSet;

/**
 * Created by Jake on 4/1/2015.
 */
public class Visualizer {

    public DataSet dataSet;

    public void visualize(){
        new DisplayFrame().setVisible(true);
    }

    public void setDataSet(DataSet dataSet){
        this.dataSet = dataSet;
    }
}

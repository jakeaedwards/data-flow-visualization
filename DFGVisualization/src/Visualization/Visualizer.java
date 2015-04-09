package Visualization;

import Data.InSituDataSet;

/**
 * Created by Jake on 4/1/2015.
 */
public class Visualizer {

    public InSituDataSet dataSet;

    public void visualize(){
        new DisplayFrame().setVisible(true);
    }

    public void setDataSet(InSituDataSet dataSet){
        this.dataSet = dataSet;
    }
}

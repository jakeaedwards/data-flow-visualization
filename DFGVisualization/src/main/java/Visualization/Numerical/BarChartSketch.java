package Visualization.Numerical;

import Data.InSituDataSet;
import processing.core.*;
import org.gicentre.utils.stat.*;

/**
 * Created by Jake on 4/1/2015.
 */
public class BarChartSketch extends PApplet{

    private InSituDataSet dataSet;

    public BarChartSketch(InSituDataSet data) {
        this.dataSet = data;
        dataSet.sortOn(0);
    }

    BarChart barChart;

    public void setup(){
        size(500,500);

        barChart = new BarChart(this);
        barChart.setData(buildData());

        // Scaling
        barChart.setMinValue(0);
       // barChart.setMaxValue(15);

        // Axis appearance
        textFont(createFont("Serif",10),10);

        barChart.showValueAxis(true);
        //barChart.setValueFormat("#%");
        barChart.setBarLabels(buildLabels());
        barChart.showCategoryAxis(true);

        // Bar colours and appearance
        barChart.setBarColour(color(200,80,80,150));
        barChart.setBarGap(4);

        // Bar layout
        barChart.transposeAxes(true);
    }

    public void draw(){
        background(255);
        barChart.draw(15,15,width-30,height-30);
    }

    private float[] buildData(){
        float[] data = new float[dataSet.getData().size()];

        for(int i = 0; i < data.length; i++){
            Integer val = dataSet.getData().get(i).getField(1);
            data[i] = (float) val;
        }

        return data;
    }

    private String[] buildLabels(){
        String[] data = new String[dataSet.getData().size()];

        for(int i = 0; i < data.length; i++){
            data[i] = String.valueOf(dataSet.getData().get(i).getField(0));
        }

        return data;
    }
}

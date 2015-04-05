package Visualization;
import Data.DataSet;
import processing.core.*;

import org.gicentre.utils.stat.*;

/**
 * Created by Jake on 4/1/2015.
 */
public class BarChartSketch extends PApplet{

    private DataSet dataSet;

    public void BarChartSketch(DataSet dataSet){
        this.dataSet = dataSet;
    }

    BarChart barChart;

    public void setup(){
        size(500,500);

        barChart = new BarChart(this);
        barChart.setData(new float[] {0.76F, 0.24F, 0.39F, 0.18F, 0.20F});

        // Scaling
        barChart.setMinValue(0);
        barChart.setMaxValue(1);

        // Axis appearance
        textFont(createFont("Serif",10),10);

        barChart.showValueAxis(true);
        barChart.setValueFormat("#%");
        //barChart.setBarLabels(dataSet.getLabels());
        barChart.setBarLabels(new String[] {"A", "B", "C", "D", "E"});
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
}

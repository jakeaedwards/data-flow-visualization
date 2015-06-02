package Visualization.Numerical;

import Data.InSituDataSet;
import processing.core.PApplet;
import org.gicentre.utils.stat.*;

/**
 * Created by Jake on 5/30/2015.
 */
public class LineChartSketch extends PApplet {

    private InSituDataSet dataSet;
    public LineChartSketch(InSituDataSet data) {
        this.dataSet = data;
    }
    XYChart lineChart;

    public void setup(){
        size(500,200);
        textFont(createFont("Arial",10),10);

        // Both x and y data set here.
        lineChart = new XYChart(this);
        lineChart.setData(new float[] {1900, 1910, 1920, 1930, 1940, 1950,
                        1960, 1970, 1980, 1990, 2000},
                new float[] { 6322,  6489,  6401, 7657, 9649, 9767,
                        12167, 15154, 18200, 23124, 28645});

        // Axis formatting and labels.
        lineChart.showXAxis(true);
        lineChart.showYAxis(true);
        lineChart.setMinY(0);

        lineChart.setYFormat("$###,###");  // Monetary value in $US
        lineChart.setXFormat("0000");      // Year

        // Symbol colours
        lineChart.setPointColour(color(180,50,50,100));
        lineChart.setPointSize(5);
        lineChart.setLineWidth(2);
    }

    // Draws the chart and a title.
    public void draw(){
        background(255);
        textSize(9);
        lineChart.draw(15,15,width-30,height-30);

        // Draw a title over the top of the chart.
        fill(120);
        textSize(20);
        text("Income per person, United Kingdom", 70,30);
        textSize(11);
        text("Gross domestic product measured in inflation-corrected $US",
                70,45);
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
            data[i] = dataSet.getData().get(i).getField(0);
        }

        return data;
    }
}

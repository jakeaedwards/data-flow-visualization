package Visualization.Numerical;

import Data.InSituDataSet;
import processing.core.PApplet;
import org.gicentre.utils.stat.*;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Jake on 5/30/2015.
 */
public class LineChartSketch extends PApplet {

    private InSituDataSet dataSet;
    XYChart lineChart;
    private String title;
    private String xLabel;
    private String yLabel;

    public LineChartSketch(InSituDataSet data, String title, String xLabel, String yLabel) {
        this.dataSet = data;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.title = title;
    }

    public void setup(){
        size(800, 500);
        textFont(createFont("Arial", 10), 10);

        // Both x and y data set here.
        lineChart = new XYChart(this);
        lineChart.setData(buildData());

        // Axis formatting and labels.
        lineChart.showXAxis(true);
        lineChart.showYAxis(true);
        lineChart.setMinY(0);
        lineChart.setXAxisLabel("\n" + xLabel);
        lineChart.setYAxisLabel(yLabel + "\n");

        // Symbol colours
        lineChart.setPointColour(color(180, 50, 50, 100));
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
        text(title, 70,30);
    }

    private ArrayList<PVector> buildData(){
        ArrayList<PVector> data = new ArrayList();

        for(int i = 0; i < dataSet.getData().size(); i++){

            if(dataSet.getData().get(i).getField(0).getClass() == Float.class) {
                Float xVal = dataSet.getData().get(i).getField(0);
                Float yVal = dataSet.getData().get(i).getField(1);
                data.add(new PVector(xVal, yVal));
            }
            else{
                Float xVal = Float.parseFloat((String)dataSet.getData().get(i).getField(0));
                Float yVal = Float.parseFloat((String)dataSet.getData().get(i).getField(1));
                data.add(new PVector(xVal, yVal));
            }
        }


        Collections.sort(data, new Comparator<PVector>() {
            @Override
            public int compare(PVector o1, PVector o2) {
                if(o1.x < o2.x){
                    return -1;
                }
                else if (o1.x > o2.x){
                    return 1;
                }
                return 0;
            }

            @Override
            public boolean equals(Object obj) {
                return false;
            }
        });

        return data;
    }
}

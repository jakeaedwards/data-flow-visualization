package Visualization.Numerical;

import Data.InSituDataSet;
import org.gicentre.utils.stat.BarChart;
import processing.core.*;

/**
 * Created by Jake on 4/1/2015.
 */
public class BarChartSketch extends PApplet{

    private InSituDataSet dataSet;
    public String title;
    public String xLabel;
    public String yLabel;

    public BarChartSketch(InSituDataSet data, String title, String xLabel, String yLabel) {
        this.dataSet = data;
        this.title = title;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        dataSet.sortOn(0);
    }

    BarChart barChart;

    public void setup(){
        size(500,550);

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
        barChart.setCategoryAxisLabel("\n\n" + xLabel);
        barChart.setValueAxisLabel(yLabel + "\n");

        // Bar colours and appearance
        barChart.setBarColour(color(200,80,80,150));
        barChart.setBarGap(4);

        // Bar layout
        barChart.transposeAxes(true);
    }

    public void draw(){
        background(255);
                     //xor, yor, dimensions
        barChart.draw(15,45,width-30,height-130);

        // Draw a title over the top of the chart.
        fill(120);
        textSize(20);
        text(title, (width/4),30);
    }

    private float[] buildData(){
        float[] data = new float[dataSet.getData().size()];

        for(int i = 0; i < data.length; i++){
            System.out.println(dataSet.getData().get(i).getField(1));
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

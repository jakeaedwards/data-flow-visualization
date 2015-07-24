package Visualization.Numerical;

import Data.InSituDataSet;
import processing.core.PApplet;
import org.gicentre.utils.stat.*;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by Jake on 5/30/2015.
 */
public class ScatterplotSketch extends PApplet {

    private InSituDataSet dataSet;
    String title;
    String xLabel;
    String yLabel;
    XYChart scatterplot;

    public ScatterplotSketch(InSituDataSet data, String title, String xLabel, String yLabel) {
        this.dataSet = data;
        this.xLabel = xLabel;
        this.yLabel = yLabel;
        this.title = title;
    }

    public void setup(){
        size(500,500);
        textFont(createFont("Arial",11),11);

        // Both x and y data set here.
        scatterplot = new XYChart(this);

        
        scatterplot.setData(buildData());

        // Axis formatting and labels.
        scatterplot.showXAxis(true);
        scatterplot.showYAxis(true);
        //scatterplot.setXFormat("###,###");
        scatterplot.setXAxisLabel("\n" + xLabel);
        scatterplot.setYAxisLabel(yLabel + "\n");

        // Symbol styles
        scatterplot.setPointColour(color(180,50,50,100));
        scatterplot.setPointSize(5);
    }

    public void draw(){
        background(255);
        scatterplot.draw(20,20,width-60,height-60);

        fill(120);
        textSize(15);
        text(title, (width/4),30);
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

        return data;
    }
}

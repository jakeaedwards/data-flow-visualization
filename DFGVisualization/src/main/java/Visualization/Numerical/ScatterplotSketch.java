package Visualization.Numerical;

import Data.InSituDataSet;
import processing.core.PApplet;
import org.gicentre.utils.stat.*;

/**
 * Created by Jake on 5/30/2015.
 */
public class ScatterplotSketch extends PApplet {

    private InSituDataSet dataSet;
    public ScatterplotSketch(InSituDataSet data) {
        this.dataSet = data;
    }
    XYChart scatterplot;

    public void setup(){
        size(500,250);
        textFont(createFont("Arial",11),11);

        // Both x and y data set here.
        scatterplot = new XYChart(this);

        scatterplot.setData(new float[] {1900, 1910, 1920, 1930, 1940, 1950,
                        1960, 1970, 1980, 1990, 2000},
                new float[] { 6322,  6489,  6401, 7657, 9649, 9767,
                        12167, 15154, 18200, 23124, 28645});

        // Axis formatting and labels.
        scatterplot.showXAxis(true);
        scatterplot.showYAxis(true);
        scatterplot.setXFormat("$###,###");
        scatterplot.setXAxisLabel("\nAverage income per person "+
                "(inflation adjusted $US)");
        scatterplot.setYAxisLabel("Life expectancy at birth (years)\n");

        // Symbol styles
        scatterplot.setPointColour(color(180,50,50,100));
        scatterplot.setPointSize(5);
    }

    public void draw(){
        background(255);
        scatterplot.draw(20,20,width-40,height-40);
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

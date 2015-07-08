package Visualization.Numerical;

import Data.InSituDataSet;
import org.gicentre.utils.multisketch.EmbeddedSketch;
import org.gicentre.utils.stat.XYChart;
import processing.core.PVector;

import java.util.ArrayList;

/**
 * Created by Jake on 7/7/2015.
 */
public class EmbeddedScatterplot extends EmbeddedSketch {

    private InSituDataSet dataSet;
    XYChart scatterplot;

    public EmbeddedScatterplot(InSituDataSet data) {
        this.dataSet = data;
    }

    public void setup(){
        size(500,500);
        textFont(createFont("Arial",11),11);

        // Both x and y data set here.
        scatterplot = new XYChart(this);
        scatterplot.setData(buildData());

        // Axis formatting and labels.
        scatterplot.showXAxis(false);
        scatterplot.showYAxis(false);

        // Symbol styles
        scatterplot.setPointColour(color(180,50,50,100));
        scatterplot.setPointSize(3);
    }

    public void draw(){
        background(255);
        scatterplot.draw(20,20,width-60,height-60);
    }

    private ArrayList<PVector> buildData(){
        ArrayList<PVector> data = new ArrayList();

        for(int i = 0; i < dataSet.getData().size(); i++){
            Float xval = dataSet.getData().get(i).getField(0);
            Float yval = dataSet.getData().get(i).getField(1);
            data.add(new PVector(xval, yval));
        }

        return data;
    }
}

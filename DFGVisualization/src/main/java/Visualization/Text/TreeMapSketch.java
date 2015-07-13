package Visualization.Text;

import Data.InSituDataSet;
import org.gicentre.treemappa.PTreeMappa;
import processing.core.PApplet;

/**
 * Created by Jake on 7/11/2015.
 */
public class TreeMapSketch extends PApplet{

    PTreeMappa pTreeMappa;    // Stores the treemap.
    InSituDataSet dataSet;

    public TreeMapSketch(InSituDataSet dataSet){
        this.dataSet = dataSet;
    }

    public void setup() {

        size(500,500);
        smooth();
        noLoop();

        // Create an empty treemap.
        pTreeMappa = new PTreeMappa(this);

        // Load the data and build the treemap.
        pTreeMappa.readData("C:\\Users\\Jake\\Documents\\GitHub\\data-flow-visualization\\DFGVisualization\\resources\\Generic\\life.csv");
    }

    public void draw() {

        background(255);

        // Get treemappa to draw itself.
        pTreeMappa.draw();
    }
}

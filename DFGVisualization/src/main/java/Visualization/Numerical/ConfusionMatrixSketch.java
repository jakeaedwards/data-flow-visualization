package Visualization.Numerical;

import Data.InSituDataSet;
import org.apache.commons.math.util.MathUtils;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.gicentre.utils.multisketch.EmbeddedSketch;
import org.gicentre.utils.multisketch.SketchPanel;
import processing.core.PApplet;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Jake on 7/29/2015.
 */
public class ConfusionMatrixSketch extends PApplet {

    InSituDataSet dataSet;

    public MatrixSketch(InSituDataSet dataSet){
        this.dataSet = dataSet;
    }

    public void setup(){

        //Determine number of fields and grid size

        int fieldCount = 0;
        Tuple sampleTuple = dataSet.getData().get(0);

        for(int i = 0; i < sampleTuple.getArity(); i++){
            if(sampleTuple.getField(i).getClass() == Float.class ||
                    sampleTuple.getField(i).getClass() == Double.class ||
                    sampleTuple.getField(i).getClass() == Integer.class){
                fieldCount++;
            }
        }

        int numPlots = (int) MathUtils.factorial(fieldCount - 1);

        size(800,800);
        setLayout(new GridLayout(fieldCount,fieldCount));
        noLoop();

        for(int i = 0; i < fieldCount; i++){
            for(int j = 0; j < fieldCount; j++){

                EmbeddedSketch sketch;

                if(i == j){ //Main diagonal
                    sketch = new TextSketch("Variable " + (i + 1), 200, 200);
                }
                else{
                    ArrayList<Tuple2<Float, Float>> subdata = new ArrayList<>();

                    for(Tuple tuple : dataSet.getData()){
                        subdata.add(new Tuple2(tuple.getField(i),tuple.getField(j)));
                    }

                    InSituDataSet temp = new InSituDataSet(987,subdata);
                    sketch = new EmbeddedScatterplot(temp);
                }

                SketchPanel sp = new SketchPanel(this,sketch);
                add(sp);
                sketch.setIsActive(true);
            }
        } {
}

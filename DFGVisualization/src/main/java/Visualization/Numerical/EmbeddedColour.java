package Visualization.Numerical;

import org.gicentre.utils.multisketch.EmbeddedSketch;

/**
 * Created by Jake on 7/29/2015.
 */
public class EmbeddedColour extends EmbeddedSketch {

    private Float value;

    public EmbeddedColour(Float value) {
        this.value = value;
    }

    public void setup(){
        size(500,500);
        textFont(createFont("Arial",11),11);
    }

    public void draw(){
        background(255);
    }
}

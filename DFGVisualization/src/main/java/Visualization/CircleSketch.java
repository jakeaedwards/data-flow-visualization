package Visualization;
import processing.core.*;

/**
 * Created by Jake on 4/1/2015.
 */
public class CircleSketch extends PApplet {

    public void setup() {
        size(400, 400);
        background(0);
    }
    public void draw() {
        background(0);
        fill(200);
        ellipseMode(CENTER);
        ellipse(mouseX,mouseY,40,40);
    }
}
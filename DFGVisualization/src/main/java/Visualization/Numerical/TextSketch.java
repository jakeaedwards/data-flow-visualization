package Visualization.Numerical;

import org.gicentre.utils.multisketch.EmbeddedSketch;

/**
 * Created by Jake on 7/7/2015.
 */
public class TextSketch extends EmbeddedSketch {

    private String text;
    private Integer xSize;
    private Integer ySize;

    public TextSketch(String text, int xSize, int ySize){
        this.text = text;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void setup(){
        size(xSize, ySize);
        textFont(createFont("SansSerif", 24), 24);
        fill(20, 120, 20);
    }

    // Displays some text and animates a change in size.
    public void draw(){
        super.draw();
        background(214, 214, 214);

        fill(120);
        textSize(15);
        textAlign(CENTER, CENTER);
        text(text, width/2, height/2);

    }
}

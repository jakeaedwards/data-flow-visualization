package Visualization.Text;
import processing.core.*;
import wordcram.*;

/**
 * Created by Jake on 5/21/2015.
 */
public class WordCloudSketch extends PApplet{

    public void setup(){
        size(700, 400);
        background(255);
    }

    public void draw() {


        // Each Word object has its word, and its weight.  You can use whatever
        // numbers you like for their weights, and they can be in any order.
        Word[] wordArray = new Word[]{
                new Word("Hello", 100),
                new Word("Test", 60)
        };

        // Pass in the sketch (the variable "this"), so WordCram can draw to it.
        WordCram wordcram = new WordCram(this)

                // Pass in the words to draw.
                .fromWords(wordArray);

        // Now we've created our WordCram, we can draw it:
        wordcram.drawAll();
        noLoop();
    }
}

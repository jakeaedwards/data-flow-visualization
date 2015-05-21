package Visualization;

import processing.core.PApplet;

import java.util.ArrayList;

/**
 * Created by Jake on 4/1/2015.
 */
public class DisplayFrame extends javax.swing.JFrame {

    public DisplayFrame(PApplet sketch){
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        javax.swing.JPanel panel = new javax.swing.JPanel();
        panel.add(sketch);
        this.add(panel);
        sketch.init();

        //Initialization is slow enough that sketch size needs time to set
        try {
            Thread.sleep(1000);
        } catch(InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        panel.setBounds(20, 20, sketch.getWidth(), sketch.getHeight());
        this.setSize(sketch.getWidth(), sketch.getHeight());
        this.setVisible(true);
    }
}
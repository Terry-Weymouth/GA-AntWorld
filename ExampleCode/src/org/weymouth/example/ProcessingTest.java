package org.weymouth.example;

// Example from: https://stackoverflow.com/questions/36565159/adding-processing-3-to-a-jpanel/36695510#36695510

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT.SmoothCanvas;
import processing.core.PApplet;
import processing.core.PSurface;

public class ProcessingTest extends PApplet{

    public void settings(){
        size(200, 200);
    }

    public void draw(){
        background(0);
        ellipse(mouseX, mouseY, 20, 20);
    }

    public static void main(String[] args){

        //create your JFrame
        JFrame frame = new JFrame("JFrame Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create your sketch
        ProcessingTest pt = new ProcessingTest();

        //get the PSurface from the sketch
        PSurface ps = pt.initSurface();

        //initialize the PSurface
        ps.setSize(200, 200);

        //get the SmoothCanvas that holds the PSurface
        SmoothCanvas smoothCanvas = (SmoothCanvas)ps.getNative();

        //SmoothCanvas can be used as a Component
        frame.add(smoothCanvas);

        //make your JFrame visible
        frame.setSize(200, 200);
        frame.setVisible(true);

        //start your sketch
        ps.startThread();
    }
}
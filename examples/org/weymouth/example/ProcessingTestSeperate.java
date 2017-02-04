package org.weymouth.example;

import processing.core.PApplet;

// from https://stackoverflow.com/questions/36565159/adding-processing-3-to-a-jpanel/36695510#36695510

public class ProcessingTestSeperate extends PApplet{

    public void settings(){
        size(200, 200);
    }

    public void draw(){
        background(0);
        ellipse(mouseX, mouseY, 20, 20);
    }

    public static void main(String[] args){
        PApplet.main("org.weymouth.example.ProcessingTestSeperate");
    }
	
}

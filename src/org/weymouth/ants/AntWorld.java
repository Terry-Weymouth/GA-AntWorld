package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorld extends PApplet{
	
	private static int HEIGHT = 1000;
	private static int WIDTH = 1000;
	private static int NUMBER_OF_ANTS = 1000;

	private List<Ant> ants = new ArrayList<Ant>();
	AntBrain brain = new AntBrain();
	
	public void settings(){
		size(WIDTH, HEIGHT);
    }

	public void setup() {
		for (int i = 0; i < NUMBER_OF_ANTS; i++) {
			float x = 10 + this.random(HEIGHT-20);
			float y = 10 + this.random(WIDTH-20);
			ants.add(new Ant(this, brain ,x ,y));
		}
		
	}

	public void draw() {
		background(100);
		for (Ant ant: ants) {
			ant.update();
			ant.move();
			ant.display();
		}
	}

	public Location randomPoint() {
		return  new Location(10 + this.random(HEIGHT-20),10 + this.random(WIDTH-20));
	}
	
	public void mouseClicked(MouseEvent event){
		int x = event.getX();
		int y = event.getY();
		for (Ant ant: ants) {
			ant.setTarget(new Location(x, y));
		}
	}
	
}

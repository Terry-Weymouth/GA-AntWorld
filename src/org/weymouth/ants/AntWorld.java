package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorld extends PApplet{
	
	private static int HEIGHT = 1000;
	private static int WIDTH = 1000;
	private static int NUMBER_OF_ANTS = 100;

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
	
	int count = 0;
	public void draw() {
		count ++;
		if ((count % 100) == 0) {
			System.out.println("Rounds = " + count + ", ants = " + ants.size());
			if (ants.size() == 0) stop();
		}
		background(100);
		
		List<Ant> dead = new ArrayList<Ant>();
		for (Ant ant: ants) {
			ant.update();
			ant.move();
			ant.display();
			if (ant.getHealth() < 0) {
				dead.add(ant);
			}
		}
		
		ants.removeAll(dead);
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

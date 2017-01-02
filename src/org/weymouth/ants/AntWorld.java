package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorld extends PApplet{
	
	private static int HEIGHT = 800;
	private static int WIDTH = 800;
	private static int NUMBER_OF_ANTS = 20;
	private static int NUMBER_OF_MEALS = 300;
	
	private static double SENSING_RADIUS = 20.0;
	
	private List<Ant> ants = new ArrayList<Ant>();
	private List<Food> meals = new ArrayList<Food>();
	AntBrain brain = new AntBrain();
	AntSensor sensor = new AntSensor(SENSING_RADIUS);
	
	public void settings(){
		size(WIDTH, HEIGHT);
    }

	public void setup() {
		for (int i = 0; i < NUMBER_OF_ANTS; i++) {
			ants.add(new Ant(this, brain , sensor, randomPoint()));
		}
		for (int i = 0; i < NUMBER_OF_MEALS; i++) {
			meals.add(new Food(randomPoint()));
		}
	}
	
	int count = 0;
	public void draw() {
		count ++;
		if ((count % 100) == 0) {
			System.out.println("Rounds = " + count + ", ants = " + ants.size() + ", meals = " + meals.size());
//			if (ants.size() == 0) stop();
		}
		background(100);
		
		List<Ant> dead = new ArrayList<Ant>();
		for (Food meal: meals) {
			display(meal);
		}
		for (Ant ant: ants) {
			ant.sense(meals);
			ant.update();
			ant.move();
			ant.feed(meals);
			display(ant);
			if (ant.getHealth() < 0) {
				dead.add(ant);
			}
		}
		
		ants.removeAll(dead);
	}

	private void display(Ant ant) {
		fill(255,100);
		noStroke();
		ellipse(ant.internalState.x, ant.internalState.y, 10.0f, 10.0f);
	}

	private void display(Food meal) {
		float x = meal.getXFloat() - 5.0F;
		float y = meal.getYFloat() - 5.0f;
		noStroke();
		fill(255,100);
		this.rect(x, y, 10.0f, 10.0f);
	}

	public Location randomPoint() {
		return  new Location(10 + this.random(HEIGHT-20),10 + this.random(WIDTH-20));
	}
	
	public void mouseClicked(MouseEvent event){
		int x = event.getX();
		int y = event.getY();
		for (int i = 0; i < NUMBER_OF_ANTS; i++) {
			Ant ant = new Ant(this, brain ,randomPoint());
			ants.add(ant);
			ant.setTarget(new Location(x, y));
		}
		for (int i = 0; i < NUMBER_OF_MEALS; i++) {
			meals.add(new Food(randomPoint()));
		}
	}
	
}

package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorld extends PApplet{
	
	private static int HEIGHT = 1000;
	private static int WIDTH = 1000;
	private static int NUMBER_OF_ANTS = 100;
	private static int NUMBER_OF_MEALS = 300;

	private List<Ant> ants = new ArrayList<Ant>();
	private List<Food> meals = new ArrayList<Food>();
	AntBrain brain = new AntBrain();
	
	public void settings(){
		size(WIDTH, HEIGHT);
    }

	public void setup() {
		ants.add(new Ant(this,brain,new Location(500,500)));
//		for (int i = 0; i < NUMBER_OF_ANTS; i++) {
//			ants.add(new Ant(this, brain ,randomPoint()));
//		}
//		for (int i = 0; i < NUMBER_OF_MEALS; i++) {
//			meals.add(new Food(randomPoint()));
//		}
		int x = 505;
		int y = 505;
		for (int i = 0; i < NUMBER_OF_MEALS; i++) {
			Location l = new Location(x,y);
			x += 5;
			y += 5;
			meals.add(new Food(l));
		}
	}
	
	int count = 0;
	public void draw() {
		count ++;
		if ((count % 100) == 0) {
			System.out.println("Rounds = " + count + ", ants = " + ants.size() + ", meals = " + meals.size());
			if (ants.size() == 0) stop();
		}
		background(100);
		
		List<Ant> dead = new ArrayList<Ant>();
		for (Food meal: meals) {
			display(meal);
		}
		for (Ant ant: ants) {
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
		for (Ant ant: ants) {
			ant.setTarget(new Location(x, y));
		}
	}
	
}

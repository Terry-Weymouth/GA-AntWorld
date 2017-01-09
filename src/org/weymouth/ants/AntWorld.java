package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorld extends PApplet{
	
	static int HEIGHT = 800;
	static int WIDTH = 800;
	private static int NUMBER_OF_ANTS = 20;
	private static int NUMBER_OF_MEALS = 300;
	
	private static double SENSING_RADIUS = 20.0;
	
	static double NOMRAL_SPEED = 10.0;
	
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
			if (ants.size() == 0) System.exit(0);
			for (Ant ant: ants) {
				ant.jitter();
			}
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
			if (ant.getHealth() < 0) {
				dead.add(ant);
			} else {
				display(ant);
			}
		}
		
		ants.removeAll(dead);
	}

	private void display(Ant ant) {
		fill(255,100);
		noStroke();
		drawAnt(ant);
	}

	private void drawAnt(Ant ant) {
		drawAntBody(ant);
		drawAntHead(ant);
	}
	
	private void drawAntBody(Ant ant) {
		float bodyRadius = 10.0f;
		ellipse(ant.location.getFloatX(), ant.location.getFloatY(), bodyRadius, bodyRadius);
	}

	private void drawAntHead(Ant ant) {
		double ang1 = ant.heading;
		double ang2 = ant.heading + 90.0;
		double ang3 = ant.heading - 90.0;
		double r1 = 15.0;
		double r2 = 5.0;
		Location l1 = new Location(ant.location.x + Compass.dxForThetaR(ang1,r1), ant.location.y + Compass.dyForThetaR(ang1,r1));
		Location l2 = new Location(ant.location.x + Compass.dxForThetaR(ang2,r2), ant.location.y + Compass.dyForThetaR(ang2,r2));
		Location l3 = new Location(ant.location.x + Compass.dxForThetaR(ang3,r2), ant.location.y + Compass.dyForThetaR(ang3,r2));
		beginShape();
		vertex(l1.getFloatX(),l1.getFloatY());
		vertex(l2.getFloatX(),l2.getFloatY());
		vertex(l3.getFloatX(),l3.getFloatY());
		endShape();
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
		setup();
	}

	public void conform(Ant ant) {
		double x = ant.location.x;
		double y = ant.location.y;
		double tx = (double) WIDTH/2;
		double ty = (double) HEIGHT/2;
		if ( (x < 0) || (x > WIDTH) || (y < 0) || (y > HEIGHT) ) {
			ant.heading = Compass.headingForDelta(tx - x , ty - y);
		}
	}
	
}

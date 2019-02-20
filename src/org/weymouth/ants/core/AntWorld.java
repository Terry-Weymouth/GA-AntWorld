package org.weymouth.ants.core;

import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorld extends PApplet {
	
	public static final double SENSING_RADIUS = 20.0;
	public static final int NUMBER_OF_BRAINS = 3;
	public static final int[] BRAIN_LAYER_WIDTHS = {6,8,7,2};
	public static final int NUMBER_OF_ROUNDS = 5;
	
	static final int NUMBER_OF_ANTS = 10;
	static final int NUMBER_OF_MEALS = 1000;
	static final int HEIGHT = 800;
	static final int WIDTH = 800;
	static final int MARGIN = 20 + NUMBER_OF_ANTS*20;
	
	private Generation g = null;
	
	private List<AntBrain> brains;
	
	private int currentBrainIndex = -1;
	
	public void settings(){
		size(WIDTH + MARGIN, HEIGHT);
    }

	public void setup(List<AntBrain> brains_in) {
		brains = brains_in;
		if (g != null) {
			System.out.println("Generation average: " + g.getAverageScore());
		}
		currentBrainIndex += 1;
		System.out.println("Brain index = " + currentBrainIndex + ", of " + brains.size());
		if (currentBrainIndex < brains.size()) {
			AntBrain b = brains.get(currentBrainIndex);
			g = new Generation(this, b);
		} else {
			exit();
		}
	}
	
	public void draw() {
		if (g == null || !g.oneStep())
			setup(AntBrain.starterList());
		background(100);
		drawMargin();
		for (Food meal: g.getMeals()) {
			display(meal);
		}
		for (Ant ant: g.getAnts()) {
			display(ant);
		}
	}
	
	private void drawMargin() {
		fill(255,255);
		rect(WIDTH, 0, MARGIN, HEIGHT);
		fill(0,255);
		float x = 10.0f + WIDTH;
		float y = 20.0f;
		float height = 200.0f;
		float w = 19.0f;
		float maxHealth = 0;
		Ant maxAnt = null;
		for (Ant ant: g.getAnts()) {
			float health = (float)ant.getHealth();
			if (health > maxHealth) {
				maxHealth = health;
				maxAnt = ant;
			}
			float offset = (1.0f - health) * height;
			float h = health * height;
			rect(x, y + offset, w, h);
			x += 20.0f;
		}
		if (!g.getAnts().isEmpty()) {
			double[] inputs = maxAnt.getSensoryInput();
			x = 10.0f + WIDTH;
			y = 40.0f + height;
			for (double value: inputs) {
				float gray = (float)(1.0f -value) * 255;
				fill(gray, 255);
				rect(x, y, 19, 19);
				x += 20.0f;
			}

			double[] outputs = maxAnt.getBrain().getOutputs();
			x = 10.0f + WIDTH;
			y += 40.0f;
			for (double value: outputs) {
				float gray = (float)(1.0f -value) * 255;
				fill(gray, 255);
				rect(x, y, 19, 19);
				x += 20.0f;
			}

		}
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
		rect(x, y, 10.0f, 10.0f);
	}

	public void mouseClicked(MouseEvent event){
		System.out.println("Mouse was clicked at: " + event.getX() + ", " + event.getY());
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

	public List<Food> getMeals() {
		return g.getMeals();
	}

	public List<Ant> getAnts() {
		return g.getAnts();
	}

	public void setBrain(AntBrain antBrain) {
		// TODO Auto-generated method stub
		
	}

	public boolean update() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}

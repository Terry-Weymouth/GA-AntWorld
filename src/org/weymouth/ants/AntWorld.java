package org.weymouth.ants;

import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorld extends PApplet{
	
	public static double SENSING_RADIUS = 20.0;
	public static int NUMBER_OF_BRAINS = 20;
	public static int[] BRAIN_LAYER_WIDTHS = {6,8,7,2};
	public static final int NUMBER_OF_ROUNDS = 5;
	
	static int HEIGHT = 800;
	static int WIDTH = 800;
	static int NUMBER_OF_ANTS = 10;
	static int NUMBER_OF_MEALS = 300;
	
	List<AntBrain> brains = AntBrain.starterList();

	private Generation g = null;
	
	int currentBrainIndex = -1;
	
	public void settings(){
		size(WIDTH, HEIGHT);
    }
	
	public void setup() {
		currentBrainIndex++;
		if (g != null) {
			System.out.println("Generation average: " + g.getAverageScore());
		}
		if (currentBrainIndex < brains.size()) {
			AntBrain b = brains.get(currentBrainIndex);
			g = new Generation(this, b);
		}
		else {
			System.exit(0);
		}
	}
	
	public void draw() {
		if (!g.oneStep())
			setup();
		background(100);
		
		for (Food meal: g.getMeals()) {
			display(meal);
		}
		for (Ant ant: g.getAnts()) {
			display(ant);
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
		this.rect(x, y, 10.0f, 10.0f);
	}

	public void mouseClicked(MouseEvent event){
		System.out.println("Mouse was clicked");
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

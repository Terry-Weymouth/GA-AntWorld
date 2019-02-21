package org.weymouth.ants.core;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorldView extends PApplet {
	
	static final int MARGIN = 20 + AntWorld.NUMBER_OF_ANTS*20;

	private List<Ant> ants = new ArrayList<Ant>();
	private List<Food> meals = new ArrayList<Food>();
	
	public void settings(){
		size(AntWorld.WIDTH + MARGIN, AntWorld.HEIGHT);
		AntWorldViewController.getController().register(this);
    }
	
	public void draw() {
		background(100);
		drawMargin();
		
		for (Food meal: meals) {
			display(meal);
		}
		for (Ant ant: ants) {
			display(ant);
		}
	}
	
	private void drawMargin() {
		fill(255,255);
		rect(AntWorld.WIDTH, 0, MARGIN, AntWorld.HEIGHT);
		fill(0,255);
		float x = 10.0f + AntWorld.WIDTH;
		float y = 20.0f;
		float height = 200.0f;
		float w = 19.0f;
		float maxHealth = 0;
		Ant maxAnt = null;
		for (Ant ant: ants) {
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

		if (maxAnt != null) {
			double[] inputs = maxAnt.getSensoryInput();
			x = 10.0f + AntWorld.WIDTH;
			y = 40.0f + height;
			for (double value: inputs) {
				float gray = (float)(1.0f -value) * 255;
				fill(gray, 255);
				rect(x, y, 19, 19);
				x += 20.0f;
			}

			double[] outputs = maxAnt.getBrain().getOutputs();
			x = 10.0f + AntWorld.WIDTH;
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

	public void update(List<Ant> cloneAnts, List<Food> cloneMeals) {
		ants = cloneAnts;
		meals = cloneMeals;
	}

}

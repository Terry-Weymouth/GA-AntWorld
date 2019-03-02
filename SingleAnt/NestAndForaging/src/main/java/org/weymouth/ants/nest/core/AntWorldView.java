package org.weymouth.ants.nest.core;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.event.MouseEvent;

public class AntWorldView extends PApplet {
	
	static final int MARGIN = 20 + Math.max(AntWorld.NUMBER_OF_ANTS * 2, 17)*20;
	
	float diameter = AntWorld.NEST_RADIUS*2;
	float rSquared = AntWorld.NEST_RADIUS*AntWorld.NEST_RADIUS;
	float cx = AntWorld.WIDTH/2;
	float cy = AntWorld.HEIGHT/2;

	private List<Ant> ants = new ArrayList<Ant>();
	private List<Food> meals = new ArrayList<Food>();
	
	public void settings(){
		size(AntWorld.WIDTH + MARGIN, AntWorld.HEIGHT);
		AntWorldViewController.getController().register(this);
    }
	
	public void close() {
		this.exit();
	}
	
	public void draw() {
		background(100);
		
		for (Food meal: meals) {
			display(meal);
		}
		
		drawNest();
		
		for (Ant ant: ants) {
			display(ant);
		}

		drawMargin(ants);
	}
	
	private void drawNest() {
		noFill();
		stroke(0,0,255,200);
		ellipse(cx, cy, diameter, diameter);
	}
	
	public void drawMargin(List<Ant> ants) {
		fill(255,255);
		stroke(0);
		rect(AntWorld.WIDTH, 0, MARGIN, AntWorld.HEIGHT);
		noStroke();
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
			stroke(0,0,255);
			line(x, y-1.0f, x+w, y-1.0f);
			noStroke();
			x += 40.0f;
		}
		fill(255, 255 ,0, 255);
		x = 30.0f + AntWorld.WIDTH;
		for (Ant ant: ants) {
			float carrying = (float) ant.getCarrying();
			float offset = (1.0f - carrying) * height;
			float h = carrying * height;
			rect(x, y + offset, w, h);
			stroke(0,0,255);
			line(x, y-1.0f, x+w, y-1.0f);
			noStroke();
			x += 40.0f;
		}

		if (maxAnt != null) {
			double[][] netValues = maxAnt.getBrain().getNetwork().getLayerValues();
			float maxNetWidth = (float)AntWorld.MAX_LAYER_WIDTH;
			float baseX = 10.0f + AntWorld.WIDTH;
			y = 40.0f + height;
			
			stroke(0,0,255);
			for (double[] row: netValues) {
				x = baseX + (20.0f * (maxNetWidth-row.length)/2f );
				y += 40.0f;
				for (double value: row) {
					float gray = (float)(1.0f - value) * 255;
					fill(gray, 255);
					rect(x, y, 19, 19);
					x += 20.0f;
				}				
			}
			noStroke();
		}
	}

	private void display(Ant ant) {
		noFill();
		stroke(0);
		drawAntSee(ant);
		fill(255,100);
		noStroke();
		drawAnt(ant);
	}
	
	private void drawAntSee(Ant ant) {
		float center = (float)ant.heading;
		float start = radians(center - 45.0f);
		float end = radians(center + 45.0f);
		float x = (float)ant.location.x;
		float y = (float)ant.location.y;
		float r = (float)AntWorld.SENSING_RADIUS;
		arc(x, y, r, r, start, end, PIE);
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

	public void display(Food meal) {
		float x = meal.getXFloat() - 5.0F;
		float y = meal.getYFloat() - 5.0f;
		float dx = x - cx;
		float dy = y - cy;
		float distSquared = dx*dx + dy*dy;
		boolean inside = distSquared < rSquared;
		noStroke();
		if (inside) {
			fill(255,255,0,100);
		} else {
			fill(255,100);
		}
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

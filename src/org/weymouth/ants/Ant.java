package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

public class Ant {
	
	private AntWorld pa;
	
	private AntBrain brain;

	AntState internalState = new AntState();
	
	private Location target;
	
	private int health;
	
	public Ant(AntWorld p, AntBrain brain,  Location l) {
		this.pa = p;
		this.brain = brain;
		internalState.x = (float) l.x;
		internalState.y = (float) l.y;
		internalState.speed = 1.0f;
		target = pa.randomPoint();
		health = (int)(pa.random(1000));
	}

	public void update() {
		float newHeading = brain.action(internalState,target);
		internalState.heading = newHeading;
	}

	public void move() {
		if (canMove(internalState,target)) {
			double r = (double)internalState.speed;
			double t = (double)internalState.heading;
			float dx = (float) Compass.dxForThetaR(t, r);
			float dy = (float) Compass.dyForThetaR(t, r);
			internalState.dx = dx;
			internalState.dy = dy;
			internalState.x += dx;
			internalState.y += dy;
			health--;
		} else {
			target = Util.cycleTarget();
//			target = pa.randomPoint();
		}
	}
	
	public boolean canMove(AntState internalState, Location target) {
		if (distanceToTarget(internalState,target) > 5.0) return true; 
		return false;
	}

	private double distanceToTarget(AntState internalState, Location target) {
		double dx = target.x - (double) internalState.x;
		double dy = target.y - (double) internalState.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public void setTarget(Location location) {
		target = location;
	}

	public int getHealth(){
		return health;
	}

	public void feed(List<Food> meals) {
		double x1 = (double) internalState.x - internalState.dx;
		double y1 = (double) internalState.y - internalState.dy;
		double x2 = (double) internalState.x;
		double y2 = (double) internalState.y;
		List<Food> eaten = new ArrayList<Food>();
		for (Food meal: meals) {
			double d = Util.shortestDistance(x1, y1, x2, y2, meal.x, meal.y);
			if (d < 10.0) {
				if (health < 10) {
					eaten.add(meal);
					System.out.println("   ---- health = " + health);
					health++;
					System.out.println("   " + x1 + ", " + y1);
					System.out.println("   " + x2 + ", " + y2);
					System.out.println("   " + meal.x + ", " + meal.y);
					System.out.println("   ---- health = " + health);
				}
			}
		}
		meals.removeAll(eaten);
	}

}

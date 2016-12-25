package org.weymouth.ants;

public class Ant {
	
	private AntWorld pa;
	
	private AntBrain brain;

	private AntState internalState = new AntState();
	
	private Location target;
	
	public Ant(AntWorld p, AntBrain brain, float x, float y) {
		this.pa = p;
		this.brain = brain;
		internalState.x = x;
		internalState.y = y;
		internalState.speed = 1.0f;
		target = pa.randomPoint();
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
			internalState.x += dx;
			internalState.y += dy;
		} else {
			target = pa.randomPoint();
		}
	}

	public void display() {
		pa.fill(255,100);
		pa.noStroke();
		pa.ellipse(internalState.x, internalState.y, 10.0f, 10.0f);
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

}

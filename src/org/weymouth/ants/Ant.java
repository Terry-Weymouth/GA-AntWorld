package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

public class Ant {
	
	private static int MAX_HEALTH = 1000;
	private static double FOOD_GRASPING_RANGE = 10.0;
	private static double FOOD_HEALTH = 10.0;
	
	private AntWorld pa;
	
	private AntBrain brain;
	
	private AntSensor sensor;
	
	Location location = new Location();
	Location oldLocation = new Location();
	double heading = 0.0;
	double speed = AntWorld.NOMRAL_SPEED;

	private int health;
	
	public Ant(AntWorld p, AntBrain brain,  AntSensor sensor, Location l) {
		this.pa = p;
		this.brain = brain;
		this.sensor = sensor;
		this.location = l;
		this.oldLocation = l;
		health = MAX_HEALTH;
	}

	public void update() {
		double newHeading = brain.action(location,heading,sensor.inputs);
		this.heading = newHeading;
	}

	public void move() {
		double r = this.speed;
		double t = this.heading;
		double dx = Compass.dxForThetaR(t, r);
		double dy = Compass.dyForThetaR(t, r);
		oldLocation = location;
		location = new Location(location.x + dx, location.y + dy);
		pa.conform(this);
		health--;
	}
	
	public boolean canMove(Location here, Location target) {
		if (distanceToTarget(here,target) > 5.0) return true; 
		return false;
	}

	private double distanceToTarget(Location here, Location target) {
		double dx = target.x - here.x;
		double dy = target.y - here.y;
		return Math.sqrt(dx*dx + dy*dy);
	}

	public int getHealth(){
		return health;
	}

	public void feed(List<Food> meals) {
		double x1 = oldLocation.x;
		double y1 = oldLocation.y;
		double x2 = location.x;
		double y2 = location.y;
		List<Food> eaten = new ArrayList<Food>();
		for (Food meal: meals) {
			double d = Util.shortestDistance(x1, y1, x2, y2, meal.x, meal.y);
			if (d < FOOD_GRASPING_RANGE) {
				if (health < MAX_HEALTH) {
					eaten.add(meal);
					health += FOOD_HEALTH;
				}
			}
		}
		meals.removeAll(eaten);
	}

	public void sense(List<Food> meals) {
		// TODO Auto-generated method stub
	}

	public void jitter() {
		double x = location.x;
		double y = location.y;
		Location target = Util.randomLocation();
		double tx = target.x;
		double ty = target.y;
		heading = Compass.headingForDelta(tx - x , ty - y);
	}

}

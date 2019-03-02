package org.weymouth.ants.nest.core;

import java.util.List;
import java.util.ArrayList;

public class Ant {
	
	private static final int MAX_HEALTH = 100;
	private static final double FOOD_GRASPING_RANGE = 10.0;
	private static final double FOOD_HEALTH = 10.0;
	final static int CARRY_MAX = 10;

	private static int idCount = 0;
	private List<Food> backPack = new ArrayList<Food>();

	private AntBrain brain;
	
	private AntSensor sensor;
	
	private final int id;
	
	public Location location = new Location();
	Location oldLocation = new Location();
	public double heading = 0.0;
	double speed = 1.0;

	private int health;
	
	public Ant(AntBrain brain, Location l) {
		this.id = idCount++;
		this.brain = brain;
		this.sensor = new AntSensor(this);
		this.location = l;
		this.oldLocation = l;
		health = MAX_HEALTH;
	}

	public void update() {
		brain.action(location, heading, sensor.getSensoryInput());
		heading = brain.getHeading();
		speed = brain.getSpeed();
	}

	public AntBrain getBrain(){
		return brain;
	}

	public void move() {
		double r = this.speed;
		double t = this.heading;
		double dx = Compass.dxForThetaR(t, r);
		double dy = Compass.dyForThetaR(t, r);
		oldLocation = location;
		location = new Location(location.x + dx, location.y + dy);
		conform();
		health--;
	}
	
	public double getHealth(){
		double ret = 1.0/(double)MAX_HEALTH;
		ret = ret * (double) health;
		if (ret < 0) ret = 0.0;
		return ret;
	}
	
	public double getCarrying() {
		return backPack.size()/CARRY_MAX;
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
				if ((health < MAX_HEALTH) && inNest()) {
					eaten.add(meal);
					health += FOOD_HEALTH;
					if (health > MAX_HEALTH)
						health = MAX_HEALTH;
				} else if ((backPack.size() < CARRY_MAX) && !inNest()) {
					eaten.add(meal);
					backPack.add(meal);
				}
			}
		}
		meals.removeAll(eaten);
	}

	public void sense(List<Food> meals) {
		sensor.look(meals);
	}
	
	public double distanceToNestRatio() {
		return sensor.nestDistance();
	}

	public String toString() {
		String format = "Inputs(%d): %s; heading = %f , speed = %f";
		return String.format(format, id, sensor.toString(), heading, speed);
	}

	private void conform() {
		double x = location.x;
		double y = location.y;
		double tx = (double) AntWorld.WIDTH/2;
		double ty = (double) AntWorld.HEIGHT/2;
		if ( (x < 0) || (x > AntWorld.WIDTH) || (y < 0) || (y > AntWorld.HEIGHT) ) {
			heading = Compass.headingForDelta(tx - x , ty - y);
		}
	}

	public List<Food> getBackPack() {
		return backPack;
	}

	public boolean inNest() {
		return 0.0 == sensor.nestDistance();
	}

	public Food dropOneCarry() {
		if (backPack.isEmpty()) return null;
		backPack.remove(backPack.size()-1);
		return new Food(Util.randomLocatonWithinNest());
	}

	// these methods are not being used any more - keep them for a while for documentation
//	private boolean canMove(Location here, Location target) {
//		if (distanceToTarget(here,target) > 5.0) return true; 
//		return false;
//	}
//
//	private double distanceToTarget(Location here, Location target) {
//		double dx = target.x - here.x;
//		double dy = target.y - here.y;
//		return Math.sqrt(dx*dx + dy*dy);
//	}
//
//	public void jitter() {
//		double x = location.x;
//		double y = location.y;
//		Location target = Util.randomLocation();
//		double tx = target.x;
//		double ty = target.y;
//		heading = Compass.headingForDelta(tx - x , ty - y);
//		brain.scramble();
//	}

}

package org.weymouth.ants.nest.core;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;

import processing.core.PApplet;

public class RunHeadingExperiment {
	
	int baseSpeed = 10;
	
	public static void main(String[] args) {
		AntWorldViewController controller = AntWorldViewController.getController();
		PApplet.main(AntWorldView.class);
		controller.initialize();
		
		(new RunHeadingExperiment()).exec(controller);
		
	}

	private void exec(AntWorldViewController controller) {
		Random rng = new MersenneTwisterRNG();
		Network net = new Network(rng, AntWorld.BRAIN_LAYER_WIDTHS);
		play(net, controller.getView());
		controller.close();
	}

	private void play(Network net, AntWorldView antNestWorldView) {
		AntBrain antBrain = new AntBrain(net);
		int ants = 1;
		int meals = 0;
		int rounds = 1;
		AntWorld antNestWorld = new AntWorld(antBrain, ants, meals, rounds);
		Ant theAnt = antNestWorld.g.getAnts().get(0);
		for (int i = 0; i < 50; i++) {
			initAnt(theAnt);
			while (seekNest(theAnt)){
				List<Ant> theAnts = antNestWorld.cloneAnts();
				List<Food> theMeals = antNestWorld.cloneMeals();
				antNestWorldView.update(theAnts, theMeals);
			}			
		}
	}

	private void initAnt(Ant ant) {
		Location location = Util.randomInteriorPoint();
		Location lookingAt = Util.randomInteriorPoint();
		double heading = Util.headingTowards(lookingAt.x, lookingAt.y);
		ant.heading = heading;
		ant.location = location;
		ant.speed = baseSpeed;
		// System.out.println("Starting - location: " + location + ", heading = " + heading);
	}

	private boolean seekNest(Ant ant) {
		AntSensor sensor = new AntSensor(ant);
		double distanceOutput = sensor.nestDistance();
		double headingOutput = sensor.nestHeading(distanceOutput);
		double direction = headingOutput - 0.5;
		double heading = Compass.rewrap(ant.heading + (20.0 * -direction));
		double speed = baseSpeed;
		if (distanceOutput < 0.0001) {
			speed = Math.max(0.0, ant.speed - 2.0);
		}
		ant.heading = heading;
		ant.speed = speed;
		// System.out.println("Move: heading = " + heading +  ", " + speed);
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ant.move();
		if (speed == 0.0) {
			return false;			
		}
		return true;
	}
}

package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

public class AntWorld {
	
	public static double SENSING_RADIUS = 20.0;
	public static int NUMBER_OF_BRAINS = 5;
	public static int[] BRAIN_LAYER_WIDTHS = {6,8,7,2};
	
	static int HEIGHT = 800;
	static int WIDTH = 800;
	static int NUMBER_OF_ANTS = 10;
	static int NUMBER_OF_MEALS = 300;
	
	private Generation g = null;
	private boolean running;
		
	public void setBrain(AntBrain b) {
		g = new Generation(this, b);
		running = true;
	}

	public boolean update() {
		if (g == null) return false;
		if (g.oneStep()) {
			running = true;
			return true;
		}
		running = false;
		return false;
	}
	
	public boolean isRunning() {
		if (g == null) return false;
		return running;
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
		if (isRunning()){
			return g.getMeals();
		}
		return new ArrayList<Food>();
	}

	public List<Ant> getAnts() {
		if (isRunning()){
			return g.getAnts();
		}
		return new ArrayList<Ant>();
	}

}

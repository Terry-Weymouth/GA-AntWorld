package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

public class AntWorld {
	
	public static double SENSING_RADIUS = 40.0;
	public static int NUMBER_OF_BRAINS = 40;
	public static int[] BRAIN_LAYER_WIDTHS = {6,8,7,2};
	
	public static boolean graphics = true;
	
	static int HEIGHT = 400;
	static int WIDTH = 400;
	static int NUMBER_OF_ANTS = 1;
	static int NUMBER_OF_MEALS = 50;
	
	private Generation g = null;
	private boolean running;
	
	private List<WorldChangeListener> listeners = new ArrayList<WorldChangeListener>();
		
	public Generation setBrain(AntBrain b) {
		g = new Generation(this, b);
		running = true;
		notifyListerersOfNewGeneration();
		return g;
	}

	public boolean update() {
		if (g == null) return false;
		if (g.oneStep()) {
			notifyListenersOfAntChanges();
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
		double hx = Compass.dxForThetaR(ant.heading, 1.0);
		double hy = Compass.dyForThetaR(ant.heading, 1.0);
		if ( (x < 0) || (x > WIDTH) ) {
			hx = - hx;
		}
		if ( (y < 0) || (y > HEIGHT) ) {
			hy = - hy;
		}
		ant.heading = Compass.headingForDelta(hx , hy);
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
	
	public void registerChangeListener(WorldChangeListener l){
		listeners.add(l);
	}

	private void notifyListerersOfNewGeneration() {
		for (WorldChangeListener l: listeners) {
			l.generationChanged();
		}
	}
	
	private void notifyListenersOfAntChanges() {
		for (WorldChangeListener l: listeners) {
			l.antsChanged();
		}
	}

	public int getScore() {
		if (g == null) return -1;
		return g.score();
	}

}

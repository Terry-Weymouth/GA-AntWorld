package org.weymouth.ants.nest.core;

import java.util.ArrayList;
import java.util.List;

public class AntWorld {
	
	public static final double SENSING_RADIUS = 120.0;
	public static final int NUMBER_OF_ROUNDS = 3;
	
	public static final int[] BRAIN_LAYER_WIDTHS = {11,15,10,2};
	public static final int MAX_LAYER_WIDTH = 15;
	
	public static final String WORLD_TYPE = "Single Ant; Nest and Foraging";
	
	static final int NUMBER_OF_ANTS = 3;
	static final int NUMBER_OF_MEALS = 1000;
	
	static final int HEIGHT = 800;
	static final int WIDTH = 800;
	
	static final int NEST_RADIUS = 100; 

	private final Generation g;
	private int currentScore = 0;
	
	public AntWorld(AntBrain antBrain) {
		g = new Generation(antBrain);
	}

	public boolean update() {
		if (g.oneStep()) {
			currentScore = g.getAverageScore();
			return true;
		}
		currentScore = g.getAverageScore();
		return false;
	}

	public int getScore() {
		return currentScore;
	}

	public List<Ant> cloneAnts() {
		ArrayList<Ant> ret = new ArrayList<Ant>();
		for (Ant ant: g.getAnts()) {
			ret.add(ant);
		}
		return ret;
	}
	
	public List<Food> cloneMeals() {
		ArrayList<Food> ret = new ArrayList<Food>();
		for (Food meal: g.getMeals()) {
			ret.add(meal);
		}
		return ret;
	}
	
}

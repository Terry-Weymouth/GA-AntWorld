package org.weymouth.ants.nest.nest;

import org.weymouth.ants.nest.core.AntWorld;

public class AntNestWorld extends AntWorld {
	
	static final int NUMBER_OF_ANTS = 3;
	static final int NUMBER_OF_MEALS = 1000;
	
	static final int HEIGHT = 800;
	static final int WIDTH = 800;
	
	static final int NEST_RADIUS = 100; 

	private final NestGeneration g;
	private int currentScore = 0;

	public AntNestWorld(NestAntBrain antBrain) {
		super(antBrain);
		g = new NestGeneration(antBrain);
	}

	@Override
	public boolean update() {
		if (g.oneStep()) {
			currentScore = g.getAverageScore();
			return true;
		}
		currentScore = g.getAverageScore();
		return false;
	}

}

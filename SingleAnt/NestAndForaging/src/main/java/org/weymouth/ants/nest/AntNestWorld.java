package org.weymouth.ants.nest;

import org.weymouth.ants.core.AntBrain;
import org.weymouth.ants.core.AntWorld;

public class AntNestWorld extends AntWorld {
	
	static final int NUMBER_OF_ANTS = 1;
	static final int NUMBER_OF_MEALS = 1000;
	
	static final int HEIGHT = 800;
	static final int WIDTH = 800;

	public AntNestWorld(AntBrain antBrain) {
		super(antBrain);
	}

}

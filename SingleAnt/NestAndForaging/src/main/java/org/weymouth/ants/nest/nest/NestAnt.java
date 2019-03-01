package org.weymouth.ants.nest;

import org.weymouth.ants.core.Ant;
import org.weymouth.ants.core.AntBrain;
import org.weymouth.ants.core.Location;

public class NestAnt extends Ant {

	public NestAnt(AntBrain brain, Location l) {
		super(brain, l);
	}

	public double getCarrying() {
		return this.getHealth();
	}

}

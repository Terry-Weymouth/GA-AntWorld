package org.weymouth.ants.nest.nest;

import org.weymouth.ants.nest.core.AntBrain;
import org.weymouth.ants.nest.core.Location;
import org.weymouth.ants.nest.core.Ant;

public class NestAnt extends Ant {

	public NestAnt(AntBrain brain, Location l) {
		super(brain, l);
	}

	public double getCarrying() {
		return this.getHealth();
	}

}

package org.weymouth.ants.nest.watchmaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;
import org.weymouth.ants.nest.core.Network;

public class NetworkCrossover extends AbstractCrossover<Network> {

	public NetworkCrossover(int crossoverPoints) {
		super(crossoverPoints);
	}

	@Override
	protected List<Network> mate(Network parent1, Network parent2, int numberOfCrossoverPoints, Random rng) {
		List<Network> ret = new ArrayList<Network>();
		ret.add(parent1.cross(parent2, rng));
		ret.add(parent2.cross(parent1, rng));
		return ret;
	}

}

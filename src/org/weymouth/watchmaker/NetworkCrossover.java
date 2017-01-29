package org.weymouth.watchmaker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.operators.AbstractCrossover;
import org.weymouth.ants.Network;

public class NetworkCrossover extends AbstractCrossover<Netowrk> {

	protected NetworkCrossover(int crossoverPoints) {
		super(crossoverPoints);
	}

	@Override
	protected List<Netowrk> mate(Netowrk parent1, Netowrk parent2, int numberOfCrossoverPoints, Random rng) {
		List<Network> ret = new ArrayList<Network>();
		ret.add(parent1.cross(parent2,rng));
		ret.add(parent2.cross(parent1,rng));
		return ret;
	}

}

package org.weymouth.watchmaker;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.Network;

public class NetworkFitnessEvaluator implements FitnessEvaluator<Network> {

	@Override
	public double getFitness(Network fn, List<? extends Network> list) {
		return 0.0;
	}

	@Override
	public boolean isNatural() {
		return false;
	}

}

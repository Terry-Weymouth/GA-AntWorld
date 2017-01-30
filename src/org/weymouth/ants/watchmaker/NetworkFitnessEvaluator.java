package org.weymouth.ants.watchmaker;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.AntWorldInterface;
import org.weymouth.ants.Network;

public class NetworkFitnessEvaluator implements FitnessEvaluator<Network> {

	@Override
	public double getFitness(Network net, List<? extends Network> list) {
		int score = AntWorldInterface.getInterface().evaluate(net);
		return (double)score;
	}

	@Override
	public boolean isNatural() {
		return false;
	}

}

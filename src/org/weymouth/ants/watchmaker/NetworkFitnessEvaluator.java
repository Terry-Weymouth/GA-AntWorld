package org.weymouth.ants.watchmaker;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.AntBrain;
import org.weymouth.ants.AntWorld;
import org.weymouth.ants.Network;

public class NetworkFitnessEvaluator implements FitnessEvaluator<Network> {

	@Override
	public double getFitness(Network net, List<? extends Network> list) {
		int score = evaluate(net);
		return (double)score;
	}

	@Override
	public boolean isNatural() {
		return true;
	}

	
	private static int NUMBER_OF_PASSES = 20;
	
	public int evaluate(Network net) {
		AntWorld world = new AntWorld();
		AntBrain antBrain = new AntBrain(net);
		int totalScore = 0;
		for (int i = 0; i < NUMBER_OF_PASSES; i++){
			world.setBrain(antBrain);
			while (world.update()){}
			totalScore += world.getScore();
		}
		return totalScore/NUMBER_OF_PASSES;
	}

}

package org.weymouth.ants.watchmaker;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.core.AntBrain;
import org.weymouth.ants.core.AntWorld;
import org.weymouth.ants.core.Network;
import org.weymouth.ants.core.AntWorldController;

public class NetworkFitnessEvaluator implements FitnessEvaluator<Network> {
	
	private final AntWorldController worldController;

	public NetworkFitnessEvaluator() {
		worldController = new AntWorldController();
	}
	
	@Override
	public double getFitness(Network net, List<? extends Network> list) {
		int score = evaluate(net);
		System.out.println("Called NetworkFitnessEvaluator; returning score = " + score); 
		return (double)score;
	}

	@Override
	public boolean isNatural() {
		return true;
	}

	private static int NUMBER_OF_PASSES = 20;
	
	public int evaluate(Network net) {
		AntBrain antBrain = new AntBrain(net);
		AntWorld antWorld = worldController.getSimulator();
		int totalScore = 0;
		antWorld.setBrain(antBrain);
		for (int i = 0; i < NUMBER_OF_PASSES; i++){
			antWorld.startSimulation();
			while (antWorld.update()){}
			totalScore += antWorld.getScore();
		}
		return totalScore/NUMBER_OF_PASSES;
	}

}

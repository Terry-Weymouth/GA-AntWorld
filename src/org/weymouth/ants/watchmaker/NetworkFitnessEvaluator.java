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
		worldController = AntWorldController.getController();
		worldController.initialize();
	}
	
	@Override
	public double getFitness(Network net, List<? extends Network> list) {
		int score = evaluate(net);
		System.out.println("Called NetworkFitnessEvaluator; returning score = " + score);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignore) {
		}
		return (double)score;
	}

	@Override
	public boolean isNatural() {
		return true;
	}

	public int evaluate(Network net) {
		AntBrain antBrain = new AntBrain(net);
		AntWorld antWorld = worldController.getSimulator();
		antWorld.setBrain(antBrain);
		antWorld.startSimulation();
		while (antWorld.update()){}
		return antWorld.getScore();
	}

}

package org.weymouth.ants.watchmaker;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.core.AntBrain;
import org.weymouth.ants.core.AntWorld;
import org.weymouth.ants.core.AntWorldView;
import org.weymouth.ants.core.Network;
import org.weymouth.ants.core.AntWorldViewController;

public class NetworkFitnessEvaluator implements FitnessEvaluator<Network> {
	
	private final AntWorldViewController worldController;

	public NetworkFitnessEvaluator() {
		worldController = AntWorldViewController.getController();
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
		AntWorldView antWorldView = worldController.getView();
		AntWorld antWorld = new AntWorld(antBrain);
		while (antWorld.update()){
			antWorldView.update(antWorld.cloneAnts(), antWorld.cloneMeals());
		}
		return antWorld.getScore();
	}

}

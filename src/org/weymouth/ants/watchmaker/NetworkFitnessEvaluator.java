package org.weymouth.ants.watchmaker;

import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.core.AntBrain;
import org.weymouth.ants.core.AntWorld;
import org.weymouth.ants.core.AntWorldView;
import org.weymouth.ants.core.Network;
import org.weymouth.ants.main.WatchmakerMain;
import org.weymouth.ants.core.AntWorldViewController;

public class NetworkFitnessEvaluator implements FitnessEvaluator<Network> {
	
	private final AntWorldViewController worldController;
	
	private static int count = 0;

	public NetworkFitnessEvaluator() {
		if (WatchmakerMain.HEADLESS) {
			worldController = null;
		} else {
			worldController = AntWorldViewController.getController();
			worldController.initialize();			
		}
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
		count += 1;
		System.out.println("Evaluate-called count = " + count);
		AntBrain antBrain = new AntBrain(net);
		AntWorldView antWorldView = null;
		if (!WatchmakerMain.HEADLESS) {
			antWorldView = worldController.getView();
		}
		AntWorld antWorld = new AntWorld(antBrain);
		while (antWorld.update()){
			if (!WatchmakerMain.HEADLESS) {
				antWorldView.update(antWorld.cloneAnts(), antWorld.cloneMeals());
			}
		}
		return antWorld.getScore();
	}

}

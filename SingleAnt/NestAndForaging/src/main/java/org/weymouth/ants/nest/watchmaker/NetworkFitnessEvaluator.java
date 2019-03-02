package org.weymouth.ants.nest.watchmaker;

import java.util.List;
import java.util.HashMap;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.nest.core.AntBrain;
import org.weymouth.ants.nest.core.AntWorld;
import org.weymouth.ants.nest.core.AntWorldView;
import org.weymouth.ants.nest.core.Network;
import org.weymouth.ants.nest.main.WatchmakerMain;
import org.weymouth.ants.nest.core.AntWorldViewController;

public class NetworkFitnessEvaluator implements FitnessEvaluator<Network> {
	
	private final AntWorldViewController worldController;
	
	private final HashMap<Network, Double> cache = new HashMap<Network, Double>();
	
	private final boolean headless;
	
	private static int count = 0;

	private boolean useCache = false;
	
	public NetworkFitnessEvaluator(boolean flag) {
		headless = flag;
		if (headless) {
			worldController = null;
		} else {
			worldController = AntWorldViewController.getController();
			worldController.initialize();			
		}
	}
	
	@Override
	public double getFitness(Network net, List<? extends Network> list) {
		double score = evaluate(net);
		System.out.println("Called NetworkFitnessEvaluator; returning score = " + score);
		if (!headless) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ignore) {
			}
		}
		return score;
	}

	@Override
	public boolean isNatural() {
		return true;
	}

	public double evaluate(Network net) {
		if (useCache) {
			Double scoreHolder = cache.get(net);
			if (scoreHolder != null) {
				System.out.println("Evaluate-called: scored by lookup");
				return scoreHolder.doubleValue();
			}
		}
		count += 1;
		System.out.println("Evaluate-called count = " + count);
		AntBrain antBrain = new AntBrain(net);
		AntWorldView antWorldView = null;
		if (!headless) {
			antWorldView = worldController.getView();
		}
		AntWorld antWorld = new AntWorld(antBrain, WatchmakerMain.NUMBER_OF_ANTS, 
				WatchmakerMain.NUMBER_OF_MEALS, WatchmakerMain.NUMBER_OF_ROUNDS);
		while (antWorld.update()){
			if (!headless) {
				antWorldView.update(antWorld.cloneAnts(), antWorld.cloneMeals());
			}
		}
		double score = (double)antWorld.getScore()/10000.0;
		cache.put(net, new Double(score));
		return score;
	}

	
	public void setUseCache(boolean flag) {
		useCache = flag;
	}
}

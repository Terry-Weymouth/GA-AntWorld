package org.weymouth.ants.watchmaker;

import java.util.HashMap;
import java.util.List;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.weymouth.ants.core.Network;

public class NetworkFitnessCacheEvaluator implements FitnessEvaluator<Network> {
	
	private static NetworkFitnessCacheEvaluator theEvaluator = new NetworkFitnessCacheEvaluator();
	private NetworkFitnessCacheEvaluator() {}
	
	public static NetworkFitnessCacheEvaluator getEvaluator() {
		return theEvaluator;
	}
	
	private HashMap<Network,Double> cache = new HashMap<Network,Double>(); 
	
	public void addToCache(Network key, double score) {
		Double value = new Double(score);
		cache.put(key, value);
	}
	
	public double getFromCache(Network key) {
		Double value = cache.get(key);
		if (value == null) return -1.0;
		return value.doubleValue();
	}

	@Override
	public double getFitness(Network network, List<? extends Network> arg1) {
		return getFromCache(network);
	}

	@Override
	public boolean isNatural() {
		return true;
	}
	
	
}

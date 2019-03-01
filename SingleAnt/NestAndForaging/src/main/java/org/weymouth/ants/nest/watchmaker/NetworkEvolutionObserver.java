package org.weymouth.ants.nest.watchmaker;

import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;
import org.weymouth.ants.nest.core.Network;

public class NetworkEvolutionObserver extends EvolutionMonitor<Network> {

	private final NetworkFitnessEvaluator fitnessEvaluator;
	
	public NetworkEvolutionObserver(NetworkFitnessEvaluator fe) {
		super();
		this.fitnessEvaluator = fe;
	}

	private double lastFitness = 0.0;
	
	public void populationUpdate(PopulationData<? extends Network> data)
    {
		super.populationUpdate(data);
    	Network fn = data.getBestCandidate();
    	fitnessEvaluator.setUseCache(true);
    	double fittness = fitnessEvaluator.getFitness(fn, null);
    	fitnessEvaluator.setUseCache(false);
    	if (fittness != lastFitness) {
    		lastFitness = fittness;
	        System.out.printf("Generation %d: (%f) %s\n",
	                          data.getGenerationNumber(),
	                          fittness,
	                          fn);
    	}
    }

}

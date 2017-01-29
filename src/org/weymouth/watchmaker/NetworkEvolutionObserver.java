package org.weymouth.watchmaker;

import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;
import org.weymouth.ants.Network;

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
    	double error = fitnessEvaluator.getFitness(fn, null);
    	if (error != lastFitness) {
    		lastFitness = error;
	        System.out.printf("Generation %d: (%f) %s\n",
	                          data.getGenerationNumber(),
	                          error,
	                          fn);
    	}
    }

}

package org.weymouth.ants.watchmaker;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import org.weymouth.ants.core.Network;

public class SimpleTextObserver implements EvolutionObserver<Network> {

	@Override
	public void populationUpdate(PopulationData<? extends Network> data) {
		double topScore = data.getBestCandidateFitness();
		int generation = data.getGenerationNumber();
		Network network = data.getBestCandidate();
		System.out.println("Generation " + generation);
		System.out.println("  topScore = " + topScore);
		System.out.println("  elapsed time = " + data.getElapsedTime());
		System.out.println("  " + data.toString());
		System.out.println("  " + network.toString());
	}

}

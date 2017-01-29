package org.weymouth.watchmaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.CandidateFactory;

public class NetworkFactory implements CandidateFactory<Network> {
	
	@Override
	public List<Network> generateInitialPopulation(int populationSize, Random rng) {
		return makeInitialPopulation(populationSize,rng);
	}

	@Override
	public List<Network> generateInitialPopulation(int populationSize, Collection<Network> seedCandidates, Random rng) {
		return makeInitialPopulation(populationSize,rng);
	}

	@Override
	public Network generateRandomCandidate(Random rng) {
		return new Network(rng);
	}

	private List<Network> makeInitialPopulation(int populationSize, Random rng) {
		List<Network> ret = new ArrayList<Network>();
		for (int i = 0; i < populationSize; i++) {
			ret.add(new Network(rng));
		}
		return ret;
	}
}

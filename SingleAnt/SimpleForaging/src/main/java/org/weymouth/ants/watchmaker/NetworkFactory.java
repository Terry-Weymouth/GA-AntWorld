package org.weymouth.ants.watchmaker;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;
import org.weymouth.ants.core.AntWorld;
import org.weymouth.ants.core.Network;

public class NetworkFactory extends AbstractCandidateFactory<Network> {
	
	private final Collection<Network> seedCandidates;

	public NetworkFactory(Collection<Network> seedCandidates) {
		super();
		this.seedCandidates = seedCandidates;
	}

	@Override
	public List<Network> generateInitialPopulation(int populationSize, Collection<Network> seedCandidates, Random rng) {
		seedCandidates = this.seedCandidates;
		return super.generateInitialPopulation(populationSize, seedCandidates, rng);
	}

	@Override
	public Network generateRandomCandidate(Random rng) {
		return new Network(rng,AntWorld.BRAIN_LAYER_WIDTHS);
	}

}

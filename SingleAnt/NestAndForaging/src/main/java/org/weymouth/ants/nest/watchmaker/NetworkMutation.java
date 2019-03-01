package org.weymouth.ants.nest.watchmaker;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.weymouth.ants.nest.core.Network;

public class NetworkMutation implements EvolutionaryOperator<Network> {
	
	private final Probability p;

	public NetworkMutation(Probability probability) {
		p = probability;
	}

	@Override
	public List<Network> apply(List<Network> list, Random rng) {
		for (Network net: list) {
			if (p.nextEvent(rng)){
				net.mutate(rng);
			}			
		}
		return list;
	}

}

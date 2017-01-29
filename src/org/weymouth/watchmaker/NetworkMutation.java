package org.weymouth.watchmaker;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

public class NetworkMutation implements EvolutionaryOperator<Network> {
	
	private final Probability p;

	public NetworkMutation(Probability probability) {
		p = probability;
	}

	@Override
	public List<Network> apply(List<Network> list, Random rng) {
		for (Network f: list) {
			if (p.nextEvent(rng)){
				f.mutate(rng);
			}			
		}
		return list;
	}

}

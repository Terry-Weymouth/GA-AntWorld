package org.weymouth.ants.watchmaker;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.weymouth.ants.core.Network;

public class NetworkScramble implements EvolutionaryOperator<Network> {

	private final Probability p;
	
	public NetworkScramble(Probability probability) {
		p = probability;
	}

	@Override
	public List<Network> apply(List<Network> list, Random rng) {
		for (Network f: list) {
			if (p.nextEvent(rng)){
				f.scramble(rng);
			}			
		}
		return list;
	}

}

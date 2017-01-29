package org.weymouth.watchmaker;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;

public class NetworkReplace implements EvolutionaryOperator<Network> {

	private final Probability p;

	public NetworkReplace(Probability probability) {
		p = probability;
	}

	@Override
	public List<Network> apply(List<Network> list, Random rng) {
		for (int i = 0; i < list.size(); i++) {
			if (p.nextEvent(rng)){
				Network f = new Network(rng);
				list.add(i,f);
				list.remove(i+1);
			}						
		}
		return list;
	}

}

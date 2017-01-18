package org.weymouth.ants;

import java.util.ArrayList;
import java.util.List;

public class AntBrain {
	
	private static double TURN_MAX = 45.0;
	
	private final Network network;
	
	public AntBrain(Network n){
		network = n;
	}
	
	public double action(Location here, double heading, double[] inputs) {
		network.setInputs(inputs);
		network.propogate();
		double output[] = network.output();
		double direction = output[0] - 0.5;
		return Compass.rewrap(heading + (TURN_MAX * direction));
	}

	public void scramble() {
		network.setNetToRandom();
	}
	
	public Network getNetwork(){
		return network;
	}

	public static List<AntBrain> starterList() {
		List<AntBrain> ret = new ArrayList<AntBrain>();
		for (int i = 0 ; i < AntWorld.NUMBER_OF_BRAINS; i++) {
			AntBrain b = new AntBrain(new Network(AntWorld.BRAIN_LAYER_WIDTHS));
			b.scramble();
			ret.add(b);
		}
		return ret;
	}

}

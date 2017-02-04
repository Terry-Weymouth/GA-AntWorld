package org.weymouth.ants.process3;

import java.util.ArrayList;
import java.util.List;

public class AntBrain {
	
	private static double TURN_MAX = 45.0;
	private static double MAXIMUM_SPEED = 10.0;
	
	private final Network network;
	
	private double speed;
	private double heading;
	
	public AntBrain(Network n){
		network = n;
	}
	
	public void action(Location here, double oldHeading, double[] inputs) {
		network.setInputs(inputs);
		network.propogate();
		double output[] = network.output();
		double direction = output[0] - 0.5;
		heading = Compass.rewrap(oldHeading + (TURN_MAX * direction));
		speed = output[1] * MAXIMUM_SPEED;
	}

	public double getHeading() {
		return heading;
	}

	public double getSpeed() {
		return speed;
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

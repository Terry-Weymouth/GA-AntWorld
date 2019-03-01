package org.weymouth.ants.nest.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		inputs[6] = netValueForTurnDirection();
		inputs[7] = netValueForSpeed();
		network.setInputs(inputs);
		network.propogate();
		double output[] = network.output();
		double direction = output[0] - 0.5;
		heading = Compass.rewrap(oldHeading + (TURN_MAX * direction));
		speed = output[1] * MAXIMUM_SPEED;
	}
	
	public double[] getOutputs() {
		return network.output();
	}

	public double getHeading() {
		return heading;
	}

	public double getSpeed() {
		return speed;
	}
	
	public double netValueForTurnDirection() {
		return network.output()[0];
	}
	
	public double netValueForSpeed() {
		return network.output()[1];
	}

	public void scramble() {
		network.scramble(new Random());
	}
	
	public Network getNetwork(){
		return network;
	}

	public static List<AntBrain> starterList(int numberOfBrains) {
		List<AntBrain> ret = new ArrayList<AntBrain>();
		for (int i = 0 ; i < numberOfBrains; i++) {
			AntBrain b = new AntBrain(new Network(new Random(), AntWorld.BRAIN_LAYER_WIDTHS));
			b.scramble();
			ret.add(b);
		}
		return ret;
	}

}

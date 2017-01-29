package org.weymouth.ants;

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

	public Network getNetwork(){
		return network;
	}

}

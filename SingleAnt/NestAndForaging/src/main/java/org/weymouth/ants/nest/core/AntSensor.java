package org.weymouth.ants.nest.core;

import java.util.List;
import java.util.stream.Collectors;

public class AntSensor {

	private final static double radius = AntWorld.SENSING_RADIUS;
	private final static Location nestLocation = new Location(AntWorld.WIDTH/2, AntWorld.HEIGHT/2);
	private final static Location minLocation = new Location(0, 0);
	private final static double maxNestDistance = Util.distance(minLocation, nestLocation) - radius;

	private double[] inputs = new double[11];
	private double senseStrength = 0.0;
	private int senseIndex = -1;
	private final Ant body;

	
	public AntSensor(Ant body) {
		this.body = body;
	}

	public List<Food> look(List<Food> meals) {
		List<Food> selected = meals.stream().filter(p -> Util.insideBox(body.location,radius,p)).collect(Collectors.toList());
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] = 0.0;
		}
		boolean valueSet = false;
		for (Food f: selected) {
			setSensation(f);
			if (senseIndex > -1) {
				inputs[senseIndex] += senseStrength;
				valueSet = true;
			}
		}
		// normalize values 
		if (valueSet) {
			double max = 0.0;
			for (int i = 0; i < inputs.length; i++) {
				if (inputs[i] > max) max = inputs[i];
			}
			if (max > 1.0) {
				for (int i = 0; i < inputs.length; i++) {
					inputs[i] = inputs[i]/max;
				}
			}
		}
		inputs[5] = nestDistance();
		inputs[6] = nestHeading(inputs[5]);
		inputs[7] = getCarrying();
		inputs[8] = body.getHealth();
		return selected;
	}

	private void setSensation(Food f) {
		double dx = f.x - body.location.x;
		double dy = f.y - body.location.y;
		double theta = Compass.headingForDelta(dx, dy);
		double r = Math.sqrt(dx*dx + dy*dy);
		double dHeading = Compass.rewrap(theta - body.heading);
//		System.out.printf("look-distance = %4.4f, look-direction = %4.4f\n",r,dHeading);
		if ((r > radius) || (dHeading > 45.0) || (dHeading < -45.0)) {
//			System.out.println("Rejected");
			senseStrength = 0.0;
			senseIndex = -1;
		} else {
			senseStrength = 1.0 - (r/radius);
			senseIndex = (int) ( (dHeading + 45.0)/18.0 );
//			System.out.println("Sense(" + senseIndex + "): " + senseStrength); 
		}
	}
	
	double nestDistance() {
		double rawDistance = Util.distance(body.location, nestLocation);
		if (rawDistance < radius) {
			return 0.0;
		}
		rawDistance = rawDistance - radius;
		return rawDistance/maxNestDistance;
	}

	private double nestHeading(double distance) {
		if (distance == 0.0) {
			return 0.5; // center heading
		}
		double angle = Compass.headingForDelta(nestLocation.x - body.location.x, nestLocation.y - body.location.y);
		double deltaAngle = Compass.rewrap(angle - body.heading);
		return (deltaAngle + 180.0)/360.0;
	}

	private double getCarrying() {
		return ((double)body.getBackPack().size())/((double)Ant.CARRY_MAX);
	}

	public double[] getSensoryInput() {
		// NOTE: inputs[9] and inputs[10] are set to currentSpeed and currentHeading, resp., by brain action 
		return inputs;
	}
	
	public String toString() {
		String ret = null;
		String format = "%2.4f";
		for (int i = 0; i < inputs.length; i++ ){
			String part = String.format(format, inputs[i]);
			if (ret == null) ret = part;
			else ret += ", " + part;
}
		return ret;
	}

}

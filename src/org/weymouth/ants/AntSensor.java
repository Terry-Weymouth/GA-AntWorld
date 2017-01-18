package org.weymouth.ants;

import java.util.List;
import java.util.stream.Collectors;

public class AntSensor {

	private final static double radius = AntWorld.SENSING_RADIUS;

	private double[] inputs = {1.0,2.0,3.0,4.0,5.0,6.0};
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
		for (Food f: selected) {
			setSensation(f);
			if (senseIndex > -1)
				inputs[senseIndex] += senseStrength;
		}
		double max = 0.0;
		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] > max) max = inputs[i];
		}
		if (max > 1.0) {
			for (int i = 0; i < inputs.length; i++) {
				inputs[i] = inputs[i]/max;
			}
		}
		inputs[5] = body.getHealth();
		return selected;
	}

	private void setSensation(Food f) {
		double dx = f.x - body.location.x;
		double dy = f.y - body.location.y;
		double theta = Compass.headingForDelta(dx, dy);
		double r = Math.sqrt(dx*dx * dy*dy);
		double dHeading = Compass.rewrap(theta - body.heading);
		if ((r > radius) || (dHeading > 45.0) || (dHeading < -45.0)) {
			senseStrength = 0.0;
			senseIndex = -1;
		} else {
			senseStrength = 1.0 - (r/radius);
			senseIndex = (int) ( (dHeading + 45.0)/18.0 );
		}
	}
	
	public double[] getSensoryInput() {
		return inputs;
	}

}

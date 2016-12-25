package org.weymouth.ants;

public class AntBrain {
	
	public float action(AntState status, Location target) {
		double dx = target.x - (double) status.x;
		double dy = target.y - (double) status.y;
		return (float) Compass.headingForDelta(dx,dy);
	}

}

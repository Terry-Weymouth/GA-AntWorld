package org.weymouth.ants;

public class AntBrain {
	
	public float action(Location here, double heading, double[] inputs) {
		Location target =  Util.randomLocation();
		double dx = target.x - here.x;
		double dy = target.y - here.y;
		return (float) Compass.headingForDelta(dx,dy);
	}

}

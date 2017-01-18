package org.weymouth.ants;

/*
 * The coordinate System is -
 * 		the direction of positive X is East
 * 		the direction of positive Y is South
 * 		the direction of zero degrees is East
 * 		the direction of +90 degrees is South
 * 	Angles in degrees
 */

public class Compass {
	
	public static double headingForDelta(double dx, double dy){
		double theta = Math.atan2(dy,dx);
		double angle = (theta * 180.0) / Math.PI;
		return angle;
	}
	
	public static double dxForThetaR(double theta, double r){
		double radians = (theta / 180.0) * Math.PI;
		return r * Math.cos(radians);
	}

	public static double dyForThetaR(double theta, double r){
		double radians = (theta / 180.0) * Math.PI;
		return r * Math.sin(radians);
	}

	public static double rewrap(double theta) {
		while (theta > 180.0) {
			theta -= 360.0;
		}
		while (theta < 180.0) {
			theta += 360.0;
		}
		return theta;
	}
	
}

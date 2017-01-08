package org.weymouth.ants;

public class Location {
	
	public final double x;
	public final double y;

	public Location(){
		this(0, 0);
	}
	
	public Location(int xi, int yi) {
		x = (double) xi;
		y = (double) yi;
	}

	public Location(float xf, float yf) {
		x = (double) xf;
		y = (double) yf;
	}

	public Location(double xd, double yd) {
		x = xd;
		y = yd;
	}
	
	public float getFloatX(){
		return (float)x;
	}

	public float getFloatY(){
		return (float)y;
	}
}

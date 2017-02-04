package org.weymouth.ants.process3;

public class Food extends Location{

	public Food(int xi, int yi) {
		super(xi, yi);
	}

	public Food(Location l) {
		super(l.x,l.y);
	}

	public float getXFloat() {
		return (float) x;
	}

	public float getYFloat() {
		return (float) y;
	}

}

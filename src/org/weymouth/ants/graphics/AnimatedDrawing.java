package org.weymouth.ants.graphics;

import java.awt.Dimension;
import java.awt.Graphics;

public abstract class AnimatedDrawing {
	
	protected Dimension size;

	public abstract void update();
	public abstract void draw(Graphics g);

	public void setSize(Dimension size) {
		this.size = size;
	}

	public Dimension getSize() {
		return size;
	}


}

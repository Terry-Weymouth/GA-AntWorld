package org.weymouth.ants.graphics;

import java.awt.Color;
import java.awt.Graphics;

public class AnimationDemo extends AnimatedDrawing {

	int x = 5;
	int y = 5;

	@Override
	public void update() {
		x++;
		y++;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLUE);;
		g.fillRect (0 + x, 0 + y, 50, 50);
	}

}

package org.weymouth.ants;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class AntDisplay {

	public void drawWorld(Graphics g, AntWorld antWorld) {
		g.setColor(Color.WHITE);
		Graphics2D g2d = (Graphics2D)g;
		for (Food meal: antWorld.getMeals()) {
			drawFood(g2d,meal);
		}
		for (Ant ant: antWorld.getAnts()) {
			drawAnt(g2d,ant);
		}
	}

	private void drawAnt(Graphics2D g, Ant ant) {
		drawAntBody(g, ant);
		drawAntHead(g, ant);
	}
	
	private void drawAntBody(Graphics2D g, Ant ant) {
		double bodyRadius = 10.0;
		g.fill(new Ellipse2D.Double(ant.location.x-5.0, ant.location.y-5.0, bodyRadius, bodyRadius));
	}

	private void drawAntHead(Graphics2D g, Ant ant) {
		double ang1 = ant.heading;
		double ang2 = ant.heading + 90.0;
		double ang3 = ant.heading - 90.0;
		double r1 = 15.0;
		double r2 = 5.0;
		Location l1 = new Location(ant.location.x + Compass.dxForThetaR(ang1,r1), ant.location.y + Compass.dyForThetaR(ang1,r1));
		Location l2 = new Location(ant.location.x + Compass.dxForThetaR(ang2,r2), ant.location.y + Compass.dyForThetaR(ang2,r2));
		Location l3 = new Location(ant.location.x + Compass.dxForThetaR(ang3,r2), ant.location.y + Compass.dyForThetaR(ang3,r2));
		Polygon p = new Polygon();
		p.addPoint((int)l1.x, (int)l1.y);
		p.addPoint((int)l2.x, (int)l2.y);
		p.addPoint((int)l3.x, (int)l3.y);
		g.fill(p);
	}

	private void drawFood(Graphics2D g, Food meal) {
		double x = meal.x - 5.0;
		double y = meal.y - 5.0;
		g.fill(new Rectangle2D.Double(x, y, 10.0f, 10.0f));
	}

}

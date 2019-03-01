package org.weymouth.ants.nest;

import java.util.List;

import org.weymouth.ants.core.Ant;
import org.weymouth.ants.core.AntWorldView;
import org.weymouth.ants.core.Food;

public class AntNestWorldView extends AntWorldView {
	
	static final int MARGIN = 20 + Math.max(AntNestWorld.NUMBER_OF_ANTS * 2, 11)*20;
	
	float diameter = AntNestWorld.NEST_RADIUS*2;
	float rSquared = AntNestWorld.NEST_RADIUS*AntNestWorld.NEST_RADIUS;
	float cx = AntNestWorld.WIDTH/2;
	float cy = AntNestWorld.HEIGHT/2;

	public void settings(){
		size(AntNestWorld.WIDTH + MARGIN, AntNestWorld.HEIGHT);
		AntNestWorldViewController.getController().register(this);
    }

	@Override
	public void draw() {
		super.draw();
		
		drawNest();
	}

	@Override
	public void display(Food meal) {
		float x = meal.getXFloat() - 5.0F;
		float y = meal.getYFloat() - 5.0f;
		float dx = x - cx;
		float dy = y - cy;
		float distSquared = dx*dx + dy*dy;
		boolean inside = distSquared < rSquared;
		noStroke();
		if (inside) {
			fill(255,255,0,100);
		} else {
			fill(255,100);
		}
		rect(x, y, 10.0f, 10.0f);
	}
	
	private void drawNest() {
		noFill();
		stroke(0,0,255,200);
		ellipse(cx, cy, diameter, diameter);
	}
	
	public void drawNestMargin(List<NestAnt> ants) {
		fill(255,255);
		stroke(0);
		rect(AntNestWorld.WIDTH, 0, MARGIN, AntNestWorld.HEIGHT);
		noStroke();
		fill(0,255);
		float x = 10.0f + AntNestWorld.WIDTH;
		float y = 20.0f;
		float height = 200.0f;
		float w = 19.0f;
		float maxHealth = 0;
		Ant maxAnt = null;
		for (Ant ant: ants) {
			float health = (float)ant.getHealth();
			if (health > maxHealth) {
				maxHealth = health;
				maxAnt = ant;
			}
			float offset = (1.0f - health) * height;
			float h = health * height;
			rect(x, y + offset, w, h);
			stroke(0,0,255);
			line(x, y-1.0f, x+w, y-1.0f);
			noStroke();
			x += 40.0f;
		}
		fill(255, 255 ,0, 255);
		x = 30.0f + AntNestWorld.WIDTH;
		for (NestAnt ant: ants) {
			float carrying = 0.5f;
			float offset = (1.0f - carrying) * height;
			float h = carrying * height;
			rect(x, y + offset, w, h);
			stroke(0,0,255);
			line(x, y-1.0f, x+w, y-1.0f);
			noStroke();
			x += 40.0f;
		}
		
		if (maxAnt != null) {
			double[][] netValues = maxAnt.getBrain().getNetwork().getLayerValues();
			// float maxNetWidth = maxAnt.getBrain().getNetwork().getMaxLayerWidth();
			float maxNetWidth = 10;
			float baseX = 10.0f + AntNestWorld.WIDTH;
			y = 40.0f + height;
			
			stroke(0,0,255);
			for (double[] row: netValues) {
				x = baseX + (20.0f * (maxNetWidth-row.length)/2f );
				y += 40.0f;
				for (double value: row) {
					float gray = (float)(1.0f - value) * 255;
					fill(gray, 255);
					rect(x, y, 19, 19);
					x += 20.0f;
				}				
			}
			noStroke();
			
		}
	}
}

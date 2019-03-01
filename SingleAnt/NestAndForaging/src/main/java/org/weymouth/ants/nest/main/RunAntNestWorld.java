package org.weymouth.ants.nest.main;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.weymouth.ants.nest.core.Ant;
import org.weymouth.ants.nest.core.AntBrain;
import org.weymouth.ants.nest.core.AntWorld;
import org.weymouth.ants.nest.core.AntWorldView;
import org.weymouth.ants.nest.core.AntWorldViewController;
import org.weymouth.ants.nest.core.Food;
import org.weymouth.ants.nest.core.Network;

import processing.core.PApplet;

public class RunAntNestWorld {

	public static void main(String[] args) {
		AntWorldViewController controller = AntWorldViewController.getController();
		PApplet.main(AntWorldView.class);
		controller.initialize();
		
		(new RunAntNestWorld()).exec(controller);
		
	}
	
	private void exec(AntWorldViewController controller) {
		Random rng = new MersenneTwisterRNG();
		Network net = new Network(rng, AntWorld.BRAIN_LAYER_WIDTHS);
		double score = play(net, controller.getView());
		System.out.println("Score = " + score);
		controller.close();
	}

	private double play(Network net, AntWorldView antNestWorldView) {
		AntBrain antBrain = new AntBrain(net);
		AntWorld antNestWorld = new AntWorld(antBrain);
		while (antNestWorld.update()){
			List<Ant> theAnts = antNestWorld.cloneAnts();
			List<Food> theMeals = antNestWorld.cloneMeals();
			antNestWorldView.update(theAnts, theMeals);
		}
		double score = (double)antNestWorld.getScore()/10000.0;
		return score;
	}
}

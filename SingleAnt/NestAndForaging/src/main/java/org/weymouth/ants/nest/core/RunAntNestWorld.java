package org.weymouth.ants.nest.core;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;

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
		int ants = 3;
		int meals = 1000;
		int rounds = 1;
		AntWorld antNestWorld = new AntWorld(antBrain, ants, meals, rounds);
		while (antNestWorld.update()){
			List<Ant> theAnts = antNestWorld.cloneAnts();
			List<Food> theMeals = antNestWorld.cloneMeals();
			antNestWorldView.update(theAnts, theMeals);
		}
		double score = (double)antNestWorld.getScore()/10000.0;
		return score;
	}
}

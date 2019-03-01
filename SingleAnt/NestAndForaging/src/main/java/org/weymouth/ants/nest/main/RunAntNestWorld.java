package org.weymouth.ants.nest.main;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.weymouth.ants.nest.core.Food;
import org.weymouth.ants.nest.core.Network;
import org.weymouth.ants.nest.nest.AntNestWorld;
import org.weymouth.ants.nest.nest.AntNestWorldView;
import org.weymouth.ants.nest.nest.AntNestWorldViewController;
import org.weymouth.ants.nest.nest.NestAntBrain;
import org.weymouth.ants.nest.core.Ant;

import processing.core.PApplet;

public class RunAntNestWorld {

	public static void main(String[] args) {
		AntNestWorldViewController controller = AntNestWorldViewController.getController();
		PApplet.main(AntNestWorldView.class);
		controller.initialize();
		
		(new RunAntNestWorld()).exec(controller);
		
	}
	
	private void exec(AntNestWorldViewController controller) {
		Random rng = new MersenneTwisterRNG();
		Network net = new Network(rng, AntNestWorld.BRAIN_LAYER_WIDTHS);
		double score = play(net, controller.getView());
		System.out.println("Score = " + score);
		controller.close();
	}

	private double play(Network net, AntNestWorldView antNestWorldView) {
		NestAntBrain antBrain = new NestAntBrain(net);
		AntNestWorld antNestWorld = new AntNestWorld(antBrain);
		while (antNestWorld.update()){
			List<Ant> theAnts = antNestWorld.cloneAnts();
			List<Food> theMeals = antNestWorld.cloneMeals();
			antNestWorldView.update(theAnts, theMeals);
		}
		double score = (double)antNestWorld.getScore()/10000.0;
		return score;
	}
}

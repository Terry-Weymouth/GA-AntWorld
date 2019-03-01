package org.weymouth.ants.main;

import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.weymouth.ants.core.Ant;
import org.weymouth.ants.core.AntBrain;
import org.weymouth.ants.core.Food;
import org.weymouth.ants.core.Network;
import org.weymouth.ants.nest.AntNestWorld;
import org.weymouth.ants.nest.AntNestWorldView;
import org.weymouth.ants.nest.AntNestWorldViewController;

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
		AntBrain antBrain = new AntBrain(net);
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

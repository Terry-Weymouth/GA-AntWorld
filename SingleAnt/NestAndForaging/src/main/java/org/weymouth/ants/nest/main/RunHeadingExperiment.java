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

public class RunHeadingExperiment {
	
	public static void main(String[] args) {
		AntWorldViewController controller = AntWorldViewController.getController();
		PApplet.main(AntWorldView.class);
		controller.initialize();
		
		(new RunHeadingExperiment()).exec(controller);
		
	}

	private void exec(AntWorldViewController controller) {
		Random rng = new MersenneTwisterRNG();
		Network net = new Network(rng, AntWorld.BRAIN_LAYER_WIDTHS);
		play(net, controller.getView());
		controller.close();
	}

	private void play(Network net, AntWorldView antNestWorldView) {
		AntBrain antBrain = new AntBrain(net);
		int ants = 1;
		int meals = 0;
		int rounds = 1;
		AntWorld antNestWorld = new AntWorld(antBrain, ants, meals, rounds);
		while (seekNest(antNestWorld)){
			List<Ant> theAnts = antNestWorld.cloneAnts();
			List<Food> theMeals = antNestWorld.cloneMeals();
			antNestWorldView.update(theAnts, theMeals);
		}
	}

	private boolean seekNest(AntWorld antNestWorld) {
		// TODO Auto-generated method stub
		return false;
	}


}

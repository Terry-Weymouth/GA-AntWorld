package org.weymouth.ants.main;

import java.util.Random;

import org.weymouth.ants.AntWorld;
import org.weymouth.ants.AntWorldInterface;
import org.weymouth.ants.Network;

public class FitnessTestMain {
	
	public static void main(String[] args) {
		(new FitnessTestMain()).exec();
	}

	private void exec() {
		Network net = new Network(new Random(),AntWorld.BRAIN_LAYER_WIDTHS);
		int score = AntWorldInterface.getInterface().evaluate(net);
		System.out.println(score);
	}


}

package org.weymouth.ants.main;

public class AntLearnMain {

	public static void main(String[] args) {
		new AntLearnMain().exec();
	}
	
	private void exec() {
		// Learning Cycle = 
		//   for each selected network
		//		1) apply brain to world
		//		2) simulated N runs and evaluate (final score/N)
		//		3) assign score to network
		//	 evolve networks
	}
}

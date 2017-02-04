package org.weymouth.ants;

public class AntWorldInterface {

	private final static AntWorldInterface theInterface = new AntWorldInterface();
	private AntWorld theWorld  = new AntWorld();

	public static AntWorldInterface getInterface() { return theInterface; }

	private static int NUMBER_OF_PASSES = 7;
	
	public int evaluate(Network net) {
		AntBrain antBrain = new AntBrain(net);
		int totalScore = 0;
		for (int i = 0; i < NUMBER_OF_PASSES; i++){
			theWorld.setBrain(antBrain);
			while (theWorld.update()){}
			totalScore += theWorld.getScore();
		}
		return totalScore/NUMBER_OF_PASSES;
	}

}

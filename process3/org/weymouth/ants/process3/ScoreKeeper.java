package org.weymouth.ants.process3;

import java.util.Stack;

public class ScoreKeeper {
	
	private Stack<Integer> scores = new Stack<Integer>();

	public int lastScore() {
		Integer top = scores.peek();
		return top.intValue();
	}

	public void recordScore(int score) {
		scores.push(new Integer(score));
	}
}

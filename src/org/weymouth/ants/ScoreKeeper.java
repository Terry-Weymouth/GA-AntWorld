package org.weymouth.ants;

import java.util.Stack;

public class ScoreKeeper {
	
	private static Stack<Long> scores = new Stack<Long>();

	public static long lastScore() {
		Long top = scores.peek();
		return top.longValue();
	}

	public static void recordScore(long score) {
		scores.push(new Long(score));
	}
}

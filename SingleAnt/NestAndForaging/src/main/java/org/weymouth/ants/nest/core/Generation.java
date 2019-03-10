package org.weymouth.ants.nest.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Generation {
	
	private int rounds = 0;
	private int count = 0;
	private int totalScore = 0;
	private int averageScore = 0;
	
	private List<Ant> ants = new ArrayList<Ant>();
	private List<Food> meals = new ArrayList<Food>();
	
	private final AntBrain brain;
	private final int numberOfAnts;
	private final int numberOfMeals;
	private final int numberOfRounds;
	private final HashMap<Ant,AntActionStats> antStatsHolder;
	
	public Generation(AntBrain b, int nAnts, int nMeals, int nRounds) {
		brain = b;
		numberOfAnts = nAnts;
		numberOfMeals = nMeals;
		numberOfRounds = nRounds;
		antStatsHolder = new HashMap<Ant,AntActionStats>();
		nextRound();
	}

	private void nextRound() {
		count = 0;
		totalScore = 0;
		antStatsHolder.clear();
		ants = new ArrayList<Ant>();
		for (int i = 0; i < numberOfAnts; i++) {
			Ant a = new Ant(brain , Util.randomLocatonWithinNest());
			ants.add(a);
			antStatsHolder.put(a, new AntActionStats());
		}
		meals = new ArrayList<Food>();
		for (int i = 0; i < numberOfMeals; i++) {
			meals.add(new Food(Util.randomInteriorPoint()));
		}
	}
	
	private void printRound(){
		System.out.println ("    round: " + rounds + ", score: " + totalScore 
				+ ", avg: " + averageScore);
	}
	
	private void printActionStats(AntActionStats s) {
		System.out.println(String.format("    ant stats - "
				+ "turn: mean = %.2f, sd = %.4f; "
				+ "speed: mean = %.2f, sd = %.4f", 
				s.turnMean(), s.turnSd(), s.speedMean(), s.speedSd()));
	}

	public boolean oneStep() {
		updateAll();
		try {
			Thread.sleep(100);
		} catch (InterruptedException ignore) {
		}
		count ++;
		if ((count % 2) == 0) {
			if (ants.size() == 0) {
				averageScore = (averageScore * (rounds) + totalScore)/(rounds + 1);
				brain.getNetwork().setScore(((double)averageScore/10000.0));
				printRound();
				rounds++;
				if (rounds >= numberOfRounds) {
					return false;
				}
				nextRound();
			}
		}
		return true;
	}

	private void updateAll() {
		List<Ant> dead = new ArrayList<Ant>();
		for (Ant ant: ants) {
			ant.sense(meals);
			ant.update();
			ant.move();
			ant.feed(meals);
			AntActionStats stats = antStatsHolder.get(ant);
			stats.updateTurn(ant.getTurnDirection());
			stats.updateSpeed(ant.getSpeed());
			if (ant.getHealth() == 0) {
				printActionStats(stats);
				dead.add(ant);
			} else if (ant.inNest()) {
				Food forNest = ant.dropOneCarry();
				if (forNest != null) {
					meals.add(forNest);
				}
			}
		}
		ants.removeAll(dead);
		updateScore();
	}

	public List<Food> getMeals() {
		return meals;
	}

	public List<Ant> getAnts() {
		return ants;
	}

	public void updateScore() {
		totalScore += count;
	}
	
	public int score() {
		return totalScore;
	}

	public int getAverageScore() {
		return averageScore;
	}

}
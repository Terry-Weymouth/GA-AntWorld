package org.weymouth.ants.nest.core;

import java.util.ArrayList;
import java.util.List;

public class Generation {
	
	private int rounds = 0;
	private int count = 0;
	private int totalScore = 0;
	private int averageScore = 0;
	
	private List<Ant> ants = new ArrayList<Ant>();
	private List<Food> meals = new ArrayList<Food>();
	
	private final AntBrain brain;
	
	public Generation(AntBrain b) {
		brain = b;
		nextRound();
	}

	private void nextRound() {
		count = 0;
		totalScore = 0;
		ants = new ArrayList<Ant>();
		for (int i = 0; i < AntWorld.NUMBER_OF_ANTS; i++) {
			ants.add(new Ant(brain , Util.randomInteriorPoint()));
		}
		meals = new ArrayList<Food>();
		for (int i = 0; i < AntWorld.NUMBER_OF_MEALS; i++) {
			meals.add(new Food(Util.randomInteriorPoint()));
		}
	}
	
	private void printRound(){
		System.out.println ("round: " + rounds + ", score: " + totalScore 
				+ ", avg: " + averageScore);
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
				if (rounds >= AntWorld.NUMBER_OF_ROUNDS) {
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
			if (ant.getHealth() == 0) {
				int bonus = (int)Math.round(10000.0 * ant.getCarrying());
				totalScore += bonus;
				bonus = (int)Math.round(10000.0 * (1.0-ant.distanceToNestRatio()));
				totalScore += bonus;
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
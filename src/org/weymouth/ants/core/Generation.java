package org.weymouth.ants.core;

import java.util.ArrayList;
import java.util.List;

public class Generation {
	
	private int rounds = 0;
	private int count = 0;
	private int totalScore = 0;
	private int averageScore = 0;
	
	private List<Ant> ants = new ArrayList<Ant>();
	private List<Food> meals = new ArrayList<Food>();
	
	private final AntWorld world;
	private final AntBrain brain;
	
	public Generation(AntWorld w, AntBrain b) {
		world = w;
		brain = b;
		nextRound();
	}

	private void nextRound() {
		count = 0;
		totalScore = 0;
		ants = new ArrayList<Ant>();
		for (int i = 0; i < AntWorld.NUMBER_OF_ANTS; i++) {
			ants.add(new Ant(world, brain , Util.randomInteriorPoint()));
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
		count ++;
		if ((count % 100) == 0) {
			if (ants.size() == 0) {
				averageScore = (averageScore * (rounds) + totalScore)/(rounds + 1);
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
			if (ant.getHealth() < 0) {
				dead.add(ant);
				updateScore();
			} 
		}			
		ants.removeAll(dead);
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
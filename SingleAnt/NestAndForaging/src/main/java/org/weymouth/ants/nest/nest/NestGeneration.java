package org.weymouth.ants.nest;

import java.util.ArrayList;
import java.util.List;

import org.weymouth.ants.core.Food;
import org.weymouth.ants.core.Util;

public class NestGeneration {
	
	private int rounds = 0;
	private int count = 0;
	private int totalScore = 0;
	private int averageScore = 0;
	
	private List<NestAnt> ants = new ArrayList<NestAnt>();
	private List<Food> meals = new ArrayList<Food>();
	
	private final NestAntBrain brain;
	
	public NestGeneration(NestAntBrain b) {
		brain = b;
		nextRound();
	}

	private void nextRound() {
		count = 0;
		totalScore = 0;
		ants = new ArrayList<NestAnt>();
		System.out.println(AntNestWorld.NUMBER_OF_ANTS);
		for (int i = 0; i < AntNestWorld.NUMBER_OF_ANTS; i++) {
			ants.add(new NestAnt(brain , Util.randomInteriorPoint()));
		}
		meals = new ArrayList<Food>();
		for (int i = 0; i < AntNestWorld.NUMBER_OF_MEALS; i++) {
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
				if (rounds >= AntNestWorld.NUMBER_OF_ROUNDS) {
					return false;
				}
				nextRound();
			}
		}
		return true;
	}

	private void updateAll() {
		List<NestAnt> dead = new ArrayList<NestAnt>();
		for (NestAnt ant: ants) {
			ant.sense(meals);
			ant.update();
			ant.move();
			ant.feed(meals);
			if (ant.getHealth() == 0) {
				dead.add(ant);
			} 
		}
		ants.removeAll(dead);
		updateScore();
	}

	public List<Food> getMeals() {
		return meals;
	}

	public List<NestAnt> getAnts() {
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
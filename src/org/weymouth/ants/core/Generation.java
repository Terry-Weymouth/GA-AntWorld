package org.weymouth.ants.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Generation {
	
	private int rounds = 0;
	private int count = 0;
	private int totalScore = 0;
	private int averageScore = 0;
	
	private List<Ant> antHolder = new ArrayList<Ant>();
	private List<Food> foodHolder = new ArrayList<Food>();
	
	private final AntWorld world;
	private final AntBrain brain;
	
	public Generation(AntWorld w, AntBrain b) {
		world = w;
		brain = b;
		nextRound();
	}

	private void nextRound() {
		System.out.println("nextRound");
		count = 0;
		totalScore = 0;
		List<Ant> antHolder = new ArrayList<Ant>();
		for (int i = 0; i < AntWorld.NUMBER_OF_ANTS; i++) {
			antHolder.add(new Ant(world, brain , Util.randomInteriorPoint()));
		}
		List<Food> foodHolder = new ArrayList<Food>();
		for (int i = 0; i < AntWorld.NUMBER_OF_MEALS; i++) {
			foodHolder.add(new Food(Util.randomInteriorPoint()));
		}
		System.out.println(antHolder.size());
	}
	
	private void printRound(){
		System.out.println ("count: " + count + ", round: " + rounds + ", score: " + totalScore 
				+ ", avg: " + averageScore);
	}

	public boolean oneStep() {
		System.out.println("oneStep: " + antHolder.size());
		updateAll();
		try {
			Thread.sleep(100);
		} catch (InterruptedException ignore) {
		}
		count ++;
		if ((count % 2) == 0) {
			if (antHolder.size() == 0) {
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
		Iterator<Ant> ants = getAnts();
		while (ants.hasNext()) {
			Ant ant = ants.next();
			ant.sense(foodHolder);
			ant.update();
			ant.move();
			Iterator<Food> meals = getMeals();
			ant.feed(meals);
			if (ant.getHealth() == 0) {
				ants.remove();
			} 
		}
		updateScore();
	}

	public Iterator<Food> getMeals() {
		return foodHolder.iterator();
	}

	public Iterator<Ant> getAnts() {
		return antHolder.iterator();
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
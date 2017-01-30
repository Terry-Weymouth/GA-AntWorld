package org.weymouth.ants.main;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.weymouth.ants.Network;
import org.weymouth.watchmaker.NetworkController;
import org.weymouth.watchmaker.NetworkCrossover;
import org.weymouth.watchmaker.NetworkEvolutionObserver;
import org.weymouth.watchmaker.NetworkFactory;
import org.weymouth.watchmaker.NetworkFitnessEvaluator;
import org.weymouth.watchmaker.NetworkMutation;
import org.weymouth.watchmaker.NetworkReplace;
import org.weymouth.watchmaker.NetworkScramble;

public class WatchmakerMain {
	
	public static void main(String[] args) {
		(new WatchmakerMain()).exec();
	}

	private void exec() {
		
		NetworkController controller = new NetworkController();
		
		controller.setupGui();
		
		CandidateFactory<Network> candidateFactory = new NetworkFactory();
		NetworkFitnessEvaluator fitnessEvaluator = new NetworkFitnessEvaluator();
		SelectionStrategy<? super Network> selectionStrategy = new RouletteWheelSelection();
		Random rng = new MersenneTwisterRNG();

		List<EvolutionaryOperator<Network>> operators = new LinkedList<EvolutionaryOperator<Network>>();
		operators.add(new NetworkCrossover(1));
		operators.add(new NetworkMutation(new Probability(0.3)));
		operators.add(new NetworkScramble(new Probability(0.3)));
		operators.add(new NetworkReplace(new Probability(0.1)));

		EvolutionaryOperator<Network> pipeline = new EvolutionPipeline<Network>(operators);
		
		EvolutionEngine<Network> engine =
			new GenerationalEvolutionEngine<Network>(candidateFactory,
				pipeline,
                fitnessEvaluator,
                selectionStrategy,
                rng);
	
		engine.addEvolutionObserver(new NetworkEvolutionObserver(fitnessEvaluator));
		engine.addEvolutionObserver(controller.getEvolutionObserver());
		
		boolean naturalFitness = false;		
		
		double targetFitness = 0.01;
		TerminationCondition condition = new TargetFitness(targetFitness,naturalFitness);
		
		int populationSize = 40;
		int eliteCount = 4;
		
		engine.evolve(populationSize,eliteCount,condition);
	        
	}

}

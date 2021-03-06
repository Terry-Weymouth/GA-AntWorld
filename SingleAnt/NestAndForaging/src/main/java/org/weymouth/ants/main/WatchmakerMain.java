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
import org.uncommons.watchmaker.framework.AbstractEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.weymouth.ants.core.AntWorldView;
import org.weymouth.ants.core.AntWorldViewController;
import org.weymouth.ants.core.Network;
import org.weymouth.ants.watchmaker.NetworkController;
import org.weymouth.ants.watchmaker.NetworkCrossover;
import org.weymouth.ants.watchmaker.NetworkEvolutionObserver;
import org.weymouth.ants.watchmaker.NetworkFactory;
import org.weymouth.ants.watchmaker.NetworkFitnessEvaluator;
import org.weymouth.ants.watchmaker.NetworkMutation;
import org.weymouth.ants.watchmaker.NetworkReplace;
import org.weymouth.ants.watchmaker.NetworkScramble;
import org.weymouth.ants.watchmaker.StoringTextObserver;

import processing.core.PApplet;

public class WatchmakerMain {
	
	private final AntWorldViewController worldController = AntWorldViewController.getController();
	private final boolean headless;

	public WatchmakerMain(boolean flag) {
		headless = flag;
	}
	
	public void exec() {
		
		if (!headless) {
			PApplet.main(AntWorldView.class);
			worldController.initialize();
		}
		
		CandidateFactory<Network> candidateFactory = new NetworkFactory();
		NetworkFitnessEvaluator fitnessEvaluator = new NetworkFitnessEvaluator(headless);
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

		
		if (headless) {
			engine.addEvolutionObserver(new StoringTextObserver());
		} else {
			NetworkController controller = new NetworkController();
			controller.setupGui();
			engine.addEvolutionObserver(new NetworkEvolutionObserver(fitnessEvaluator));
			engine.addEvolutionObserver(controller.getEvolutionObserver());
			
		}
		
		// boolean naturalFitness = true;		
		// double targetFitness = 0.01;
		// TerminationCondition condition = new TargetFitness(targetFitness, naturalFitness);

		TerminationCondition condition = new GenerationCount(50);
		
		int populationSize = 20;
		int eliteCount = 4;

		((AbstractEvolutionEngine<Network>)engine).setSingleThreaded(true);
		engine.evolve(populationSize, eliteCount, condition);
	        
	}

}

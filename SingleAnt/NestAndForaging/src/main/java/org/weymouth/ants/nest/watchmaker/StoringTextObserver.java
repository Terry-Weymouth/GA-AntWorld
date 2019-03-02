package org.weymouth.ants.nest.watchmaker;

import java.sql.SQLException;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import org.weymouth.ants.nest.core.Network;
import org.weymouth.ants.nest.core.NetworkPojo;
import org.weymouth.ants.nest.storage.SqlliteStorage;

import com.fasterxml.jackson.core.JsonProcessingException;

public class StoringTextObserver implements EvolutionObserver<Network> {

	private int lastRecoredId = 0;
	
	@Override
	public void populationUpdate(PopulationData<? extends Network> data) {
		double topScore = data.getBestCandidateFitness();
		int generation = data.getGenerationNumber();
		Network network = data.getBestCandidate();
		System.out.println("Generation " + generation);
		System.out.println("  topScore = " + topScore);
		System.out.println("  elapsed time = " + timeString(data.getElapsedTime()));
		if (networkToStore(network)) {
			System.out.println("-->Network stored with id = " + lastRecoredId);
		} else {
			System.out.println("-->Network storing failed!");
		}
	}
	
	private String timeString(long elapsedTime) {
		double seconds = elapsedTime/1000.0;
		int minutes = (int)(elapsedTime/60000);
		int hours = minutes/60;
		seconds = seconds - minutes*60;
		minutes = minutes - hours*60;
		return String.format("Elapsed Time - %2d:%2d:%2.2f", hours, minutes, seconds);
	}
	
	private boolean networkToStore(Network network){
		boolean sucess = false;
		SqlliteStorage store = null;
		try {
			store = new SqlliteStorage("network.db");
			NetworkPojo networkPojo = new NetworkPojo(network);
			lastRecoredId = store.storeNetwork(networkPojo);
			sucess = true;
		} catch (SQLException | ClassNotFoundException | JsonProcessingException e) {
			e.printStackTrace();
		} finally {
			if (store != null)
				try {
					store.close();
				} catch (SQLException ignore) {
				}
		}
		return sucess;
	}

}
